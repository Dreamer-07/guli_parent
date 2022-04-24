package pers.prover07.guli.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.prover07.guli.edu.entity.Course;
import pers.prover07.guli.edu.entity.Teacher;
import pers.prover07.guli.edu.entity.vo.CourseInfoVo;
import pers.prover07.guli.edu.entity.vo.CoursePublishVo;
import pers.prover07.guli.edu.entity.vo.CourseQueryVo;
import pers.prover07.guli.edu.entity.vo.TeacherQueryVo;
import pers.prover07.guli.edu.service.CourseService;
import pers.prover07.guli.utils.Result;
import pers.prover07.guli.utils.enums.CourseStatusEnum;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-22
 */
@Api(tags = "课程管理")
@RestController
@RequestMapping("/api/edu/course")
@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation("分页条件检索课程列表")
    @PostMapping("findAll/{pageNo}/{pageSize}")
    public Result findAll(@ApiParam("当前页码") @PathVariable long pageNo,
                          @ApiParam("当前页大小") @PathVariable long pageSize,
                          @ApiParam("查询教师信息条件封装对象") @RequestBody(required = false) CourseQueryVo courseQueryVo) {
        Page<Course> coursePage = new Page<>(pageNo, pageSize);

        // 根据条件检索分页
        courseService.pageByCondition(coursePage, courseQueryVo);

        // 获取总记录数
        long total = coursePage.getTotal();
        // 获取当前页数据
        List<Course> records = coursePage.getRecords();

        // 用 Map 封装数据
        HashMap<String, Object> dataMap = new HashMap<>(2);
        dataMap.put("total", total);
        dataMap.put("records", records);
        return Result.ok().data(dataMap);
    }

    @ApiOperation("添加课程信息")
    @PostMapping("save")
    public Result save(@ApiParam(value = "课程信息数据集", required = true)
                       @RequestBody CourseInfoVo courseSaveVo) {
        String courseId = courseService.saveDetail(courseSaveVo);
        return Result.ok().data(courseId);
    }

    @ApiOperation("获取课程信息")
    @GetMapping("{courseId}")
    public Result getCourseById(@ApiParam("课程标识") @PathVariable String courseId) {
        // Course course = courseService.getById(courseId);
        CourseInfoVo courseInfoVo = courseService.getDetail(courseId);
        return Result.ok().data(courseInfoVo);
    }

    @ApiOperation("修改课程信息")
    @PutMapping("{courseId}")
    public Result update(@ApiParam("课程标识") @PathVariable String courseId,
                                   @RequestBody CourseInfoVo courseInfoVo){
        courseService.updateDetail(courseInfoVo);
        return Result.ok();
    }

    @ApiOperation("获取课程发布信息")
    @GetMapping("publish-info/{courseId}")
    public Result getCoursePublishInfo(@PathVariable String courseId) {
        CoursePublishVo coursePublishVo = courseService.getCoursePublishInfo(courseId);
        return Result.ok().data(coursePublishVo);
    }

    @ApiOperation("修改课程发布信息")
    @PutMapping("publish-info/normal/{courseId}")
    public Result updateCoursePublishInfo(@PathVariable String courseId) {
        Course course = new Course();
        course.setId(courseId);
        course.setStatus(CourseStatusEnum.Normal.getValue());
        courseService.updateById(course);
        return Result.ok();
    }

    @ApiOperation("删除课程")
    @DeleteMapping("{courseId}")
    public Result delete(@ApiParam("课程标识") @PathVariable String courseId) {
        courseService.deleteDetail(courseId);
        return Result.ok();
    }

}

