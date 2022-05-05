package pers.prover07.guli.order.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pers.prover07.guli.order.service.PayLogService;
import pers.prover07.guli.utils.Result;
import pers.prover07.guli.utils.enums.ResultTypeEnum;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-05-03
 */
@CrossOrigin
@RestController
@RequestMapping("/app/order/pay-log")
public class PayLogController {

    @Autowired
    private PayLogService payLogService;

    @PostMapping("/native/{orderNo}")
    public Result createNative(@PathVariable String orderNo) {
        Map dataMap = payLogService.createNative(orderNo);
        return Result.ok().data(dataMap);
    }

    @GetMapping("/native/{orderNo}")
    public Result getNativeInfo(@PathVariable String orderNo) {
        Map infoMap = payLogService.getNativeInfo(orderNo);
        /*
         * 微信支付状态查询字段
         */
        String wxNativeState = "trade_state";
        String wxNativeStateSuccess = "SUCCESS";
        if (wxNativeStateSuccess.equals(infoMap.get(wxNativeState))) {
            payLogService.addPayLog(infoMap);
            return Result.ok();
        }
        return Result.ok(ResultTypeEnum.WX_PAYING);
    }

}

