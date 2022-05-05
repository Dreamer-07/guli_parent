package pers.prover07.guli.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pers.prover07.guli.serviceenv.vo.OrderCourseVo;

@FeignClient("service-edu")
@Component
public interface OrderEduFeignClient {

    @GetMapping("/app/edu/course/order/{courseId}")
    OrderCourseVo getOrderCourseInfo(@PathVariable("courseId") String courseId);

}
