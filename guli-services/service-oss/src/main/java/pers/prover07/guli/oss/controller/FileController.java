package pers.prover07.guli.oss.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pers.prover07.guli.oss.properties.OSSConfigProperties;
import pers.prover07.guli.oss.service.FileService;
import pers.prover07.guli.utils.Result;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/20 13:15
 */
@Api(tags = "OSS 对象存储")
@RestController
@CrossOrigin
@RequestMapping("/api/oss/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("upload")
    public Result uploadFile(@ApiParam(name = "file", value = "请选择要上传的文件", required = true)
                             @RequestPart("file") MultipartFile file) {
        String fileUrl = fileService.uploadFile(file);
        return Result.ok().message("文件上传成功").data(fileUrl);
    }




}
