package com.module.service;

import com.core.base.service.BaseModelService;
import com.core.util.BeanUtils;
import com.core.util.IdGenerator;
import com.core.util.StringUtils;
import com.module.entity.WechatUserInfo;
import com.module.mapper.WechatUserInfoMapper;
import com.vdurmont.emoji.EmojiParser;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Service;

/**
 * @author chc
 * @create 2017-11-21 11:11
 **/
@Service("wechatUserInfoService")
public class WechatUserInfoService extends BaseModelService<WechatUserInfoMapper,WechatUserInfo> {


    /**
     * 创建或更新微信用户信息
     * @param wxMpUser
     */
    public void saveWeChatUserInfo(WxMpUser wxMpUser){
        WechatUserInfo userInfo = this.selectByUniqueColumn("openId",wxMpUser.getOpenId());
        if(userInfo!=null){
            //更新
            String id = userInfo.getId();
            BeanUtils.copyPropertiesWithoutNull(wxMpUser,userInfo);
            userInfo.setId(id);
            String nickName =  EmojiParser.removeAllEmojis(userInfo.getNickname());
            Long[] longs = wxMpUser.getTagIds();
            userInfo.setNickname(nickName);
            if(longs!=null){
                String s = "";
                for (Long aLong : longs) {
                    s += aLong+",";
                }
                userInfo.setTagIds(s);
            }
            this.updateById(userInfo);
        }else{
            //创建
            userInfo = new WechatUserInfo();
            BeanUtils.copyProperties(wxMpUser,userInfo);
            userInfo.setId(IdGenerator.generate());
            String nickName =  EmojiParser.removeAllEmojis(userInfo.getNickname());
            userInfo.setNickname(nickName);
            Long[] longs = wxMpUser.getTagIds();
            if(longs!=null){
                String s = "";
                for (Long aLong : longs) {
                    s += aLong+",";
                }
                userInfo.setTagIds(s);
            }
            this.insert(userInfo);
        }
    }

    public WechatUserInfo getWecahtUserInfo(String openId){
        WechatUserInfo userInfo = this.selectByUniqueColumn("openId",openId);
        return userInfo;
    }

}
