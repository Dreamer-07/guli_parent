package pers.prover07.guli.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/20 13:14
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan("pers.prover07.guli")
public class GuliServiceOSSApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuliServiceOSSApplication.class, args);
    }

}
