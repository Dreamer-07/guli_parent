package pers.prover07.guli.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/26 14:56
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("pers.prover07.guli")
@MapperScan("pers.prover07.guli.cms.mapper")
public class GuliServiceCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuliServiceCmsApplication.class, args);
    }

}
