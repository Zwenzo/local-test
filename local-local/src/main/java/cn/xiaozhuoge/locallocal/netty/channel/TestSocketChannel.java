package cn.xiaozhuoge.locallocal.netty.channel;

import lombok.SneakyThrows;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;

/**
 * @author zhaowz
 * @Date 2023/9/27 14:59
 */
public class TestSocketChannel implements Serializable {
    
    @SneakyThrows
    public static void main(String[] args) {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);
        
        serverSocketChannel.socket().bind(inetSocketAddress);
    }
}
