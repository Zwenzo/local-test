package cn.xiaozhuoge.locallocal.oss;

import com.aliyun.oss.model.PutObjectResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;

/**
 * @author zhaowz
 * @date 2023/6/3 17:23
 */
public interface OssTemplate {
    
    String BUCKET_NAME = "xinli-oss";
    
    PutObjectResult putFile(File file, String fileName);
    
    URL putFile(MultipartFile file) throws IOException;
    
    PutObjectResult putFile(InputStream inputStream, String fileName);
    
    URL getFilePath(String fileName);
    
    void getFileToLocal(String fileName, String localFilePath) throws IOException;
    
}
