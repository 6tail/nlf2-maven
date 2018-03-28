package com.nlf.extend.wechat.message;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.extend.wechat.exception.WeixinException;
import com.nlf.extend.wechat.message.bean.Message;
import com.nlf.extend.wechat.util.HttpsClient;
import com.nlf.log.Logger;
import com.nlf.serialize.json.JSON;

/**
 * 模板消息工具类
 * 
 * @author 6tail
 *
 */
public class MessageHelper{

  protected MessageHelper(){}

  /**
   * 发送模板消息
   * 
   * @param accessToken
   * @param message
   * @return 消息ID
   * @see com.nlf.extend.wechat.base.BaseHelper
   * @see com.nlf.extend.wechat.base.AccessToken
   * @throws WeixinException
   */
  public static String send(String accessToken,Message message) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.message_template_send",accessToken);
      Bean b = new Bean();
      b.set("touser",message.getToUser());
      b.set("template_id",message.getTemplateId());
      b.set("url",message.getUrl());
      b.set("topcolor",message.getTopColor());
      Bean d = new Bean();
      d.set("first",message.getFirst());
      for(String key:message.getDatas().keySet()){
        d.set(key,message.getDatas().get(key));
      }
      d.set("remark",message.getRemark());
      b.set("data",d);
      String data = JSON.fromObject(b);
      Logger.getLog().debug(App.getProperty("nlf.weixin.send",data));
      String result = HttpsClient.post(url,data);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg")+" template_id="+o.getString("template_id"));
      }
      return o.getString("msgid");
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}