package pers.prover07.guli.edu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.prover07.guli.utils.Result;

@FeignClient("service-order")
@Component
public interface OrderFeignClient {

    @GetMapping("/app/order/isBuyCourse/{memberId}/{courseId}")
    boolean getOrderStatus(@PathVariable("memberId") String memberId, @PathVariable("courseId") String courseId);

}
