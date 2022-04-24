package pers.prover07.guli.edu.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pers.prover07.guli.edu.entity.Chapter;
import pers.prover07.guli.edu.entity.vo.ChapterSaveVo;
import pers.prover07.guli.edu.service.ChapterService;
import pers.prover07.guli.utils.Result;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-22
 */
@Api(tags = "课程章节管理")
@CrossOrigin
@RestController
@RequestMapping("/api/edu/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    @ApiOperation("根据课程标识查找所有章节")
    @GetMapping("/findAll/{courseId}")
    public Result findAllChapterByCourseId(@ApiParam("课程标识") @PathVariable String courseId) {
        List<ChapterSaveVo> chapterSaveVoList = chapterService.findAllChapterByCourseId(courseId);
        return Result.ok().data(chapterSaveVoList);
    }

    @ApiOperation("根据章节标识获取某个详细标识")
    @GetMapping("{chapterId}")
    public Result findChapterDetail(@ApiParam("章节标识") @PathVariable String chapterId) {
        Chapter chapter = chapterService.getById(chapterId);
        return Result.ok().data(chapter);
    }

    @ApiOperation("添加章节信息")
    @PostMapping("/save")
    public Result save(@ApiParam("章节信息") @RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return Result.ok();
    }

    @ApiOperation("修改章节信息")
    @PutMapping("{chapterId}")
    public Result update(@ApiParam("章节信息") @RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return Result.ok();
    }

    @ApiOperation("删除章节信息")
    @DeleteMapping("{chapterId}")
    public Result delete(@ApiParam("章节标识") @PathVariable String chapterId) {
        chapterService.removeByHasVideo(chapterId);
        return Result.ok();
    }
}

