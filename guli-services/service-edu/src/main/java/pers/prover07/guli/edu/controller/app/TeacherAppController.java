package pers.prover07.guli.edu.controller.app;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.prover07.guli.edu.entity.Course;
import pers.prover07.guli.edu.entity.Teacher;
import pers.prover07.guli.edu.service.CourseService;
import pers.prover07.guli.edu.service.TeacherService;
import pers.prover07.guli.utils.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/5/1 10:34
 */
@CrossOrigin
@RestController
@RequestMapping("/app/edu/teacher")
public class TeacherAppController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @PostMapping("/page/{pageNo}/{pageSize}")
    public Result page(@PathVariable Long pageNo, @PathVariable Long pageSize) {
        // 查找数据
        Page<Teacher> teacherPage = new Page<>(pageNo, pageSize);
        teacherService.page(teacherPage, null);
        // 转换成 map 结构
        HashMap dataMap = JSON.parseObject(JSON.toJSONString(teacherPage), HashMap.class);
        dataMap.put("hasNext", teacherPage.hasNext());
        dataMap.put("hasPrevious", teacherPage.hasPrevious());
        return Result.ok().data(dataMap);
    }

    @GetMapping("{teacherId}")
    public Result detailInfo(@PathVariable String teacherId) {
        Teacher teacher = teacherService.getById(teacherId);
        List<Course> courseList = courseService.getByTeacherId(teacherId);

        return Result.ok().data(new HashMap<String, Object>(){{
            put("teacher", teacher);
            put("courseList", courseList);
        }});
    }

}
