package cn.xiaozhuoge.locallocal.netty.bio;

import com.google.common.primitives.Bytes;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhaowz
 * @Date 2023/9/26 15:42
 */
@Slf4j
public class BIOServer implements Serializable {
    
    
    @SneakyThrows
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        
        ServerSocket serverSocket = new ServerSocket(6666);
        
        log.info("serverSocket---启动---：：：：：");
        
        while (true) {
            final Socket accept = serverSocket.accept();
            log.info("accept链接到一个客户端");
            
            executorService.execute(() -> {
                try {
                    tail(accept);
                } catch (IOException e) {
                    //IGNORE
                }
            });
        }
    }
    
    
    public static void tail(Socket socket) throws IOException {
        log.info("执行通讯方法：：：：tail");
        byte[] bytes = Bytes.concat();
        try (InputStream inputStream = socket.getInputStream()) {
            if (-1 != inputStream.read(bytes)) {
                log.info("读取数据结束，数据为：：：[{}]", new String(bytes));
            }
        }
    }
}
