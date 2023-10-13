package cn.xiaozhuoge.locallocal.oss;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.time.Instant;
import java.util.Date;

/**
 * @author zhaowz
 * @date 2023/6/3 17:30
 */
@AllArgsConstructor
public class DefaultOssTemplate implements OssTemplate {
    
    private OSSClient ossClient;
    
    @Override
    public PutObjectResult putFile(File file, String fileName) {
        return ossClient.putObject(getBucketName(), fileName, file);
    }
    
    @Override
    public URL putFile(MultipartFile file) throws IOException {
        ossClient.putObject(getBucketName(), file.getOriginalFilename(), file.getInputStream());
        return ossClient.generatePresignedUrl(getBucketName(), file.getOriginalFilename(), toExpireDate(3600));
    }
    
    @Override
    public PutObjectResult putFile(InputStream inputStream, String fileName) {
        return ossClient.putObject(getBucketName(), fileName, inputStream);
    }
    
    @Override
    public URL getFilePath(String fileName) {
        return ossClient.generatePresignedUrl(getBucketName(), fileName, toExpireDate(3600), HttpMethod.GET);
    }
    
    protected Date toExpireDate(int expire) {
        long expTimeMillis = Instant.now().toEpochMilli();
        expTimeMillis += 1000 * expire;
        Date expiration = new Date();
        expiration.setTime(expTimeMillis);
        return expiration;
    }
    
    public void getFileToLocal(String fileName, String localFilePath) throws IOException {
        OSSObject object = ossClient.getObject(new GetObjectRequest(getBucketName(), fileName));
        FileUtils.copyToFile(object.getObjectContent(), new File(
                System.getProperty("user.home") + "/" + fileName + "." + FileDataContext.getFileType(fileName)));
    }
    
    public String getBucketName() {
        return BUCKET_NAME;
    }
}