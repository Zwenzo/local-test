package cn.xiaozhuoge.locallocal.pachong;

import cn.xiaozhuoge.locallocal.dataobject.UserDO;
import com.alibaba.excel.EasyExcel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhaowz
 * @date 2023/6/5 14:06
 */
public class TestPaChong {
    
    private static final String url = "http://www.buildhr.com/area/shanghai/";
    
    public static void main(String[] args) throws Exception {
        System.out.println("---------------------------------------爬虫数据");
        //Jsoup.connect(url).postDataCharset().get();
        Document document = Jsoup.parse(new URL(url).openStream(), "GBK", url);
        //Document document = Jsoup.connect(url).postDataCharset(CharsetUtil.GBK).get();
        Elements chinajobs = document.getElementsByClass("chinajobs");
        Elements title = document.getElementsByAttribute("title");
        
        List<String> list = title.eachText();
        System.out.println(list);
        List<UserDO> ss = list.stream().map(s -> {
            UserDO userDO = new UserDO();
            userDO.setName(s);
            return userDO;
        }).collect(Collectors.toList());
        
        EasyExcel.write(new FileOutputStream("D:\\爬虫.xlsx"), UserDO.class).sheet("公司名称").doWrite(ss);
    }
}