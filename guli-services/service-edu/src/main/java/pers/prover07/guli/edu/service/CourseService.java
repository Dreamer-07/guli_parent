package pers.prover07.guli.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.prover07.guli.edu.entity.Course;
import pers.prover07.guli.edu.entity.vo.CourseInfoVo;
import pers.prover07.guli.edu.entity.vo.CoursePublishVo;
import pers.prover07.guli.edu.entity.vo.CourseQueryVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-22
 */
public interface CourseService extends IService<Course> {

    /**
     * 保存详细信息
     * @param courseInfoVo
     * @return
     */
    String saveDetail(CourseInfoVo courseInfoVo);

    /**
     * 修改课程详细信息
     * @param courseInfoVo
     */
    void updateDetail(CourseInfoVo courseInfoVo);

    /**
     * 获取课程详细信息
     * @param courseId
     * @return
     */
    CourseInfoVo getDetail(String courseId);

    /**
     * 获取课程发布信息
     * @param courseId
     * @return
     */
    CoursePublishVo getCoursePublishInfo(String courseId);

    /**
     * 根据指定条件检索分页数据
     * @param coursePage
     * @param courseQueryVo
     */
    void pageByCondition(Page<Course> coursePage, CourseQueryVo courseQueryVo);

    /**
     * 删除指定课程的所有的信息
     * @param courseId
     */
    void deleteDetail(String courseId);

    /**
     * 获取热度列表
     * @return
     */
    List<Course> getIndexRankList();
}
