package top.liubaiblog.masterstudio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import top.liubaiblog.masterstudio.common.constant.HttpConstants;
import top.liubaiblog.masterstudio.common.constant.RabbitConstants;
import top.liubaiblog.masterstudio.common.exception.param.RequestParamsException;
import top.liubaiblog.masterstudio.dao.mapper.UploadFileMapper;
import top.liubaiblog.masterstudio.domain.po.UploadFile;
import top.liubaiblog.masterstudio.domain.po.User;
import top.liubaiblog.masterstudio.domain.query.file.GetFileQuery;
import top.liubaiblog.masterstudio.domain.vo.PageVO;
import top.liubaiblog.masterstudio.domain.vo.file.FileDetailVO;
import top.liubaiblog.masterstudio.domain.vo.file.FilePartVO;
import top.liubaiblog.masterstudio.domain.vo.file.SaveFileVO;
import top.liubaiblog.masterstudio.service.UploadFileService;
import top.liubaiblog.masterstudio.service.UserService;
import top.liubaiblog.masterstudio.util.date.DateUtils;
import top.liubaiblog.masterstudio.util.file.StorageUnitUtil;
import top.liubaiblog.masterstudio.util.http.HttpRequestUtils;
import top.liubaiblog.masterstudio.util.http.HttpResponseUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 13326
 * @description 针对表【ms_upload_file(文件表)】的数据库操作Service实现
 * @createDate 2022-10-03 15:23:40
 */
@Slf4j
@Service
public class UploadFileServiceImpl extends ServiceImpl<UploadFileMapper, UploadFile>
        implements UploadFileService {

    @Value("${upload.file.save-path:file-data}")
    private String savePath;

    @Autowired
    @SuppressWarnings("all")
    private WebMvcProperties webMvcProperties;

    @Autowired
    @SuppressWarnings("all")
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserService userService;

    @Override
    public PageVO<FilePartVO> listByFileQuery(GetFileQuery fileQuery) {
        // 条件封装
        LambdaQueryWrapper<UploadFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(UploadFile::getCreateTime);
        if (StringUtils.hasText(fileQuery.getFilename())) {
            wrapper.like(UploadFile::getFilename, fileQuery.getFilename());
        }
        if (StringUtils.hasText(fileQuery.getOriginFilename())) {
            wrapper.like(UploadFile::getOriginFilename, fileQuery.getOriginFilename());
        }
        if (StringUtils.hasText(fileQuery.getCreateStartTime())) {
            wrapper.ge(UploadFile::getCreateTime, fileQuery.getCreateStartTime());
        }
        if (StringUtils.hasText(fileQuery.getCreateStopTime())) {
            wrapper.le(UploadFile::getCreateTime, fileQuery.getCreateStopTime());
        }
        // 条件查询
        Page<UploadFile> filePage = new Page<>(fileQuery.getPageNo(), fileQuery.getPageSize());
        page(filePage, wrapper);
        // 封装返回值
        PageVO<FilePartVO> filePageVOs = new PageVO<>();
        filePageVOs.setTotalRecords(filePage.getTotal());
        filePageVOs.setTotalPages(filePage.getPages());
        List<UploadFile> files = filePage.getRecords();
        List<FilePartVO> filePartVOs = files.stream().map(file -> {
            FilePartVO filePartVO = new FilePartVO();
            BeanUtils.copyProperties(file, filePartVO);
            String size = StorageUnitUtil.autoConvert(file.getSize(), StorageUnitUtil.Unit.B, " ", "");
            filePartVO.setSize(size);
            return filePartVO;
        }).collect(Collectors.toList());
        filePageVOs.setRecords(filePartVOs);
        return filePageVOs;
    }

    @Override
    public SaveFileVO save(MultipartFile file) {
        try {
            // 获取本地路径，若不存在则创建
            String serverPath = savePath;
            // 设置保存文件名
            String originalFilename = file.getOriginalFilename();
            if (!StringUtils.hasLength(originalFilename)) {
                originalFilename = "";
            }
            String uuidFilename = UUID.randomUUID().toString().replace("-", "");
            String datePrefix = DateUtils.format(new Date(), "yyyy-MM-dd");
            String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String finalFilename = uuidFilename + fileSuffix;
            String finalFullFilename = serverPath + File.separator + datePrefix + File.separator + finalFilename;
            // 判断文件路径是否存在，不存在则创建
            File finalSavePathObj = new File(serverPath + File.separator + datePrefix);
            if (!finalSavePathObj.exists()) {
                finalSavePathObj.mkdirs();
            }
            // 保存到服务器本地
            File finalFileObj = new File(finalFullFilename);
            file.transferTo(finalFileObj);
            // 封装信息，准备存入数据库，并获取返回对象
            SaveFileVO saveFileVO = storeUploadFileToDb(originalFilename, finalFullFilename, finalFilename, file.getSize(), datePrefix);
            return saveFileVO;
        } catch (IOException e) {
            log.error("文件保存出错");
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileDetailVO getById(Long fid) {
        // 从数据库查询数据
        UploadFile uploadFile = getById((Serializable) fid);
        if (Objects.isNull(uploadFile)) {
            throw new RequestParamsException("文件编号错误");
        }
        // 封装返回对象
        FileDetailVO fileDetailVO = new FileDetailVO();
        String size = StorageUnitUtil.autoConvert(uploadFile.getSize(), StorageUnitUtil.Unit.B, " ", "");
        fileDetailVO.setSize(size);
        BeanUtils.copyProperties(uploadFile, fileDetailVO);
        if (!Objects.isNull(uploadFile.getCreateBy())) {
            String createBy = userService.getSingleNameById(uploadFile.getCreateBy());
            fileDetailVO.setCreateBy(createBy);
        }
        if (!Objects.isNull(uploadFile.getUpdateBy())) {
            String updateBy = userService.getSingleNameById(uploadFile.getUpdateBy());
            fileDetailVO.setUpdateBy(updateBy);
        }
        return fileDetailVO;
    }

    @Override
    public boolean removeById(Long fid) {
        // 获取该文件磁盘路径
        String localPath = getLocalPathById(fid);
        // 异步删除文件
        if (StringUtils.hasText(localPath)) {
            rabbitTemplate.convertAndSend(RabbitConstants.FILE_EXCHANGE,
                    RabbitConstants.FILE_REMOVE_EXCHANGE_BINDING_QUEUE,
                    localPath);
        }
        return removeById((Serializable) fid);
    }

    @Override
    public String getLocalPathById(Long fid) {
        LambdaQueryWrapper<UploadFile> wrapper = new LambdaQueryWrapper<>();
        wrapper.select(UploadFile::getFid, UploadFile::getLocalPath);
        wrapper.eq(UploadFile::getFid, fid);
        UploadFile file = getOne(wrapper);
        return !Objects.isNull(file) ? file.getLocalPath() : "";
    }

    @Override
    public void downloadFile(Long fid) {
        // 根据id从数据库获取文件信息
        String localPath = getLocalPathById(fid);
        if (!StringUtils.hasText(localPath)) {
            throw new RequestParamsException("文件编号错误");
        }
        // 获取响应对象
        HttpServletResponse resp = HttpResponseUtils.getLocalResponse();
        // 根据本地路径获取文件名
        String filename = localPath.substring(localPath.lastIndexOf(File.separator) + 1);
        // 根据文件路径创建输入流，从响应中获取输出流
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(localPath));
            OutputStream os = resp.getOutputStream()) {
            // 设置响应头
            resp.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            resp.setStatus(HttpServletResponse.SC_OK);
            // 拷贝文件
            FileCopyUtils.copy(bis, os);
        } catch (Exception e) {
            throw new RuntimeException("下载出现异常");
        }
    }

    /**
     * 保存上传文件的信息到数据库
     */
    private SaveFileVO storeUploadFileToDb(String originalFilename,
                                           String finalFullFilename,
                                           String finalFilename,
                                           Long size,
                                           String datePrefix) {
        // 获取登录用户
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = (User) authentication.getPrincipal();
        // 封装数据库对象
        String basePath = HttpRequestUtils.getBasePath();
        UploadFile uploadFile = new UploadFile();
        uploadFile.setFilename(finalFilename);
        uploadFile.setOriginFilename(originalFilename);
        uploadFile.setSize(size);
        uploadFile.setLocalPath(finalFullFilename);
        String staticPathPattern = webMvcProperties.getStaticPathPattern();
        String staticPath = staticPathPattern.substring(1, staticPathPattern.lastIndexOf("/"));
        // 拼接网络路径，拼接完成后的结果如: http://localhost:8080/static/2022-10-20/xxxxxxxxxxxxxxxxx.png
        String networkPath = basePath +
                staticPath +
                HttpConstants.URL_SEPARATOR +
                datePrefix +
                HttpConstants.URL_SEPARATOR +
                finalFilename;
        uploadFile.setNetworkPath(networkPath);
        uploadFile.setCreateBy(currentUser.getUid());
        uploadFile.setCreateTime(new Date());
        uploadFile.setUpdateBy(currentUser.getUid());
        uploadFile.setUpdateTime(new Date());
        // 采用异步方式文件信息保存到数据库
        rabbitTemplate.convertAndSend(RabbitConstants.FILE_EXCHANGE,
                RabbitConstants.FILE_UPLOAD_EXCHANGE_BINDING_QUEUE,
                uploadFile);
        // 封装返回对象
        SaveFileVO saveFileVO = new SaveFileVO();
        saveFileVO.setFilename(finalFilename);
        saveFileVO.setNetworkPath(networkPath);
        return saveFileVO;
    }
}




