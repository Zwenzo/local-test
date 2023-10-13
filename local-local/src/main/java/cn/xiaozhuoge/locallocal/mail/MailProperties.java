package cn.xiaozhuoge.locallocal.mail;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaowz
 * @date 2023/6/7 13:48
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.mail")
public class MailProperties {
    
    private String host;
    
    private String username;
    
    private String password;
    
    private String protocol;
}