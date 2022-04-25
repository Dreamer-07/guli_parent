package pers.prover07.guli.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/18 8:56
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@ComponentScan("pers.prover07.guli")
public class GuliServiceEduApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuliServiceEduApplication.class, args);
    }

}
