package pers.prover07.guli.edu.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pers.prover07.guli.edu.entity.Subject;
import pers.prover07.guli.edu.entity.execl.SubjectData;
import pers.prover07.guli.edu.mapper.SubjectMapper;
import pers.prover07.guli.serviceenv.exception.GuliException;
import pers.prover07.guli.utils.enums.ResultTypeEnum;

import javax.annotation.Resource;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/22 10:55
 */
@Component
public class SubjectXlsxListener extends AnalysisEventListener<SubjectData> {

    @Resource
    private SubjectMapper subjectMapper;

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext context) {
        if (subjectData == null) {
            throw new GuliException(ResultTypeEnum.XLSX_FILE_NO_SIZE);
        }
        // 查找一级分类
        Subject parentSubject = gcSubject(subjectData.getParentSubjectName(), "0");
        // 查找二级分类
        gcSubject(subjectData.getCurSubjectName(), parentSubject.getId());
    }

    /**
     * gc(get and create) 获取 Subject，如果不存在就创建一个并返回
     * @param title
     * @param parentId
     * @return
     */
    private Subject gcSubject(String title, String parentId) {
        LambdaQueryWrapper<Subject> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper
                .eq(Subject::getTitle, title)
                .eq(Subject::getParentId, parentId);
        Subject subject = subjectMapper.selectOne(lambdaQueryWrapper);
        if (subject == null) {
            subject = new Subject();
            subject.setTitle(title);
            subject.setParentId(parentId);
            subjectMapper.insert(subject);
        }
        return subject;
    }




    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {

    }
}
