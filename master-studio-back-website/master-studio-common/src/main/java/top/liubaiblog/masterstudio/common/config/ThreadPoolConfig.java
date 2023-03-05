package top.liubaiblog.masterstudio.common.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author 留白
 * @description 线程池配置
 */
@EnableAsync
@Configuration(proxyBeanMethods = false)
public class ThreadPoolConfig {

    @Autowired
    private TaskExecutionProperties taskExecutionProperties;

    @Bean("threadPoolTaskExecutor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        TaskExecutionProperties.Pool poolConfig = taskExecutionProperties.getPool();
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.setKeepAliveSeconds((int) poolConfig.getKeepAlive().getSeconds());
        pool.setCorePoolSize(poolConfig.getCoreSize()); // 核心线程池数
        pool.setMaxPoolSize(poolConfig.getMaxSize()); // 最大线程
        pool.setQueueCapacity(poolConfig.getQueueCapacity()); // 队列容量
        pool.setRejectedExecutionHandler(new java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy()); // 队列满，线程被拒绝执行策略
        return pool;
    }

}
