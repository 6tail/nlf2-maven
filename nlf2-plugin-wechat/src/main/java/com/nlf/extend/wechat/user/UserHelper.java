package com.nlf.extend.wechat.user;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.extend.wechat.exception.WeixinException;
import com.nlf.extend.wechat.user.bean.UserInfo;
import com.nlf.extend.wechat.util.HttpsClient;
import com.nlf.log.Logger;
import com.nlf.serialize.json.JSON;

/**
 * 公众号用户工具类，获取关注用户信息
 * 
 * @author 6tail
 *
 */
public class UserHelper{
  /** 简体 */
  public static final String LANG_ZH_CN = "zh_CN";
  /** 繁体 */
  public static final String LANG_ZH_TW = "zh_TW";
  /** 英语 */
  public static final String LANG_EN = "en";

  protected UserHelper(){}

  /**
   * 获取用户信息
   * 
   * @param accessToken 令牌
   * @param openid 普通用户标识，对该公众帐号唯一
   * @param lang 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
   * @return 用户信息
   * @throws WeixinException
   */
  public static UserInfo getUser(String accessToken,String openid,String lang) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.user_info",accessToken,openid,lang);
      String result = HttpsClient.get(url);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
      UserInfo u = new UserInfo();
      u.setSubscribe(o.getInt("subscribe",0));
      u.setOpenid(o.getString("openid"));
      u.setNickname(o.getString("nickname"));
      u.setSex(o.getInt("sex",0));
      u.setProvince(o.getString("province"));
      u.setCity(o.getString("city"));
      u.setCountry(o.getString("country"));
      u.setLanguage(o.getString("language"));
      u.setHeadimgurl(o.getString("headimgurl"));
      u.setSubscribeTime(o.getLong("subscribe_time",0));
      u.setUnionid(o.getString("unionid"));
      return u;
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}