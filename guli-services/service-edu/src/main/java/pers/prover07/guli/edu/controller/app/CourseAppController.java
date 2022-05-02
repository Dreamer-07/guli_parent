package pers.prover07.guli.edu.controller.app;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.prover07.guli.edu.entity.Course;
import pers.prover07.guli.edu.entity.vo.ChapterSaveVo;
import pers.prover07.guli.edu.entity.vo.CourseAppDetailVo;
import pers.prover07.guli.edu.entity.vo.CourseAppQueryVo;
import pers.prover07.guli.edu.service.ChapterService;
import pers.prover07.guli.edu.service.CourseService;
import pers.prover07.guli.utils.Result;

import java.util.HashMap;
import java.util.List;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/5/1 10:34
 */
@CrossOrigin
@RestController
@RequestMapping("/app/edu/course")
public class CourseAppController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @PostMapping("/page/{pageNo}/{pageSize}")
    public Result pageByCondition(@PathVariable Long pageNo, @PathVariable Long pageSize,
                                  @RequestBody(required = false) CourseAppQueryVo courseAppQueryVo) {
        // 根据条件查找数据
        Page<Course> coursePage = new Page<>(pageNo, pageSize);
        courseService.pageByCondition(coursePage, courseAppQueryVo);

        // 转换为 map
        HashMap dataMap = JSON.parseObject(JSON.toJSONString(coursePage), HashMap.class);
        dataMap.put("hasPrevious", coursePage.hasPrevious());
        dataMap.put("hasNext", coursePage.hasNext());

        return Result.ok().data(dataMap);
    }

    @GetMapping("{courseId}")
    public Result detail(@PathVariable String courseId) {
        CourseAppDetailVo courseAppDetailVo = courseService.getCourseDetailInfo(courseId);

        List<ChapterSaveVo> chapterSaveVos = chapterService.findAllChapterByCourseId(courseId);

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("courseInfo", courseAppDetailVo);
        dataMap.put("chapterList", chapterSaveVos);

        return Result.ok().data(dataMap);
    }
}
