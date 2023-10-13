package cn.xiaozhuoge.locallocal;


import com.google.common.primitives.Bytes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

@Slf4j
@SpringBootApplication
public class LocalLocalApplication {
    
    
    public static void main(String[] args) throws IOException {
        //SpringApplication.run(LocalLocalApplication.class, args);
        System.out.println(2 << 1);
    }
    
    
    public static void tail(Socket socket) throws IOException {
        log.info("执行通讯方法：：：：tail");
        byte[] bytes = Bytes.concat();
        try (InputStream inputStream = socket.getInputStream()) {
            if(-1 != inputStream.read(bytes)){
                log.info("读取数据结束，数据为：：：[{}]",new String(bytes));
            }
        }
    }
}