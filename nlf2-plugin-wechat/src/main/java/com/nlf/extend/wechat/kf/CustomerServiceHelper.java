package com.nlf.extend.wechat.kf;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.extend.wechat.exception.WeixinException;
import com.nlf.extend.wechat.util.HttpsClient;
import com.nlf.log.Logger;
import com.nlf.serialize.json.JSON;

public class CustomerServiceHelper{

  protected CustomerServiceHelper(){}

  public static void sendText(String accessToken,String openid,String text) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.custom_send",accessToken);
      Bean b = new Bean();
      b.set("touser",openid);
      b.set("msgtype","text");
      b.set("text",new Bean().set("content",text));
      String data = JSON.fromObject(b);
      Logger.getLog().debug(App.getProperty("nlf.weixin.send",data));
      String result = HttpsClient.post(url,data);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}