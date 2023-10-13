package cn.xiaozhuoge.locallocal.dataobject;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author zhaowz
 * @date 2023/6/5 14:56
 */
@Data
public class UserDO {
    
    @ExcelIgnore
    private Integer age;
    
    @ExcelProperty("名称")
    private String name;
    
}