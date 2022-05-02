package pers.prover07.guli.vdo.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VdoFileService {

    /**
     * 上传文件
     * @param file
     */
    String uploadFile(MultipartFile file);

    /**
     * 根据 {@code videoSourceId} 删除视频资源
     * @param videoSourceId 视频资源标识
     */
    void delete(String videoSourceId);

    /**
     * 批量删除视频资源
     * @param videoSourceIds 待删除的视频资源标识集合
     */
    void batchDetele(List<String> videoSourceIds);

    /**
     * 获取播放凭证
     * @param videoSourceId
     * @return
     */
    String getPlayAuth(String videoSourceId);
}
