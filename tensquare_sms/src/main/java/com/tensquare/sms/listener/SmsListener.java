package com.tensquare.sms.listener;


import com.aliyuncs.exceptions.ClientException;
import com.tensquare.sms.utils.SmsUtil;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "sms")
public class SmsListener {
    @Autowired
    private SmsUtil smsUtil ;
//    @Value("${aliyun.sms.template_code}")
    private String template_code ;
//    @Value("${aliyun.sms.sign_name}")
    private String sign_name ;

    @RabbitHandler
    public void executeSms(Map<String, String> map) {
        sign_name = "十次方社交网站";
        template_code = "SMS_166372744";
        String mobile = map.get("mobile");
        String checkCode = map.get("checkcode");
        System.out.println("手机号: " + mobile +" , 验证码: " + checkCode);
//        try{
//            smsUtil.sendSms(mobile, template_code, sign_name, "{\"code\":\"" + checkCode + "\"}");
//            System.out.println("发送成功");
//        }catch (ClientException e)
//        {
//            e.printStackTrace();
//        }
    }
}
