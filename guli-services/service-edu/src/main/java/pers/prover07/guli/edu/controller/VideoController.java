package pers.prover07.guli.edu.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pers.prover07.guli.edu.entity.Video;
import pers.prover07.guli.edu.service.VideoService;
import pers.prover07.guli.utils.Result;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-22
 */
@Api(tags = "课时小节管理")
@CrossOrigin
@RestController
@RequestMapping("/api/edu/video")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/save")
    @ApiOperation("添加小节")
    public Result save(@ApiParam("小节信息") @RequestBody Video video) {
        videoService.save(video);
        return Result.ok();
    }

    @DeleteMapping("{videoId}")
    @ApiOperation("删除小节")
    public Result delete(@ApiParam("小节id") @PathVariable String videoId) {
        videoService.removeDetail(videoId);
        return Result.ok();
    }

}

