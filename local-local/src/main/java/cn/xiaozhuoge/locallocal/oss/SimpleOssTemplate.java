package cn.xiaozhuoge.locallocal.oss;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.date.DateUtil;
import com.aliyun.oss.OSSClient;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.time.Instant;
import java.util.Date;

@Slf4j
@AllArgsConstructor
public class SimpleOssTemplate{
    String bucket  = "";
    
    private OSSClient ossClient;
    
    public URL putFile(File file) {
        String filePreKeyName = StringUtils.substringBefore(file.getName(), StringPool.DOT);
        ossClient.putObject(bucket, filePreKeyName, file);
        return ossClient.generatePresignedUrl(bucket, filePreKeyName, toExpireDate(3600));
    }
    
    public URL putFile(String base64) {
        //获取扩展名
        String fileEx = getFileEx(base64);
        
        //随机key
        String fileNameKey = "base64_" + System.currentTimeMillis() + fileEx;
        
        //base64转字节
        byte[] bytes = Base64.decode(base64.replace(base64.split("base64,")[0] + "base64,", ""));
        
        //上传以及拿到路径
        ossClient.putObject(bucket, fileNameKey, new ByteArrayInputStream(bytes));
        return ossClient.generatePresignedUrl(bucket, fileNameKey, toExpireDate(3600));
    }
    
    public URL putFile(MultipartFile file) throws IOException {
        ossClient.putObject(bucket, file.getOriginalFilename(), file.getInputStream());
        return ossClient.generatePresignedUrl(bucket, file.getOriginalFilename(), toExpireDate(3600));
    }
    
    private static String getFileEx(String base64) {
        String dataPrix = base64.split("base64,")[0];
        String suffix = "";
        if ("data:image/jpeg;".equalsIgnoreCase(dataPrix)) {//data:image/jpeg;base64,base64编码的jpeg图片数据
            suffix = ".jpg";
        } else if ("data:image/x-icon;".equalsIgnoreCase(dataPrix)) {//data:image/x-icon;base64,base64编码的icon图片数据
            suffix = ".ico";
        } else if ("data:image/gif;".equalsIgnoreCase(dataPrix)) {//data:image/gif;base64,base64编码的gif图片数据
            suffix = ".gif";
        } else if ("data:image/png;".equalsIgnoreCase(dataPrix)) {//data:image/png;base64,base64编码的png图片数据
            suffix = ".png";
        }
        return suffix;
    }
    
    //local  test
    //    public static void main(String[] args) throws IOException {
    //        OSSClient ossClient = new OSSClient(endpoint, accessKeyID, accessKeySecret);
    //        File file = new File("C:\\Users\\15069\\图片.jpg");
    //        String filePreKeyName = StringUtils.substringBefore(file.getName(), StringPool.DOT);
    //        ossClient.putObject(bucket, filePreKeyName, file);
    //        URL url = ossClient.generatePresignedUrl(bucket, filePreKeyName, toExpireDate(3600));
    //        log.info("文件路径为::::::" + url.toString());
    //        //Files.createFile(Paths.get(System.getProperty("user.home") + "/测试文件.xlsx"));
    //    }
    
    
    protected static Date toExpireDate(int expire) {
        long expTimeMillis = Instant.now().toEpochMilli();
        expTimeMillis += 1000 * expire;
        Date expiration = new Date();
        expiration.setTime(expTimeMillis);
        return expiration;
    }
    
    public static void main(String[] args) {
        long expTimeMillis = Instant.now().toEpochMilli();
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000000000);
        System.out.println(DateUtil.toLocalDateTime(expiration).getYear());
        System.out.println(DateUtil.toLocalDateTime(expiration).getMonthValue());
        System.out.println(DateUtil.toLocalDateTime(expiration).getDayOfMonth());
    }
}