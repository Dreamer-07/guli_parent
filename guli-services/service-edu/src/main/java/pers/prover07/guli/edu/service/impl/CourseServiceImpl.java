package pers.prover07.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import pers.prover07.guli.edu.entity.Course;
import pers.prover07.guli.edu.entity.CourseDescription;
import pers.prover07.guli.edu.entity.Teacher;
import pers.prover07.guli.edu.entity.vo.*;
import pers.prover07.guli.edu.mapper.CourseMapper;
import pers.prover07.guli.edu.service.*;
import pers.prover07.guli.serviceenv.vo.OrderCourseVo;
import pers.prover07.guli.serviceenv.vo.OrderMemberVo;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-22
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

    @Autowired
    private TeacherService teacherService;

    @Override
    public String saveDetail(CourseInfoVo courseInfoVo) {
        // 保存 课程信息
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        this.save(course);

        // 保存课程详细信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(course.getId());
        courseDescriptionService.save(courseDescription);

        return course.getId();
    }

    @Override
    public void updateDetail(CourseInfoVo courseInfoVo) {
        // 修改课程信息
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        this.updateById(course);

        // 修改课程简介
        CourseDescription courseDescription = new CourseDescription();
        BeanUtils.copyProperties(courseInfoVo, courseDescription);
        courseDescriptionService.updateById(courseDescription);
    }

    @Override
    public CourseInfoVo getDetail(String courseId) {
        Course course = this.getById(courseId);
        CourseDescription courseDescription = courseDescriptionService.getById(courseId);

        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course, courseInfoVo);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public CoursePublishVo getCoursePublishInfo(String courseId) {
        return baseMapper.getCoursePublishInfo(courseId);
    }

    @Override
    public void pageByCondition(Page<Course> coursePage, CourseQueryVo courseQueryVo) {
        LambdaQueryWrapper<Course> lqw = new LambdaQueryWrapper<Course>()
                .like(!StringUtils.isEmpty(courseQueryVo.getTitle()), Course::getTitle, courseQueryVo.getTitle())
                .eq(!StringUtils.isEmpty(courseQueryVo.getStatus()), Course::getStatus, courseQueryVo.getStatus())
                .ge(!StringUtils.isEmpty(courseQueryVo.getBegin()), Course::getGmtCreate, courseQueryVo.getBegin())
                .le(!StringUtils.isEmpty(courseQueryVo.getEnd()), Course::getGmtCreate, courseQueryVo.getEnd());

        this.page(coursePage, lqw);
    }

    @Override
    public void pageByCondition(Page<Course> coursePage, CourseAppQueryVo courseAppQueryVo) {
        LambdaQueryWrapper<Course> lqw = new LambdaQueryWrapper<Course>()
                .eq(!StringUtils.isEmpty(courseAppQueryVo.getSubjectParentId()), Course::getSubjectParentId, courseAppQueryVo.getSubjectParentId())
                .eq(!StringUtils.isEmpty(courseAppQueryVo.getSubjectId()), Course::getSubjectId, courseAppQueryVo.getSubjectId())
                .orderByDesc(!StringUtils.isEmpty(courseAppQueryVo.getBuyCountSort()), Course::getBuyCount)
                .orderByDesc(!StringUtils.isEmpty(courseAppQueryVo.getGmtCreateSort()), Course::getGmtCreate)
                .orderByDesc(!StringUtils.isEmpty(courseAppQueryVo.getPriceSort()), Course::getPrice);

        this.page(coursePage, lqw);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = {Exception.class})
    @Override
    public void deleteDetail(String courseId) {
        // 删除小节
        videoService.deleteByCourseId(courseId);
        // 删除章节
        chapterService.deleteByCourseId(courseId);
        // 删除简介
        courseDescriptionService.removeById(courseId);
        // 删除课程
        this.removeById(courseId);
    }

    @Override
    public List<Course> getIndexRankList() {
        LambdaQueryWrapper<Course> lqw = new LambdaQueryWrapper<Course>()
                .orderByDesc(Course::getId)
                .last("limit 8");
        return this.list(lqw);
    }

    @Override
    public List<Course> getByTeacherId(String teacherId) {
        LambdaQueryWrapper<Course> lqw = new LambdaQueryWrapper<Course>()
                .eq(Course::getTeacherId, teacherId);
        return this.list(lqw);
    }

    @Override
    public CourseAppDetailVo getCourseDetailInfo(String courseId) {
        return baseMapper.getCourseDatailInfo(courseId);
    }

    @Override
    public OrderCourseVo getOrderCourseInfo(String courseId) {
        Course course = this.getById(courseId);
        Teacher teacher = teacherService.getById(course.getTeacherId());
        return new OrderCourseVo(course.getId(), course.getTitle(), course.getCover(), teacher.getName());
    }

}
