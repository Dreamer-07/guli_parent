package pers.prover07.guli.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import pers.prover07.guli.edu.entity.Teacher;
import pers.prover07.guli.edu.entity.vo.TeacherQueryVo;
import pers.prover07.guli.edu.service.TeacherService;
import pers.prover07.guli.utils.Result;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-18
 */
@Api(tags = "教师管理")
@RestController
@RequestMapping("/api/edu/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("查找所有教师")
    @GetMapping("findAll")
    public Result findAll() {
        List<Teacher> teacherList = teacherService.list(null);
        return Result.ok().data(teacherList);
    }

    @ApiOperation("查找指定教师信息")
    @GetMapping("{id}")
    public Result findById(@ApiParam("教师id") @PathVariable String id) {
        Teacher teacher = teacherService.getById(id);
        return Result.ok().data(teacher);
    }

    @ApiOperation("分页条件检索查找教师")
    @PostMapping("findAll/{pageNo}/{pageSize}")
    public Result findAll(@ApiParam("当前页码") @PathVariable long pageNo,
                          @ApiParam("当前页大小") @PathVariable long pageSize,
                          @ApiParam("查询教师信息条件封装对象") @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {
        Page<Teacher> teacherPage = new Page<>(pageNo, pageSize);

        // 构建条件
        QueryWrapper<Teacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(!StringUtils.isEmpty(teacherQueryVo.getName()), Teacher::getName, teacherQueryVo.getName())
                .eq(!StringUtils.isEmpty(teacherQueryVo.getLevel()), Teacher::getLevel, teacherQueryVo.getLevel())
                .ge(!StringUtils.isEmpty(teacherQueryVo.getBegin()), Teacher::getGmtCreate, teacherQueryVo.getBegin())
                .le(!StringUtils.isEmpty(teacherQueryVo.getEnd()), Teacher::getGmtCreate, teacherQueryVo.getEnd())
                .orderByDesc(Teacher::getGmtCreate);

        teacherService.page(teacherPage, queryWrapper);

        // 获取总记录数
        long total = teacherPage.getTotal();
        // 获取当前页数据
        List<Teacher> records = teacherPage.getRecords();

        // 用 Map 封装数据
        HashMap<String, Object> dataMap = new HashMap<>(2);
        dataMap.put("total", total);
        dataMap.put("records", records);
        return Result.ok().data(dataMap);
    }


    @ApiOperation("添加教师信息")
    @PostMapping("add")
    public Result add(@ApiParam("查询教师信息条件对象") @RequestBody Teacher teacher) {
        boolean flag = teacherService.save(teacher);
        return Result.ok().data(flag);
    }

    @ApiOperation("修改指定教师信息")
    @PutMapping("{id}")
    public Result edit(
            @ApiParam("教师id") @PathVariable String id,
            @ApiParam("需要修改的教师信息对象") @RequestBody Teacher teacher
    ) {
        teacher.setId(id);
        boolean flag = teacherService.updateById(teacher);
        return Result.ok().data(flag);
    }

    @ApiOperation("(逻辑)删除教师信息")
    @DeleteMapping("{id}")
    public Result removeById(@ApiParam("教师id") @PathVariable String id) {
        boolean flag = teacherService.removeById(id);
        return Result.ok().data(flag);
    }

}

