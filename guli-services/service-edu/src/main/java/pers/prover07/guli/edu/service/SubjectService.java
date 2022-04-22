package pers.prover07.guli.edu.service;

import org.springframework.web.multipart.MultipartFile;
import pers.prover07.guli.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.prover07.guli.edu.entity.vo.SubjectTreeVo;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-22
 */
public interface SubjectService extends IService<Subject> {

    /**
     * 添加 xlsx 文件的数据
     * @param file
     */
    void addXlsxData(MultipartFile file);

    /**
     * 查找所有数据并以树型结构封装
     * @return
     */
    List<SubjectTreeVo> findAllInTree();
}
