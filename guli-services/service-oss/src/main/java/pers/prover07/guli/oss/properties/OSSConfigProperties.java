package pers.prover07.guli.oss.properties;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * OSS 配置属性
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/20 13:29
 */
@Data
@ToString
@Component
@ConfigurationProperties(prefix = "aliyun.oss.file")
public class OSSConfigProperties {

    private String endpoint;

    private String keyId;

    private String keySecret;

    private String bucketName;

}
