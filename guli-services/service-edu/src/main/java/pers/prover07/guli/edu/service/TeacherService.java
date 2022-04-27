package pers.prover07.guli.edu.service;

import pers.prover07.guli.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-18
 */
public interface TeacherService extends IService<Teacher> {

    /**
     * 获取名牌讲师
     * @return
     */
    List<Teacher> getIndexHighLevelList();
}
