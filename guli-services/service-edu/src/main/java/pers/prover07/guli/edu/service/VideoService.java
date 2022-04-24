package pers.prover07.guli.edu.service;

import pers.prover07.guli.edu.entity.Video;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-22
 */
public interface VideoService extends IService<Video> {

    /**
     * 根据 courseId(课程标识) 查询所有小节信息
     * @param courseId
     * @return
     */
    List<Video> getVideoByCourseId(String courseId);

    /**
     * 根据课程id删除小节信息
     * @param courseId
     */
    void deleteByCourseId(String courseId);
}
