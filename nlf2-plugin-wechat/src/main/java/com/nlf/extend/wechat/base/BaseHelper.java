package com.nlf.extend.wechat.base;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import com.nlf.App;
import com.nlf.Bean;
import com.nlf.extend.wechat.base.bean.AccessToken;
import com.nlf.extend.wechat.exception.WeixinException;
import com.nlf.extend.wechat.util.HttpsClient;
import com.nlf.log.Logger;
import com.nlf.serialize.json.JSON;

/**
 * 获取AccessToken工具类
 * 
 * @author 6tail
 *
 */
public class BaseHelper{

  private BaseHelper(){}

  /**
   * 获取令牌
   * 
   * @param appid 第三方用户唯一凭证
   * @param secret 第三方用户唯一凭证密钥，即appsecret
   * @return 令牌
   * @throws WeixinException
   */
  public synchronized static AccessToken getAccessToken(String appid,String secret) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.access_token",appid,secret);
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
      return token;
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }

  /**
   * 获取微信服务器IP列表
   * 
   * @param accessToken 令牌
   * @return 服务器IP列表
   * @throws WeixinException
   */
  public static List<String> getServerIps(String accessToken) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.get_server_ips",accessToken);
      String result = HttpsClient.get(url);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
      List<String> l = o.get("ip_list");
      return l;
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
  
  public static String shortUrl(String accessToken,String longUrl) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.shorturl",accessToken);
      Bean dataBean = new Bean();
      dataBean.set("action","long2short");
      dataBean.set("long_url",longUrl);
      String data = JSON.fromObject(dataBean);
      Logger.getLog().debug(App.getProperty("nlf.weixin.send",data));
      String result = HttpsClient.post(url,data);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
      return o.getString("short_url");
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
  
  /**
   * sha1
   * 
   * @param s 原字符串
   * @return 结果字符串
   * @throws NoSuchAlgorithmException
   */
  public static String sha1(String s) throws NoSuchAlgorithmException{
    MessageDigest md = MessageDigest.getInstance("SHA-1");
    md.update(s.getBytes());
    byte[] b = md.digest();
    StringBuilder sb = new StringBuilder();
    for(int i = 0;i<b.length;i++){
      String hex = Integer.toHexString(b[i]&0xFF);
      hex = (hex.length()==1?"0":"")+hex;
      sb.append(hex);
    }
    return sb.toString();
  }
}