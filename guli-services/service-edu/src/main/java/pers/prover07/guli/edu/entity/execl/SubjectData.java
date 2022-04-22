package pers.prover07.guli.edu.entity.execl;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/22 10:24
 */
@Data
public class SubjectData {

    @ExcelProperty(value = "父级分类", index = 0)
    private String parentSubjectName;

    @ExcelProperty(value = "二级分类", index = 1)
    private String curSubjectName;

}
