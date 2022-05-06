package pers.prover07.guli.sta.service;

import pers.prover07.guli.sta.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-05-06
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    /**
     * 以日期为单位创建网站统计
     * @param day
     */
    void createStatisticsByDay(String day);

    /**
     * 显示指定日期内的数据
     * @param dataType
     * @param beginDate
     * @param endDate
     * @return
     */
    Map showDataFromDate(String dataType, String beginDate, String endDate);
}
