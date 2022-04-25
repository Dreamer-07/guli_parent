package pers.prover07.guli.vdo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.prover07.guli.utils.Result;
import pers.prover07.guli.vdo.service.VdoFileService;

import java.util.List;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/25 7:09
 */
@Api(tags = "媒体资源管理")
@CrossOrigin
@RestController
@RequestMapping("/api/vdo/file")
public class VdoFileController {

    @Autowired
    private VdoFileService vdoFileService;

    @PostMapping("upload")
    public Result upload(@ApiParam(value = "上传的文件", name = "file", required = true)
                         @RequestParam("file") MultipartFile multipartFile) {
        String videoId = vdoFileService.uploadFile(multipartFile);
        return Result.ok().data(videoId);
    }

    @ApiOperation("删除视频")
    @DeleteMapping("{videoSourceId}")
    public Result delete(@ApiParam("视频资源标识") @PathVariable String videoSourceId) {
        vdoFileService.delete(videoSourceId);
        return Result.ok();
    }

    @ApiOperation("批量删除视频")
    @DeleteMapping("batch-delete")
    public Result batchDetele(@ApiParam("视频资源标识集合") @RequestParam List<String> videoSourceIds) {
        vdoFileService.batchDetele(videoSourceIds);
        return Result.ok();
    }

}
