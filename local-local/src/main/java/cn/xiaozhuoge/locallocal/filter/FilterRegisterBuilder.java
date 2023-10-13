package cn.xiaozhuoge.locallocal.filter;

import lombok.Data;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author zhaowz
 * @date 2023/4/23 10:11
 */
@Data
public class FilterRegisterBuilder<T extends Filter> {
    
    private T filter;
    
    private String[] urls;
    
    private int order;
    
    public static FilterRegisterBuilder builder() {
        return new FilterRegisterBuilder();
    }
    
    public FilterRegisterBuilder build(T filter, String[] urls, int order) {
        this.filter = filter;
        this.urls = urls;
        this.order = order;
        return this;
    }
    
    public FilterRegistrationBean register(){
        paramCheck();
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(filter);
        filterRegistrationBean.setOrder(order);
        filterRegistrationBean.setUrlPatterns(Arrays.asList(urls));
        return filterRegistrationBean;
    }
    
    private void paramCheck() {
        Objects.requireNonNull(filter,"Filter must not be null");
        Objects.requireNonNull(urls,"Urls must not be null");
    }
}