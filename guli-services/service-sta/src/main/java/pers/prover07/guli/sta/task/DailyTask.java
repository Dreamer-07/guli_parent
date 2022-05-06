package pers.prover07.guli.sta.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pers.prover07.guli.sta.service.StatisticsDailyService;
import pers.prover07.guli.utils.DateUtil;

import java.util.Date;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/5/6 13:15
 */
@Component
public class DailyTask {

    @Autowired
    private StatisticsDailyService statisticsDailyService;

    // @Scheduled(cron = "0/5 * * * * ?")
    // public void test() {
    //     System.out.println("*********++++++++++++*****执行了");
    // }

    /**
     * 每天凌晨1点执行定时创建前一天的应用数据统计
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void createYesterdaySta() {
        //获取上一天的日期
        String day = DateUtil.formatDate(DateUtil.addDays(new Date(), -1));
        statisticsDailyService.createStatisticsByDay(day);
    }

}
