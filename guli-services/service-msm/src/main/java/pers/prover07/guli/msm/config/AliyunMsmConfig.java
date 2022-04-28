package pers.prover07.guli.msm.config;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.teaopenapi.models.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.prover07.guli.msm.properties.OSSConfigProperties;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/27 19:31
 */
@Configuration
public class AliyunMsmConfig {

    @Autowired
    private OSSConfigProperties ossConfigProperties;

    @Bean
    public Client client() throws Exception {
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(ossConfigProperties.getKeyId())
                // 您的AccessKey Secret
                .setAccessKeySecret(ossConfigProperties.getKeySecret());
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        return new Client(config);
    }


}
