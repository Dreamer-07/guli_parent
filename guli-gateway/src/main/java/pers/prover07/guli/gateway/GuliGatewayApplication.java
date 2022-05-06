package pers.prover07.guli.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/5/6 16:17
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GuliGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuliGatewayApplication.class, args);
    }

}
