package pers.prover07.guli.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pers.prover07.guli.order.client.OrderEduFeignClient;
import pers.prover07.guli.order.client.OrderMemberFeignClient;
import pers.prover07.guli.order.entity.Order;
import pers.prover07.guli.order.mapper.OrderMapper;
import pers.prover07.guli.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.prover07.guli.serviceenv.vo.OrderCourseVo;
import pers.prover07.guli.serviceenv.vo.OrderMemberVo;

import java.util.UUID;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-05-03
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private OrderMemberFeignClient orderMemberFeignClient;

    @Autowired
    private OrderEduFeignClient orderEduFeignClient;

    @Override

    public String createOrder(String courseId, String memberId) {
        OrderCourseVo orderCourseInfo = orderEduFeignClient.getOrderCourseInfo(courseId);
        OrderMemberVo orderMemberInfo = orderMemberFeignClient.getOrderMemberInfo(memberId);
        Order order = new Order();
        // 拷贝数据
        BeanUtils.copyProperties(orderCourseInfo, order);
        BeanUtils.copyProperties(orderMemberInfo, order);
        // 设置流水号
        order.setOrderNo(UUID.randomUUID().toString().replace("-", ""));
        // 设置微信支付
        order.setPayType(1);
        // 设置支付状态
        order.setStatus(0);

        this.save(order);
        return order.getOrderNo();
    }

    @Override
    public Order getByOrderNo(String orderNo) {
        return this.getOne(new LambdaQueryWrapper<Order>().eq(Order::getOrderNo, orderNo));
    }
}
