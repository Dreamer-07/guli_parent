package pers.prover07.guli.sta.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import pers.prover07.guli.sta.client.MemberFeignClient;
import pers.prover07.guli.sta.entity.StatisticsDaily;
import pers.prover07.guli.sta.mapper.StatisticsDailyMapper;
import pers.prover07.guli.sta.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.prover07.guli.utils.RandomUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-05-06
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private MemberFeignClient memberFeignClient;

    @Override
    public void createStatisticsByDay(String day) {
        // 判断今天的数据是否已生成，如果已生成就是修改
        LambdaQueryWrapper<StatisticsDaily> selectLqw = new LambdaQueryWrapper<StatisticsDaily>()
                .eq(StatisticsDaily::getDateCalculated, day);
        StatisticsDaily statisticsDaily = this.getOne(selectLqw);

        // 远程调用获取数据
        int registerCount = memberFeignClient.registerCount(day);
        statisticsDaily.setRegisterNum(registerCount);
        statisticsDaily.setLoginNum(new Random().nextInt(200));
        statisticsDaily.setVideoViewNum(new Random().nextInt(200));
        statisticsDaily.setCourseNum(new Random().nextInt(200));
        statisticsDaily.setDateCalculated(day);

        if (statisticsDaily.getId() != null) {
            this.updateById(statisticsDaily);
        } else {
            this.save(statisticsDaily);
        }

    }

    @Override
    public Map showDataFromDate(String dataType, String beginDate, String endDate) {
        QueryWrapper<StatisticsDaily> selectQW = new QueryWrapper<StatisticsDaily>()
                .select(dataType, "date_calculated")
                .between("date_calculated", beginDate, endDate);
        // 获取数据
        List<StatisticsDaily> statisticsDailyList = this.list(selectQW);
        // 将数据封装成 x,y 轴的数据
        ArrayList<String> xData = new ArrayList<>(statisticsDailyList.size());
        ArrayList<Integer> yData = new ArrayList<>(statisticsDailyList.size());
        for (StatisticsDaily statisticsDaily : statisticsDailyList) {
            xData.add(statisticsDaily.getDateCalculated());
            switch (dataType) {
                case "register_num":
                    yData.add(statisticsDaily.getRegisterNum());
                    break;
                case "login_num":
                    yData.add(statisticsDaily.getLoginNum());
                    break;
                case "video_view_num":
                    yData.add(statisticsDaily.getVideoViewNum());
                    break;
                case "course_num":
                    yData.add(statisticsDaily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        return new HashMap<String, List<?>>(){{
            put("xData", xData);
            put("yData", yData);
        }};
    }

    @Override
    public StatisticsDaily getOne(Wrapper<StatisticsDaily> queryWrapper) {
        StatisticsDaily statisticsDaily = super.getOne(queryWrapper);
        return statisticsDaily == null ? new StatisticsDaily() : statisticsDaily;
    }
}
