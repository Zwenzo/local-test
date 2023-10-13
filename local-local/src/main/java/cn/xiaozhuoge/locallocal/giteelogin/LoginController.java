package cn.xiaozhuoge.locallocal.giteelogin;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author zhaowz
 * @date 2023/6/14 9:56
 */
@RestController
public class LoginController {
    
    @Resource
    private GiteeProperties giteeProperties;
    
    //跳转到授权页面 会返回code（同意授权有，拒绝无）
    String loginUri = "https://gitee.com/oauth/authorize";
    
    //通过code获取用户token
    String authUri = "https://gitee.com/oauth/token";
    
    //通过token获取gitee用户个人信息
    String tokenUri = "https://gitee.com/api/v5/user";
    
    @GetMapping("/login")
    public void login(HttpServletResponse response) throws IOException {
        //重定向到
        response.sendRedirect(UriComponentsBuilder.fromUriString(loginUri)
                .queryParam("response_type", "code")
                .queryParam("client_id", giteeProperties.getClientId())
                .queryParam("redirect_uri", giteeProperties.getRollBack())
                .queryParam("state", "state")
                .toUriString());
    }
    
    @PostMapping("/test")
    public String testOkHttp(HttpServletRequest request, HttpServletResponse response,
            @RequestBody Map<String, Object> map) throws IOException {
        return JSONObject.toJSONString(map);
    }
    
    @GetMapping("/getFile")
    public void verify(HttpServletRequest request) {
        Object code = request.getParameter("code");
        String url = UriComponentsBuilder.fromUriString(authUri)
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", giteeProperties.getClientId())
                .queryParam("redirect_uri", giteeProperties.getRollBack())
                .queryParam("code", code)
                .toUriString();
        Map<String, Object> map = Maps.newHashMap();
        map.put("client_secret", giteeProperties.getClientSecret());
        String post = HttpUtil.post(url, map);
        JSONObject jsonObject = JSONObject.parseObject(post);
        System.out.println(post);
        
        System.out.println(HttpUtil.get(UriComponentsBuilder.fromUriString(tokenUri)
                .queryParam("access_token", jsonObject.get("access_token"))
                .queryParam("User-Agent",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36")
                .toUriString()));
    }
}