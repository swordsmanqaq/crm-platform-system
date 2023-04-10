package com.heng.base.listener;/**
 * @author shkstart
 * @create 2023-04-09 21:23
 */

import com.heng.base.config.RegisterNoticeRabbitmqConfig;
import com.heng.base.utils.SmsUtil;
import com.heng.user.dto.SendEmailAndMessageDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 *@Auther:Jarvis
 *@Date:2023年04月2023/4/9日21:23
 *@Description:
 */
@Component
public class RabbitMQListener {

    @Autowired
    private JavaMailSender javaMailSender;

    @RabbitListener(queues = {RegisterNoticeRabbitmqConfig.QUEUE_INFORM_EMAIL})
    public void listenerEmail(SendEmailAndMessageDTO dto){
        try {
            //创建邮箱对象
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            //发件人
            helper.setFrom("swords_man12@163.com");
            //主题
            helper.setSubject(dto.getTheme());
            String text = "<html>\n" +
                    "<body>\n" +
                    dto.getContent() +
                    "</body>\n" +
                    "</html>";
            //内容
            helper.setText(text,true);
            //收件人
            helper.setTo(dto.getAddressee());
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = {RegisterNoticeRabbitmqConfig.QUEUE_INFORM_SMS})
    public void listenerSms(SendEmailAndMessageDTO dto){
        //发送短信
//        SmsUtil.sendSms(dto.getAddressee(),dto.getContent());
    }
}
