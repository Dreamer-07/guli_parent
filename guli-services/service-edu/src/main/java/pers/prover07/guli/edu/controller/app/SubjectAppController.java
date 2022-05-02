package pers.prover07.guli.edu.controller.app;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.prover07.guli.edu.entity.vo.SubjectTreeVo;
import pers.prover07.guli.edu.service.SubjectService;
import pers.prover07.guli.utils.Result;

import java.util.List;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/5/1 15:09
 */
@CrossOrigin
@RestController
@RequestMapping("/app/edu/subject")
public class SubjectAppController {

    @Autowired
    private SubjectService subjectService;

    @GetMapping("/tree")
    @ApiOperation("获取指定树型结构数据")
    public Result findAllInTree() {
        List<SubjectTreeVo> subjectTreeVoList = subjectService.findAllInTree();
        return Result.ok().data(subjectTreeVoList);
    }

}
