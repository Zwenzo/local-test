package cn.xiaozhuoge.locallocal.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhaowz
 * @date 2023/6/19 11:32
 */
@Configuration
public class FilterConfiguration {
    
    @Bean
    public FilterRegistrationBean register() {
        return FilterRegisterBuilder.builder().build(new TestWebLogFilter(), new String[] {"/*"}, 0).register();
    }
    
}