package pers.prover07.guli.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import pers.prover07.guli.edu.entity.Teacher;
import pers.prover07.guli.edu.mapper.TeacherMapper;
import pers.prover07.guli.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-18
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public List<Teacher> getIndexHighLevelList() {
        LambdaQueryWrapper<Teacher> lqw = new LambdaQueryWrapper<Teacher>()
                .eq(Teacher::getLevel, "2").
                orderByDesc(Teacher::getId)
                .last("limit 4");
        return this.list(lqw);
    }
}
