package pers.prover07.guli.member;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/27 21:31
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("pers.prover07.guli")
@MapperScan("pers.prover07.guli.member.mapper")
public class GuliServiceMemberApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuliServiceMemberApplication.class, args);
    }

}
