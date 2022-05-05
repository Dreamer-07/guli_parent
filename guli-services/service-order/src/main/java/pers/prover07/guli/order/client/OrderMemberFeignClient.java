package pers.prover07.guli.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.prover07.guli.serviceenv.vo.OrderMemberVo;

@FeignClient("service-member")
@Component
public interface OrderMemberFeignClient {

    @GetMapping("/app/member/order/{memberId}")
    OrderMemberVo getOrderMemberInfo(@PathVariable("memberId") String memberId);

}
