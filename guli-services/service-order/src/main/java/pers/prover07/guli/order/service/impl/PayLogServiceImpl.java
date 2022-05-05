package pers.prover07.guli.order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
import org.apache.http.client.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import pers.prover07.guli.order.entity.Order;
import pers.prover07.guli.order.entity.PayLog;
import pers.prover07.guli.order.mapper.PayLogMapper;
import pers.prover07.guli.order.properties.WxPayProperties;
import pers.prover07.guli.order.service.OrderService;
import pers.prover07.guli.order.service.PayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.prover07.guli.order.utils.HttpClientUtils;
import pers.prover07.guli.serviceenv.exception.GuliException;
import pers.prover07.guli.utils.enums.ResultTypeEnum;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-05-03
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog> implements PayLogService {

    @Autowired
    private WxPayProperties wxPayProperties;

    @Autowired
    private OrderService orderService;

    @Override
    public Map createNative(String orderNo) {
        // 获取订单信息
        Order order = orderService.getByOrderNo(orderNo);
        if (order == null) {
            throw new GuliException(ResultTypeEnum.ORDER_NO_FOUND);
        }
        try {
            HashMap<String, String> wxPayReqInfoMap = getWxPayRequestInfoMap(order);
            // 创建一个 Http 客户端用来发送请求
            HttpClientUtils client = new HttpClientUtils("https://api.mch.weixin.qq.com/pay/unifiedorder");
            client.setXmlParam(WXPayUtil.generateSignedXml(wxPayReqInfoMap, wxPayProperties.getPartnerKey()));
            client.setHttps(true);
            // 发送请求
            client.post();

            // 得到响应数据并进行封装
            String contentXml = client.getContent();
            Map<String, String> responseMap = WXPayUtil.xmlToMap(contentXml);

            // 封装返回的结果集合
            return new HashMap<String, Object>() {{
                // 订单好
                put("out_trade_no", orderNo);
                // 课程标识
                put("course_id", order.getCourseId());
                // 价格
                put("total_fee", order.getTotalFee());
                // 响应状态码
                put("result_code", responseMap.get("result_code"));
                // 二维码地址
                put("code_url", responseMap.get("code_url"));
            }};
        } catch (Exception e) {
            throw new GuliException("微信支付出错啦!!");
        }
    }

    @Override
    public Map getNativeInfo(String orderNo) {
        // 1. 获取请求参数
        try {
            HashMap<String, String> wxPayStateReqInfoMap = getWxPayStateReqInfoMap(orderNo);
            // 2. 设置 httpClient 发送请求
            HttpClientUtils client = new HttpClientUtils("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(wxPayStateReqInfoMap, wxPayProperties.getPartnerKey()));
            client.setHttps(true);
            client.post();

            // 获取响应结果
            String contentXml = client.getContent();
            // 转换成 Map 后直接返回
            return WXPayUtil.xmlToMap(contentXml);
        } catch (Exception exception) {
            throw new GuliException("查询订单出错啦!!");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addPayLog(Map<String, String> infoMap) {
        // 查询是否已经支付过了
        String orderNo = infoMap.get("out_trade_no");
        Order order = orderService.getByOrderNo(orderNo);
        if (order.getStatus() == 1) {
            return;
        }
        // 修改订单状态
        order.setStatus(1);
        orderService.updateById(order);

        // 添加支付记录
        PayLog payLog = new PayLog();
        // 支付订单号
        payLog.setOrderNo(order.getOrderNo());
        payLog.setPayTime(new Date());
        // 支付类型(1: 微信)
        payLog.setPayType(1);
        // 总金额(分)
        payLog.setTotalFee(order.getTotalFee());
        //支付状态
        payLog.setTradeState(infoMap.get("trade_state"));
        payLog.setTransactionId(infoMap.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(infoMap));
        // 保存到数据库
        this.save(payLog);
    }

    private HashMap<String, String> getWxPayRequestInfoMap(Order order) {
        // 设置请求信息
        HashMap<String, String> requestMap = new HashMap<>(9);
        requestMap.put("appid", wxPayProperties.getAppId());
        requestMap.put("mch_id", wxPayProperties.getPartner());
        requestMap.put("nonce_str", WXPayUtil.generateNonceStr());
        requestMap.put("body", order.getCourseTitle());
        requestMap.put("out_trade_no", order.getOrderNo());
        requestMap.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
        requestMap.put("spbill_create_ip", "127.0.0.1");
        requestMap.put("notify_url", wxPayProperties.getNotifyUrl());
        requestMap.put("trade_type", "NATIVE");
        return requestMap;
    }

    private HashMap<String, String> getWxPayStateReqInfoMap(String orderNo) {
        return new HashMap<String, String>(4) {{
            put("appid", wxPayProperties.getAppId());
            put("mch_id", wxPayProperties.getPartner());
            put("out_trade_no", orderNo);
            put("nonce_str", WXPayUtil.generateNonceStr());
        }};
    }
}
