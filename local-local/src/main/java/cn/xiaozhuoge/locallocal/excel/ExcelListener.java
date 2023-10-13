package cn.xiaozhuoge.locallocal.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.google.common.collect.Lists;

import java.io.File;
import java.util.List;

/**
 * @author zhaowz
 * @date 2023/6/5 14:53
 */
public class ExcelListener extends AnalysisEventListener {
    
    private List datas = Lists.newLinkedList();
    
    
    @Override
    public void invoke(Object data, AnalysisContext context) {
        datas.add(data);
    }
    
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.out.println(datas);
    }
    
    public static void main(String[] args) {
        File file = new File("D:\\导入模板test.xlsx");
        ExcelReader build = EasyExcelFactory.read(file, new ExcelListener()).build();
        List<ReadSheet> readSheets = build.excelExecutor().sheetList();
        for (ReadSheet readSheet : readSheets) {
            System.out.println("sheet名字为" + readSheet.getSheetName());
            build.read(readSheet);
        }
        build.finish();
    }
}