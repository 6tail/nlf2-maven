package com.nlf.extend.wechat.sns;

import java.util.List;
import com.nlf.App;
import com.nlf.Bean;
import com.nlf.extend.wechat.exception.WeixinException;
import com.nlf.extend.wechat.sns.bean.AccessToken;
import com.nlf.extend.wechat.sns.bean.UserInfo;
import com.nlf.extend.wechat.util.HttpsClient;
import com.nlf.log.Logger;
import com.nlf.serialize.json.JSON;

/**
 * 开放平台关系工具类
 * @author 6tail
 *
 */
public class SNSHelper{

  protected SNSHelper(){}

  private static AccessToken getTokenByUrl(String url) throws WeixinException{
    try{
      String result = HttpsClient.get(url);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
      AccessToken token = new AccessToken();
      token.setCreateTime(System.currentTimeMillis());
      token.setToken(o.getString("access_token"));
      token.setExpiresIn(o.getInt("expires_in",7200));
      token.setRefreshToken(o.getString("refresh_token"));
      token.setOpenid(o.getString("openid"));
      token.setScope(o.getString("scope"));
      return token;
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
  
  /**
   * 获取调用接口凭证
   * @param appid 应用唯一标识
   * @param secret 应用密钥AppSecret
   * @param code oauth获得的code
   * @return 调用接口凭证
   * @throws WeixinException
   */
  public static AccessToken getAccessToken(String appid,String secret,String code) throws WeixinException{
    String url = App.getProperty("nlf.weixin.url.sns.access_token",appid,secret,code);
    return getTokenByUrl(url);
  }
  
  /**
   * 刷新或续期调用接口凭证
   * @param appid 应用唯一标识
   * @param refreshToken 刷新凭证
   * @return 调用接口凭证
   * @throws WeixinException
   */
  public static AccessToken freshAccessToken(String appid,String refreshToken) throws WeixinException{
    String url = App.getProperty("nlf.weixin.url.sns.refresh_token",appid,refreshToken);
    return getTokenByUrl(url);
  }
  
  /**
   * 验证AccessToken是否有效
   * @param accessToken 调用接口凭证
   * @param openid 普通用户标识，对该公众帐号唯一
   * @throws WeixinException
   */
  public static void checkAccessToken(String accessToken,String openid) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.sns.auth",accessToken,openid);
      String result = HttpsClient.get(url);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
  
  /**
   * 获取授权登录用户信息
   * @param accessToken 调用接口凭证
   * @param openid 普通用户标识，对该公众帐号唯一
   * @return 用户信息
   * @throws WeixinException
   */
  public static UserInfo getUser(String accessToken,String openid) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.sns.user_info",accessToken,openid);
      String result = HttpsClient.get(url);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
      UserInfo u = new UserInfo();
      u.setOpenid(o.getString("openid"));
      u.setNickname(o.getString("nickname"));
      u.setSex(o.getInt("sex",0));
      u.setProvince(o.getString("province"));
      u.setCity(o.getString("city"));
      u.setCountry(o.getString("country"));
      u.setHeadimgurl(o.getString("headimgurl"));
      List<String> ps = o.get("privilege");
      if(null!=ps){
        for(String p:ps){
          u.addPrivilege(p);
        }
      }
      u.setUnionid(o.getString("unionid"));
      return u;
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}