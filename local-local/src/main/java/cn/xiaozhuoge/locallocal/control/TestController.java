package cn.xiaozhuoge.locallocal.control;

import cn.xiaozhuoge.locallocal.Student;
import cn.xiaozhuoge.locallocal.oss.OssTemplate;
import cn.xiaozhuoge.locallocal.result.R;
import com.google.common.collect.Lists;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author zhaowz
 * @date 2023/5/24 12:14
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {
    
    @Resource
    private OssTemplate ossTemplate;
    
    
    @PostMapping("/upload-file")
    public R uploadFile(@RequestParam MultipartFile file) throws IOException {
        URL url = ossTemplate.putFile(file);
        return R.data(url);
        //String fileName = FileUtil.getPrefix(file.getOriginalFilename());
        //FileDataContext.saveFileData(fileName, FileUtil.getSuffix(file.getOriginalFilename()));
        //return R.data(ossTemplate.putFile(file.getInputStream(), fileName));
    }
    
    @PostMapping("/get-file-path")
    public void uploadFile(@RequestParam String fileName) throws IOException {
        URL filePath = ossTemplate.getFilePath(fileName);
        ossTemplate.getFileToLocal(fileName, null);
    }
    
    @PostMapping("/test")
    public void testCallBack() throws IOException {
        ArrayList<Object> list = Lists.newArrayList();
        while (true){
            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            list.add(new Student(123));
        }
    }
}