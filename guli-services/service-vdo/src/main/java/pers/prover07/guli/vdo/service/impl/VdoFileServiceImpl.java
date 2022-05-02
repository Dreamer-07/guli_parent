package pers.prover07.guli.vdo.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pers.prover07.guli.serviceenv.exception.GuliException;
import pers.prover07.guli.utils.ExceptionUtil;
import pers.prover07.guli.utils.enums.ResultTypeEnum;
import pers.prover07.guli.vdo.config.AliyunVodConfig;
import pers.prover07.guli.vdo.properties.OSSConfigProperties;
import pers.prover07.guli.vdo.service.VdoFileService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/25 7:11
 */
@Service
public class VdoFileServiceImpl implements VdoFileService {

    @Autowired
    private DefaultAcsClient acsClient;

    @Autowired
    private OSSConfigProperties ossConfigProperties;

    @Override
    public String uploadFile(MultipartFile file) {
        try {
            InputStream is = file.getInputStream();
            // 获取源文件名
            String originalFilename = file.getOriginalFilename();
            // 视频标题
            String title = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            // 构建请求
            UploadStreamRequest uploadStreamRequest = new UploadStreamRequest(
                    ossConfigProperties.getKeyId(), ossConfigProperties.getKeySecret(),
                    title, originalFilename, is
            );
            // uploadStreamRequest.setApiRegionId("cn-beijing");

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse uploadStreamResponse = uploader.uploadStream(uploadStreamRequest);
            String videoId = uploadStreamResponse.getVideoId();
            if (uploadStreamResponse.isSuccess()) {
                return videoId;
            } else {
                throw new GuliException(30003, "service-vdo/VodFileServiceImpl: 上传文件失败, 错误码:" + videoId);
            }
        } catch (IOException e) {
            throw new GuliException(ResultTypeEnum.VDO_FILE_UPLOAD_FAILD);
        }
    }

    @Override
    public void delete(String videoSourceId) {
        // 构建请求
        try {
            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();
            // 设置要删除的视频id
            deleteVideoRequest.setVideoIds(videoSourceId);
            // 执行请求
            acsClient.getAcsResponse(deleteVideoRequest);
        } catch (ClientException e) {
            throw new GuliException("删除视频失败: " + ExceptionUtil.getMessage(e));
        }
    }

    @Override
    public void batchDetele(List<String> videoSourceIds) {
        try {
            DeleteVideoRequest deleteVideoRequest = new DeleteVideoRequest();
            // 将列表的数据转换为以各个元素键使用,分隔的字符串
            String idStr = StringUtils.join(videoSourceIds, ",");
            deleteVideoRequest.setVideoIds(idStr);
            acsClient.getAcsResponse(deleteVideoRequest);
        } catch (ClientException e) {
            throw new GuliException("批量删除视频失败: " + ExceptionUtil.getMessage(e));
        }
    }

    @Override
    public String getPlayAuth(String videoSourceId) {
        try {
            GetVideoPlayAuthRequest videoPlayAuthRequest = new GetVideoPlayAuthRequest();
            videoPlayAuthRequest.setVideoId(videoSourceId);
            return acsClient.getAcsResponse(videoPlayAuthRequest).getPlayAuth();
        } catch (ClientException e) {
            throw new GuliException("获取视频凭证失败: " + ExceptionUtil.getMessage(e));
        }
    }
}
