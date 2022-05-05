package pers.prover07.guli.order.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pers.prover07.guli.order.entity.Order;
import pers.prover07.guli.order.service.OrderService;
import pers.prover07.guli.utils.JwtUtils;
import pers.prover07.guli.utils.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-05-03
 */
@CrossOrigin
@RestController
@RequestMapping("/app/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("create/{courseId}")
    public Result createOrder(@PathVariable String courseId, HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        String orderNo = orderService.createOrder(courseId, memberId);
        return Result.ok().data(orderNo);
    }

    @GetMapping("info/{orderNo}")
    public Result getOrderInfo(@PathVariable String orderNo) {
        Order order = orderService.getByOrderNo(orderNo);
        return Result.ok().data(order);
    }

}

