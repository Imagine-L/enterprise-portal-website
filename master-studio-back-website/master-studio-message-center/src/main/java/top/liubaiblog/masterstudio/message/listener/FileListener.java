package top.liubaiblog.masterstudio.message.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import top.liubaiblog.masterstudio.common.constant.RabbitConstants;
import top.liubaiblog.masterstudio.domain.po.UploadFile;
import top.liubaiblog.masterstudio.service.UploadFileService;

import java.io.File;
import java.io.IOException;

/**
 * @author 留白
 * @description
 */
@Slf4j
@Component
public class FileListener {

    @Autowired
    private UploadFileService uploadFileService;

    /**
     * 上传文件信息到数据库
     */
    @RabbitListener(queues = RabbitConstants.FILE_UPLOAD_QUEUE)
    public void receiveUpload(UploadFile uploadFile) throws IOException {
        Assert.notNull(uploadFile, "收到空消息");
        boolean result = false;
        try {
            result = uploadFileService.save(uploadFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (result) {
            log.info("================= 文件上传[start] =================");
            log.info("文件本地保存位置 -> {}", uploadFile.getLocalPath());
            log.info("文件网络保存位置 -> {}", uploadFile.getNetworkPath());
            log.info("================= 文件上传[end] =================");
        } else {
            log.error("文件上传失败");
        }
    }

    /**
     * 删除本地路径下的文件
     */
    @RabbitListener(queues = RabbitConstants.FILE_REMOVE_QUEUE)
    public void receiveRemove(String localPath) {
        File file = new File(localPath);
        try {
            if (file.delete()) {
                log.info("{} 位置文件删除成功", localPath);
            } else {
                log.error("{} 位置文件删除失败", localPath);
            }
        } catch (Exception e) {
            log.error("{} 位置文件删除失败", localPath);
        }
    }

}
