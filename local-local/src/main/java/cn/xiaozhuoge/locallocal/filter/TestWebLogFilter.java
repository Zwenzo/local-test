package cn.xiaozhuoge.locallocal.filter;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * @author zhaowz
 * @date 2023/4/23 10:19
 */
@Slf4j
@Component
public class TestWebLogFilter extends OncePerRequestFilter implements ApplicationContextAware {
    
    static ApplicationContext applicationContext;
    
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        //日志载体
        WebLogMetaData webLogMetaData = buildWebLongMetaData(request, response);
        
        Stopwatch stopwatch = Stopwatch.createStarted();
        //将请求和响应封装为可缓存式
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper responseWrapper =
                new ContentCachingResponseWrapper((HttpServletResponse) response);
        
        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            setReqRespBody(webLogMetaData, requestWrapper, responseWrapper);
            log.info("请求主机地址: [{}]     Request: [{} {}]     Status: [{}]     耗时:[{}]     请求内容:[{}]   |   响应信息:[{}]",
                    webLogMetaData.getAddress(), webLogMetaData.getMethod(), webLogMetaData.getPath(),
                    responseWrapper.getStatus(), stopwatch.toString(),
                    IOUtils.toString(webLogMetaData.getRequestBody(), StandardCharsets.UTF_8.toString()),
                    IOUtils.toString(webLogMetaData.getResponseBody(), StandardCharsets.UTF_8.toString()));
            responseWrapper.copyBodyToResponse();
        }
    }
    
    private void setReqRespBody(WebLogMetaData webLogMetaData, ContentCachingRequestWrapper requestWrapper,
                                ContentCachingResponseWrapper responseWrapper) {
        Optional.ofNullable(WebUtils.getNativeRequest(requestWrapper, ContentCachingRequestWrapper.class))
                .map(ContentCachingRequestWrapper::getContentAsByteArray)
                .ifPresent(webLogMetaData::setRequestBody);
        
        Optional.ofNullable(WebUtils.getNativeResponse(responseWrapper, ContentCachingResponseWrapper.class))
                .map(ContentCachingResponseWrapper::getContentAsByteArray)
                .ifPresent(webLogMetaData::setResponseBody);
    }
    
    private WebLogMetaData buildWebLongMetaData(HttpServletRequest request, HttpServletResponse response) {
        WebLogMetaData webLogMetaData = new WebLogMetaData();
        webLogMetaData.setAddress(request.getRemoteAddr());
        webLogMetaData.setPath(request.getServletPath());
        webLogMetaData.setMethod(request.getMethod());
        return webLogMetaData;
    }
}