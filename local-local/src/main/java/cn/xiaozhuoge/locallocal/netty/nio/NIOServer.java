package cn.xiaozhuoge.locallocal.netty.nio;

import lombok.SneakyThrows;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author zhaowz
 * @Date 2023/9/27 16:53
 */
public class NIOServer implements Serializable {
    
    @SneakyThrows
    public static void main(String[] args) {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        
        Selector selector = Selector.open();
        
        //拿到socket并绑定ip
        socketChannel.socket().bind(new InetSocketAddress(9898));
        socketChannel.configureBlocking(false);
        
        //将ServerSocketChannel 注册到selector  事件为客户端连接事件  返回一个SelectionKey
        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
        
        while (true) {
            
            if (selector.select(2000) == 0) {
                System.out.println("服务器等待2秒 无连接事件发生");
                continue;
            }
            System.out.println("连接事件的key数量" + selector.selectedKeys().size());
            for (SelectionKey selectionKey : selector.selectedKeys()) {
                
                //连接事件
                if (selectionKey.isAcceptable()) {
                    SocketChannel accept = socketChannel.accept();
                    accept.configureBlocking(false);
                    //关联buffer
                    accept.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(24));
                    System.out.println("注册的key数量" + selector.keys().size());
                }
                
                //读取事件
                if (selectionKey.isReadable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    channel.read(byteBuffer);
                    System.out.println("客户端发送数据为" + new String(byteBuffer.array()));
                }
                //手动从集合里删除key 防止重复操作
                selector.selectedKeys().remove(selectionKey);
            }
        }
    }
}





















