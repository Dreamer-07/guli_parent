package pers.prover07.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import pers.prover07.guli.edu.client.VdoFeignClient;
import pers.prover07.guli.edu.entity.Course;
import pers.prover07.guli.edu.entity.Video;
import pers.prover07.guli.edu.mapper.VideoMapper;
import pers.prover07.guli.edu.service.VideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.prover07.guli.serviceenv.exception.GuliException;
import pers.prover07.guli.utils.Result;
import pers.prover07.guli.utils.enums.ResultTypeEnum;

import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private VdoFeignClient vdoFeignClient;

    @Override
    public List<Video> getVideoByCourseId(String courseId) {
        return this.list(new LambdaQueryWrapper<Video>().eq(Video::getCourseId, courseId));
    }

    @Override
    public void deleteByCourseId(String courseId) {
        // 获取所有要删除的媒体资源标识
        LambdaQueryWrapper<Video> selectQW = new LambdaQueryWrapper<Video>()
                .select(Video::getVideoSourceId)
                .eq(Video::getCourseId, courseId);
        // 转换数据格式
        List<String> vsIds = this.list(selectQW).stream()
                .map(Video::getVideoSourceId)
                .filter(vsId -> !StringUtils.isEmpty(vsId))
                .collect(Collectors.toList());
        // 如果不为空，通过 feign 进行远程调用删除媒体资源
        if (!vsIds.isEmpty()) {
            Result result = vdoFeignClient.batchDetele(vsIds);
            if (result.getCode().equals(ResultTypeEnum.FEIGN_FALLBACK.getCode())) {
                throw new GuliException(result.getMessage());
            }
        }
        this.remove(new LambdaQueryWrapper<Video>().eq(Video::getCourseId, courseId));
    }

    @Override
    public void removeDetail(String videoId) {
        Video video = this.getById(videoId);
        // 如果存在视频资源标识，也需要删除
        if (!StringUtils.isEmpty(video.getVideoSourceId())) {
            Result result = vdoFeignClient.delete(video.getVideoSourceId());
            if (result.getCode().equals(ResultTypeEnum.FEIGN_FALLBACK.getCode())) {
                throw new GuliException(result.getMessage());
            }
        }
        this.removeById(videoId);
    }
}
