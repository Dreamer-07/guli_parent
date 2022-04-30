package pers.prover07.guli.member.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/30 18:16
 */
@Data
@Component
@ConfigurationProperties(prefix = "wx.open")
public class WxOpenProperties {

    private String appId;

    private String appSecret;

    private String redirectUrl;

}
