package cn.xiaozhuoge.locallocal.netty.buf;

import java.io.Serializable;
import java.nio.IntBuffer;

/**
 * @author zhaowz
 * @Date 2023/9/27 10:49
 */
public class BaseBuffer implements Serializable {
    
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);
        
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put(i + 2);
        }
        
        intBuffer.flip();
        
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
}
