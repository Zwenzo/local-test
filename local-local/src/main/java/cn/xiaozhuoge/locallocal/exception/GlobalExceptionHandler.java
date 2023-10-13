package cn.xiaozhuoge.locallocal.exception;

import cn.xiaozhuoge.locallocal.result.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Servlet;

import static cn.xiaozhuoge.locallocal.result.R.SystemError;

/**
 * @author zhaowz
 * @date 2023/6/20 11:14
 */
@Slf4j
@Configuration
@RestControllerAdvice
@ConditionalOnClass({Servlet.class, DispatcherServlet.class})
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class GlobalExceptionHandler {
    
    
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R exception(MissingServletRequestParameterException e) {
        String message = String.format("缺少必要的请求参数: %s", e.getParameterName());
        return R.fail(HttpStatus.BAD_REQUEST.value(), message);
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R exception(MethodArgumentTypeMismatchException e) {
        String message = String.format("请求参数格式错误: %s", e.getName());
        return R.fail(HttpStatus.BAD_REQUEST.value(), message);
    }
    
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public R exception(HttpRequestMethodNotSupportedException e) {
        return R.fail(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
    }
    
    @ExceptionHandler({DataAccessException.class, PersistenceException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R exception(DataAccessException e) {
        log.error("数据库异常", e);
        return R.fail(500, "数据处理异常");
    }
    
    @ExceptionHandler(BaseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public R exception(BaseException e) {
        log.error("服务器异常", e);
        return R.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), SystemError);
    }
    
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public R exception(Throwable e) {
        log.error("服务器异常", e);
        return R.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), SystemError);
    }
}