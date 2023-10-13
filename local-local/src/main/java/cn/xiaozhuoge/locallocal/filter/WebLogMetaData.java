package cn.xiaozhuoge.locallocal.filter;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zhaowz
 * @Date 2023/10/13 9:32
 */
@Data
public class WebLogMetaData implements Serializable {
    
    private String address;
    
    private String path;
    
    private String method;
    
    private byte[] responseBody;
    
    private byte[] requestBody;
    
}
