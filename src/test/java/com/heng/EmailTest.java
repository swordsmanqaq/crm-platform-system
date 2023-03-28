package com.heng;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.mail.internet.MimeMessage;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class EmailTest {
    @Autowired
    private JavaMailSender javaMailSender;

    @Test
    public void  send(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //设置发送人
        mailMessage.setFrom("swords_man12@163.com");
        //邮件主题
        mailMessage.setSubject("新型冠状病毒防护指南");
        //邮件内容
        mailMessage.setText("好好在家待着.....");
        //收件人
        mailMessage.setTo("1275770560@qq.com");

        javaMailSender.send(mailMessage);
    }

    @Test
    public void test2() throws Exception{
        //创建复杂邮件对象
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //发送复杂邮件的工具类
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,"utf-8");
        helper.setFrom("swords_man12@163.com");
        //主题
        helper.setSubject("店铺激活");
        //邮件内容
        helper.setText("<h1>恭喜你店铺审核通过，请前往链接激活店铺</h1>"+
                "<a href='http://localhost:8082/shop/active?shopId=1'>激活链接</a>",true);
        //添加附件
//        helper.addAttachment("罗宾.jpg",new File("C:\\Users\\hm\\Desktop\\work\\aa.jpg"));
//        helper.addAttachment("压缩文件", new File("C:\\Users\\hm\\Desktop\\20191010\\2020-02-05-智能商贸-DAY4\\resources\\resources.zip"));
        //收件人
        helper.setTo("1275770560@qq.com");

        javaMailSender.send(mimeMessage);
    }

}