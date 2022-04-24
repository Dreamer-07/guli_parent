package pers.prover07.guli.edu.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import pers.prover07.guli.edu.entity.vo.SubjectTreeVo;
import pers.prover07.guli.edu.service.SubjectService;
import pers.prover07.guli.utils.Result;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-22
 */
@Api(tags = "课程分类管理")
@CrossOrigin
@RestController
@RequestMapping("/api/edu/subject")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/upload/xlsx")
    @ApiOperation("上传execl以添加课程分类信息")
    public Result uploadXlsxData(@ApiParam(name = "file", value = "请选择要上传的 xlxs 文件", required = true)
                                 @RequestParam("file") MultipartFile file) {
        subjectService.addXlsxData(file);
        return Result.ok();
    }

    @GetMapping("/tree")
    @ApiOperation("获取指定树型结构数据")
    public Result findAllInTree() {
        List<SubjectTreeVo> subjectTreeVoList = subjectService.findAllInTree();
        return Result.ok().data(subjectTreeVoList);
    }

}

