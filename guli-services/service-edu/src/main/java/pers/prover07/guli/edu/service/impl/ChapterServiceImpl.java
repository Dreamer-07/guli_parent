package pers.prover07.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pers.prover07.guli.edu.entity.Chapter;
import pers.prover07.guli.edu.entity.Video;
import pers.prover07.guli.edu.entity.vo.ChapterSaveVo;
import pers.prover07.guli.edu.entity.vo.VideoSaveVo;
import pers.prover07.guli.edu.mapper.ChapterMapper;
import pers.prover07.guli.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.prover07.guli.edu.service.VideoService;
import pers.prover07.guli.serviceenv.exception.GuliException;
import pers.prover07.guli.utils.enums.ResultTypeEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-22
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    @Override
    public List<ChapterSaveVo> findAllChapterByCourseId(String courseId) {
        // 查询所有课程章节
        List<Chapter> chapters = this.list(new LambdaQueryWrapper<Chapter>()
                .eq(Chapter::getCourseId, courseId)
                .orderByAsc(Chapter::getSort)
        );
        // 查询所有视频小姐
        List<Video> videos = videoService.getVideoByCourseId(courseId);
        // 封装数据
        Map<String, ChapterSaveVo> chapterSaveVoMap = chapters.stream().map(chapter -> {
            ChapterSaveVo chapterSaveVo = new ChapterSaveVo();
            BeanUtils.copyProperties(chapter, chapterSaveVo);
            return chapterSaveVo;
        }).collect(Collectors.toMap(ChapterSaveVo::getId, chapterSaveVo -> chapterSaveVo));

        videos.forEach(video -> {
            VideoSaveVo videoSaveVo = new VideoSaveVo();
            BeanUtils.copyProperties(video, videoSaveVo);
            chapterSaveVoMap.get(video.getChapterId()).getVideoSaveVos().add(videoSaveVo);
        });

        return new ArrayList<>(chapterSaveVoMap.values());
    }

    @Override
    public void removeByHasVideo(String chapterId) {
        // 获取小节数量
        int videoCount = videoService.count(new LambdaQueryWrapper<Video>().eq(Video::getChapterId, chapterId));
        // 如果大于 0 表示有数据就不删除
        if (videoCount > 0) {
            throw new GuliException(ResultTypeEnum.HAS_VIDEO);
        }
        this.removeById(chapterId);
    }

    @Override
    public void deleteByCourseId(String courseId) {
        this.remove(new LambdaQueryWrapper<Chapter>().eq(Chapter::getCourseId, courseId));
    }
}
