package com.module.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.module.entity.UserInfo;
import com.module.entity.WechatUserInfo;
import org.springframework.stereotype.Repository;

/**
 * 用户Mapper
 */
@Repository
public interface WechatUserInfoMapper extends BaseMapper<WechatUserInfo> {

}
