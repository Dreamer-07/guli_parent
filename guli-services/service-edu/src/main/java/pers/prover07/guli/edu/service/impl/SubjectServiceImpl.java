package pers.prover07.guli.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import pers.prover07.guli.edu.entity.Subject;
import pers.prover07.guli.edu.entity.execl.SubjectData;
import pers.prover07.guli.edu.entity.vo.SubjectTreeVo;
import pers.prover07.guli.edu.listener.SubjectXlsxListener;
import pers.prover07.guli.edu.mapper.SubjectMapper;
import pers.prover07.guli.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.prover07.guli.serviceenv.exception.GuliException;
import pers.prover07.guli.utils.enums.ResultTypeEnum;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-22
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubjectXlsxListener subjectXlsxListener;

    @Override
    public void addXlsxData(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(), SubjectData.class, subjectXlsxListener).doReadAll();
        } catch (IOException e) {
            e.printStackTrace();
            throw new GuliException(ResultTypeEnum.XLSX_FILE_UPLOAD_FILID);
        }

    }

    @Override
    public List<SubjectTreeVo> findAllInTree() {
        // 查找所有分类
        return covertTree("0");
    }

    private List<SubjectTreeVo> covertTree(String parentId) {
        // 构建查询条件
        LambdaQueryWrapper<Subject> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Subject::getParentId, parentId);
        // 查找
        List<Subject> dataList = this.list(lqw);

        return dataList.stream()
                .map(s -> {
                    SubjectTreeVo subjectTreeVo = new SubjectTreeVo();
                    List<SubjectTreeVo> childrenSubjectList = covertTree(s.getId());

                    subjectTreeVo.setId(s.getId());
                    subjectTreeVo.setLabel(s.getTitle());
                    subjectTreeVo.setChildren(childrenSubjectList);

                    return subjectTreeVo;
                }).collect(Collectors.toList());
    }
}
