package cn.xiaozhuoge.locallocal.giteelogin;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaowz
 * @date 2023/6/13 17:36
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "gitee")
public class GiteeProperties {
    
    private String clientId;
    
    private String clientSecret;
    
    private String rollBack;
}