package cn.xiaozhuoge.locallocal.netty.channel;

import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * @author zhaowz
 * @Date 2023/9/27 14:16
 */
public class TestFileChannel implements Serializable {
    
    @SneakyThrows
    public static void main(String[] args) {
        //String test = "hello 卓哥hello 卓哥hello 卓哥hello 卓哥hello 卓哥hello 卓哥hello 卓哥hello 卓哥hello 卓哥hello 卓哥";
        //
        //FileOutputStream fileOutputStream = new FileOutputStream("D:\\test1.txt");
        //
        //FileChannel fileChannel = fileOutputStream.getChannel();
        //
        //ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //byteBuffer.put(test.getBytes(StandardCharsets.UTF_8));
        //
        //byteBuffer.flip();
        //fileChannel.write(byteBuffer);
        //
        //fileChannel.close();
        //fileOutputStream.close();
        
        //FileInputStream fileInputStream = new FileInputStream("D:\\test1.txt");
        //
        //FileChannel fileChannel = fileInputStream.getChannel();
        //
        //ByteBuffer byteBuffer = ByteBuffer.allocate(24);
        //fileChannel.read(byteBuffer);
        //
        //System.out.println(new String(byteBuffer.array()));
        //fileChannel.close();
        //fileInputStream.close();
    }
}
