package cn.xiaozhuoge.locallocal.mail;

import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;

/**
 * @author zhaowz
 * @date 2023/6/7 13:32
 */
@Component
public class SendMail {
    
    @Autowired
    MailProperties mailProperties;
    
    @Resource
    JavaMailSender mailSender;
    
    public UserRegisterDTO register(@RequestBody UserRegisterDTO userRegisterDTO) {
        
        //业务逻辑..
        
        int randomInt = RandomUtil.randomInt(999999);
        userRegisterDTO.setUserSeq(randomInt);
        
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mailProperties.getUsername());
        mailMessage.setFrom(mailProperties.getUsername());
        mailMessage.setSubject("测试发送");
        mailMessage.setText("测试发送一个邮箱验证哦。。验证码为：" + randomInt);
        mailSender.send(mailMessage);
        return userRegisterDTO;
    }

}