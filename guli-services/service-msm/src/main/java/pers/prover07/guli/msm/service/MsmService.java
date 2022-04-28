package pers.prover07.guli.msm.service;

/**
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @version 1.0
 * @date 2022/4/27 19:26
 */
public interface MsmService {
    /**
     * 发送手机验证码
     * @param phoneNum
     * @return
     */
    boolean sendPhoneCode(String phoneNum);
}
