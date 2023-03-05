package top.liubaiblog.masterstudio.message.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.liubaiblog.masterstudio.common.constant.RabbitConstants;
import top.liubaiblog.masterstudio.domain.po.Log;
import top.liubaiblog.masterstudio.domain.po.UploadFile;
import top.liubaiblog.masterstudio.service.LogService;

import java.io.IOException;

/**
 * @author 留白
 * @description
 */
@Slf4j
@Component
public class LogListener {

    @Autowired
    private LogService logService;

    @RabbitListener(queues = RabbitConstants.LOG_SAVE_QUEUE)
    public void receiveUpload(Log sysLog) throws IOException {
        try {
            logService.save(sysLog);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
