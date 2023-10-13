package cn.xiaozhuoge.locallocal.exception;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * @author zhaowz
 * @date 2023/6/20 11:27
 */
@Data
public class BaseException extends RuntimeException {
    
    protected int code = 400;
    
    public BaseException(String message, Object... args) {
        super(StrUtil.format(message, args));
    }
    
    public BaseException(int code, String message, Object... args) {
        super(StrUtil.format(message, args));
        this.setCode(code);
    }
}