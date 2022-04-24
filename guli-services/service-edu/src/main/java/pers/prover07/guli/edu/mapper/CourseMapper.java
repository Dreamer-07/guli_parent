package pers.prover07.guli.edu.mapper;

import pers.prover07.guli.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import pers.prover07.guli.edu.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-22
 */
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo getCoursePublishInfo(String courseId);

}
