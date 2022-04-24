package pers.prover07.guli.edu.entity.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程章节保存信息
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/23 14:23
 */
@Data
public class ChapterSaveVo {

    private String id;

    private String title;

    private List<VideoSaveVo> videoSaveVos = new ArrayList<>();
}
