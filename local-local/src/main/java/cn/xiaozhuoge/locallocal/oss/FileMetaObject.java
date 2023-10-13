package cn.xiaozhuoge.locallocal.oss;

import lombok.Data;

/**
 * @author zhaowz
 * @date 2023/7/11 14:21
 */
@Data
public class FileMetaObject {
    
    private String fileName;
    
    private String fileType;
    
    public FileMetaObject(String fileName,String fileType){
        this.fileName = fileName;
        this.fileType = fileType;
    }
    
}