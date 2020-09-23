package com.tju.csdnmanger.service;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageService {
    // 短信应用 SDK AppID
    private int appId = 1400428317; // SDK AppID 以1400开头
    // 短信应用 SDK AppKey
    private String appKey = "8b66a1cfc2f3bd9aaf8994c5252347da";
    // 签名
    private String smsSign = "爱天工"; // NOTE: 签名参数使用的是`签名内容`，而不是`签名ID`。这里的签名"腾讯云"只是示例，真实的签名需要在短信控制台申请

    public SmsSingleSenderResult senMessage(String phone, String[] param, int templateId){
        SmsSingleSender ssender = new SmsSingleSender(appId, appKey);
        try {
            return ssender.sendWithParam("86", phone,
                    templateId, param, smsSign, "", "");
        } catch (HTTPException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
