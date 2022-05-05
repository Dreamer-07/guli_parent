package pers.prover07.guli.order.service;

import pers.prover07.guli.order.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-05-03
 */
public interface OrderService extends IService<Order> {

    /**
     * 创建订单并返回订单流水号
     * @param courseId
     * @param memberId
     * @return
     */
    String createOrder(String courseId, String memberId);

    /**
     * 根据流水号获取订单信息
     * @param orderNo
     * @return
     */
    Order getByOrderNo(String orderNo);

    /**
     * 根据 用户和课程 查询用户是否购买过课程
     * @param memberId
     * @param courseId
     * @return
     */
    boolean getOrderStatus(String memberId, String courseId);
}
