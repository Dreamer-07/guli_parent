package pers.prover07.guli.vdo.controller.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.prover07.guli.utils.Result;
import pers.prover07.guli.vdo.service.VdoFileService;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/5/2 12:38
 */
@CrossOrigin
@RestController
@RequestMapping("/app/vdo/file")
public class VdoAppFileController {

    @Autowired
    private VdoFileService vdoFileService;

    @GetMapping("play-auth/{videoSourceId}")
    public Result getPlayAuth(@PathVariable String videoSourceId) {
        String playAuth = vdoFileService.getPlayAuth(videoSourceId);
        return Result.ok().data(playAuth);
    }

}
