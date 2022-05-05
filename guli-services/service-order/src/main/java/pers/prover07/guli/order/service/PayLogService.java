package pers.prover07.guli.order.service;

import pers.prover07.guli.order.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-05-03
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 根据 orderNo 创建微信支付
     * @param orderNo
     * @return
     */
    Map createNative(String orderNo);

    /**
     * 根据 orderNo 获取微信支付状况
     * @param orderNo
     * @return
     */
    Map getNativeInfo(String orderNo);

    /**
     * 添加支付记录
     *
     * @param infoMap
     */
    void addPayLog(Map<String, String> infoMap);
}
