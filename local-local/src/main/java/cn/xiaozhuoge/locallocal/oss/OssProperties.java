package cn.xiaozhuoge.locallocal.oss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static cn.xiaozhuoge.locallocal.constant.CommonConstant.PREFIX;

/**
 * @author zhaowz
 * @date 2023/6/3 17:34
 */
@Data
@Configuration
@ConfigurationProperties(prefix = PREFIX)
public class OssProperties {
    
    private String endpoint;
    
    private String accessKey;
    
    private String secretKey;
    
}