package pers.prover07.guli.edu.service;

import pers.prover07.guli.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.prover07.guli.edu.entity.vo.ChapterSaveVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-22
 */
public interface ChapterService extends IService<Chapter> {

    /**
     * 根据 courseId(课程标识) 获取所有课程章节信息
     * @param courseId
     * @return
     */
    List<ChapterSaveVo> findAllChapterByCourseId(String courseId);


    /**
     * 判断是否存在小节信息(video)决定是否删除
     * @param chapterId
     */
    void removeByHasVideo(String chapterId);

    /**
     * 根据课程id删除章节信息
     * @param courseId
     */
    void deleteByCourseId(String courseId);
}
