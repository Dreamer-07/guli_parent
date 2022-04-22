package pers.prover07.guli.edu.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/22 15:05
 */
@Data
public class SubjectTreeVo {

    private String id;

    private String label;

    private List<SubjectTreeVo> children;

}
