package pers.prover07.guli.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import pers.prover07.guli.msm.service.MsmService;
import pers.prover07.guli.serviceenv.exception.GuliException;
import pers.prover07.guli.serviceenv.tools.IGlobalCache;
import pers.prover07.guli.serviceenv.tools.impl.CustomRedisCacheManager;
import pers.prover07.guli.utils.RandomUtil;
import pers.prover07.guli.utils.enums.ResultTypeEnum;

import java.util.HashMap;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/27 19:26
 */
@Service
public class MsmServiceImpl implements MsmService {

    @Autowired
    private IGlobalCache globalCache;

    @Autowired
    private Client aliyunMsmClient;

    @Override
    public boolean sendPhoneCode(String phoneNum) {
        // 如果缓存中的验证码还未过期就直接返回 true 即可
        String cachedCode = (String) globalCache.get("service-msm::phone::" + phoneNum);
        if (!StringUtils.isEmpty(cachedCode)) {
            return true;
        }
        HashMap<String, Object> dataMap = new HashMap<>();
        String phoneCode = RandomUtil.getFourBitRandom();
        dataMap.put("code", phoneCode);

        SendSmsRequest sendSmsRequest = new SendSmsRequest()
                .setSignName("阿里云短信测试")
                .setTemplateCode("SMS_154950909")
                .setPhoneNumbers("17841142883")
                .setTemplateParam(JSONObject.toJSONString(dataMap));
        try {
            SendSmsResponse sendSmsResponse = aliyunMsmClient.sendSms(sendSmsRequest);
            boolean status = "OK".equals(sendSmsResponse.getBody().code);
            // 保存成功后将 验证码 保存到 redis 中
            if (status) {
                globalCache.set("service-msm::phone::" + phoneNum, phoneCode, 300);
            }
            return status;
        } catch (Exception exception) {
            throw new GuliException(ResultTypeEnum.SEND_CODE_ERROR);
        }
    }
}
