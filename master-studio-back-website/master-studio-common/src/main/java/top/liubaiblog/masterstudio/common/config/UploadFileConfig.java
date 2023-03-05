package top.liubaiblog.masterstudio.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;

/**
 * @author 留白
 * @description 上传文件配置类
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
public class UploadFileConfig {

    // 文件的保存路径


    @Value("${upload.file.save-path}")
    private String savePath;

    /**
     * 创建文件保存路径
     */
    @PostConstruct
    public void init() {
        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}
