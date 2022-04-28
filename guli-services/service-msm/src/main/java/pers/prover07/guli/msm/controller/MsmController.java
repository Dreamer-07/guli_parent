package pers.prover07.guli.msm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pers.prover07.guli.msm.service.MsmService;
import pers.prover07.guli.utils.Result;
import pers.prover07.guli.utils.enums.ResultTypeEnum;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/27 19:19
 */
@RestController
@RequestMapping("/app/msm/code")
@CrossOrigin
public class MsmController {

    @Autowired
    private MsmService msmService;

    @GetMapping("/phone/{phoneNum}")
    public Result getPhoneCode(@PathVariable String phoneNum) {
        boolean status = msmService.sendPhoneCode(phoneNum);
        return status ? Result.ok() : Result.falid(ResultTypeEnum.SEND_CODE_ERROR);
    }

}
