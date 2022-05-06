package pers.prover07.guli.sta.controller;


import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pers.prover07.guli.sta.service.StatisticsDailyService;
import pers.prover07.guli.utils.Result;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-05-06
 */
@Api(tags = "每日统计接口管理")
@CrossOrigin
@RestController
@RequestMapping("/api/sta/daily")
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    @PostMapping("/create/{day}")
    public Result createStatisticsByDay(@PathVariable String day) {
        statisticsDailyService.createStatisticsByDay(day);
        return Result.ok();
    }

    @GetMapping("/show/{dataType}/{beginDate}/{endDate}")
    public Result showDataFromDate(@PathVariable String dataType,
                                   @PathVariable String beginDate,
                                   @PathVariable String endDate) {
        Map dataMap = statisticsDailyService.showDataFromDate(dataType, beginDate, endDate);
        return Result.ok().data(dataMap);
    }

}

