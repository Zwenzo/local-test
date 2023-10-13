package cn.xiaozhuoge.locallocal.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaowz
 * @date 2023/7/6 14:00
 */
@Configuration
public class LocalRunner implements ApplicationRunner {
    
    
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("服务启动完成");
    }
}