package pers.prover07.guli.utils.enums;

import lombok.Getter;

/**
 * 课程状态
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/24 10:34
 */
@Getter
public enum CourseStatusEnum {

    /**
     * 课程已经发布
     */
    Normal("Normal"),

    /**
     * 课程暂未发布
     */
    Draft("Draft");

    private CourseStatusEnum(String value) {
        this.value = value;
    }

    private final String value;
}
