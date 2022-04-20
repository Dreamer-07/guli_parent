package pers.prover07.guli.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pers.prover07.guli.oss.properties.OSSConfigProperties;
import pers.prover07.guli.oss.service.FileService;
import pers.prover07.guli.serviceenv.exception.GuliException;
import pers.prover07.guli.utils.enums.ResultTypeEnum;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/20 13:38
 */
@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private OSSConfigProperties ossConfigProperties;

    @Override
    public String uploadFile(MultipartFile file) {
        // 构建 OSS 客户端
        try {
            OSS ossClient = new OSSClientBuilder().build(
                    ossConfigProperties.getEndpoint(),
                    ossConfigProperties.getKeyId(),
                    ossConfigProperties.getKeySecret()
            );
            // 修改文件名
            String originalFilename = file.getOriginalFilename();
            // 获取文件后缀
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 以日期为单位划分文件
            String filePath = DateTime.now().toString("yyyy/MM/dd");
            // 使用 uuid 生成文件名
            String uuidFileName = UUID.randomUUID().toString().replaceAll("-", "");
            // 拼接获取文件名
            String fileName = filePath + "/" + uuidFileName + suffix;

            // 获取文件流
            InputStream fis = file.getInputStream();

            // 上传文件
            ossClient.putObject(ossConfigProperties.getBucketName(), fileName, fis);

            // 关闭连接
            ossClient.shutdown();

            // 拼接图片地址并返回
            return String.format(
                    "http://%s.%s/%s",
                    ossConfigProperties.getBucketName(),
                    ossConfigProperties.getEndpoint(),
                    fileName
            );
        } catch (IOException e) {
            throw new GuliException(ResultTypeEnum.OSS_FILE_UPLOAD_FAILD);
        }
    }
}
