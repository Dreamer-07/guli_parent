package pers.prover07.guli.acl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("pers.prover07.guli")
@MapperScan("pers.prover07.guli.acl.mapper")
public class GuliServiceAclApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuliServiceAclApplication.class, args);
    }

}
