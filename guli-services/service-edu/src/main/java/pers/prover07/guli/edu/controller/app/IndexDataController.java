package pers.prover07.guli.edu.controller.app;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.prover07.guli.edu.entity.Course;
import pers.prover07.guli.edu.entity.Teacher;
import pers.prover07.guli.edu.service.CourseService;
import pers.prover07.guli.edu.service.TeacherService;
import pers.prover07.guli.utils.Result;

import java.util.HashMap;
import java.util.List;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/26 15:11
 */
@Api(tags = "首页数据")
@CrossOrigin
@RestController
@RequestMapping("/app/edu/index")
public class IndexDataController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @Cacheable(value = "service-edu", key = "'index::data'")
    @ApiOperation("获取首页数据")
    @GetMapping("data")
    public Result data() {
        // 获取名牌讲师
        List<Teacher> teacherList = teacherService.getIndexHighLevelList();
        // 获取热门课程
        List<Course> courseList = courseService.getIndexRankList();

        HashMap<String, List<?>> dataMap = new HashMap<>(2);
        dataMap.put("teacherList", teacherList);
        dataMap.put("courseList", courseList);

        return Result.ok().data(dataMap);
    }


}
