package pers.prover07.guli.sta.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/5/6 9:10
 */
@FeignClient("service-member")
@Component
public interface MemberFeignClient {

    @GetMapping("/app/member/sta/register-count/{day}")
    int registerCount(@PathVariable("day") String day);
}
