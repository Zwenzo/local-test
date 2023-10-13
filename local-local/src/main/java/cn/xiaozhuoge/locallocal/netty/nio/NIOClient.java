package cn.xiaozhuoge.locallocal.netty.nio;

import lombok.SneakyThrows;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author zhaowz
 * @Date 2023/9/27 16:53
 */
public class NIOClient implements Serializable {
    
    @SneakyThrows
    public static void main(String[] args) {
        SocketChannel socketChannel = SocketChannel.open();
        
        socketChannel.configureBlocking(false);
        
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 9898);
        
        //连接失败
        if (!socketChannel.connect(inetSocketAddress)) {
            
            while (!socketChannel.finishConnect()) {
                System.out.println("连接需要时间 客户端不会阻塞 做其他事情");
            }
        }
        
        //连接成功
        ByteBuffer wrap = ByteBuffer.wrap("测试socket".getBytes());
        socketChannel.write(wrap);
        System.in.read();
    }
}
