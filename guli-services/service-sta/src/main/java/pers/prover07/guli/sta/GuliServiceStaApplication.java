package pers.prover07.guli.sta;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/5/6 9:01
 */
@EnableScheduling
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("pers.prover07.guli")
@MapperScan("pers.prover07.guli.sta.mapper")
public class GuliServiceStaApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuliServiceStaApplication.class, args);
    }

}
