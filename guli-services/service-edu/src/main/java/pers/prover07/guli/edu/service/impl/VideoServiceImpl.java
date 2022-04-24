package pers.prover07.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import pers.prover07.guli.edu.entity.Video;
import pers.prover07.guli.edu.mapper.VideoMapper;
import pers.prover07.guli.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-22
 */
@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Override
    public List<Video> getVideoByCourseId(String courseId) {
        return this.list(new LambdaQueryWrapper<Video>().eq(Video::getCourseId, courseId));
    }

    @Override
    public void deleteByCourseId(String courseId) {
        this.remove(new LambdaQueryWrapper<Video>().eq(Video::getCourseId, courseId));
    }
}
