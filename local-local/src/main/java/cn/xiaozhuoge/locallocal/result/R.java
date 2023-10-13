package cn.xiaozhuoge.locallocal.result;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @author zhaowz
 * @date 2023/6/20 11:29
 */
@Data
@NoArgsConstructor
public class R<T> implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    public static final int successCode = HttpStatus.OK.value();
    
    public static final String successMsg = "操作成功";
    
    public static final String failMsg = "操作失败";
    
    public static final String SystemError = "系统繁忙，请稍后重试";
    
    public static final int failCode = HttpStatus.BAD_REQUEST.value();
    
    private boolean success;
    
    private int code;
    
    private String msg;
    
    private T data;
    
    private R(int code, String msg) {
        this.code = code;
        this.msg = msg;
        this.success = code == successCode;
    }
    
    private R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = code == successCode;
    }
    
    public static <T> R<T> fail(int code, String msg) {
        return new R<>(code, msg);
    }
    
    public static <T> R<T> fail(String msg) {
        return new R<>(failCode, msg);
    }
    
    public static <T> R<T> fail(int code) {
        return new R<>(code, failMsg);
    }
    
    public static <T> R<T> success(String msg) {
        return new R<>(successCode, msg);
    }
    
    public static <T> R<T> data(int code, T data) {
        return new R<>(code, successMsg, data);
    }
    
    public static <T> R<T> data(T data) {
        return new R<>(successCode, successMsg, data);
    }
    
    public static <T> R<T> status(boolean code) {
        return code ? success(successMsg) : fail(failMsg);
    }
    
}