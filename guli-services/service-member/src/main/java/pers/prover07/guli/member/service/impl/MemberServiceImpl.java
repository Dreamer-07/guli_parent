package pers.prover07.guli.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import pers.prover07.guli.member.entity.Member;
import pers.prover07.guli.member.entity.vo.RegisterVo;
import pers.prover07.guli.member.mapper.MemberMapper;
import pers.prover07.guli.member.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.prover07.guli.serviceenv.exception.GuliException;
import pers.prover07.guli.serviceenv.tools.IGlobalCache;
import pers.prover07.guli.utils.JwtUtils;
import pers.prover07.guli.utils.MD5;
import pers.prover07.guli.utils.enums.ResultTypeEnum;

import javax.imageio.spi.RegisterableService;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author 小丶木曾义仲丶哈牛柚子露丶蛋卷
 * @since 2022-04-27
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private IGlobalCache globalCache;

    @Override
    public String mobileLogin(String mobile, String password) {
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException();
        }
        // 根据手机号查找用户
        LambdaQueryWrapper<Member> lqw = new LambdaQueryWrapper<Member>().eq(Member::getMobile, mobile);
        Member member = this.getOne(lqw);
        // 数据校验
        if (member == null) {
            throw new GuliException(ResultTypeEnum.MEMBER_NO_FOUND);
        }
        if (!MD5.encrypt(password).equals(member.getPassword())) {
            throw new GuliException(ResultTypeEnum.MEMBER_PWD_ERROR);
        }
        if (member.getIsDisabled()) {
            throw new GuliException(ResultTypeEnum.MEMBER_IS_LOCKING);
        }
        // 生成 JWT 并返回
        return JwtUtils.getJwtToken(member.getId());
    }

    @Override
    public void register(RegisterVo registerVo) {
        // 校验数据
        if (StringUtils.isEmpty(registerVo.getMobile()) ||
            StringUtils.isEmpty(registerVo.getPassword()) ||
            StringUtils.isEmpty(registerVo.getCode()) ||
            StringUtils.isEmpty(registerVo.getNickname())) {
            throw new GuliException();
        }

        // 判断手机号是否已被注册
        LambdaQueryWrapper<Member> lqw = new LambdaQueryWrapper<Member>().eq(Member::getMobile, registerVo.getMobile());
        int count = this.count(lqw);
        if (count > 0) {
            throw new GuliException(ResultTypeEnum.USER_ALREADY_EXISTS);
        }

        // 判断验证码
        String cachedCode = (String) globalCache.get("service-msm::phone::" + registerVo.getMobile());
        if (StringUtils.isEmpty(cachedCode) || !cachedCode.equals(registerVo.getCode())) {
            throw new GuliException(ResultTypeEnum.CODE_INVALID);
        }

        // TODO: 删除验证码

        // 添加用户
        Member member = new Member();
        BeanUtils.copyProperties(registerVo, member);
        member.setPassword(MD5.encrypt(member.getPassword()));
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");
        member.setIsDisabled(false);
        this.save(member);
    }

    @Override
    public Member getByOpenId(String openId) {
        LambdaQueryWrapper<Member> lqw = new LambdaQueryWrapper<Member>().eq(Member::getOpenid, openId);
        return this.getOne(lqw);
    }
}
