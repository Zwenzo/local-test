package cn.xiaozhuoge.locallocal.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaowz
 * @date 2023/6/3 17:33
 */
@Configuration
@EnableConfigurationProperties(OssProperties.class)
public class OssConfiguration {
    
    @Bean
    public OssTemplate build(OssProperties ossProperties) {
        OSSClient build =
                new OSSClient(ossProperties.getEndpoint(), ossProperties.getAccessKey(), ossProperties.getSecretKey());
        return new DefaultOssTemplate(build);
    }
    
}