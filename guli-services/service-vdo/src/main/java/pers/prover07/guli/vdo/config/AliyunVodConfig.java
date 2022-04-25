package pers.prover07.guli.vdo.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.prover07.guli.vdo.properties.OSSConfigProperties;

/**
 * 阿里云视频点播配置类
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/25 7:14
 */
@Configuration
public class AliyunVodConfig {

    @Autowired
    private OSSConfigProperties ossConfigProperties;

    @Bean
    public DefaultAcsClient acsClient() {
        // 点播服务接入区域
        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, ossConfigProperties.getKeyId(), ossConfigProperties.getKeySecret());
        return new DefaultAcsClient(profile);
    }

}
