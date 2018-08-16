package com.nlf.extend.wechat.message.batch;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.extend.wechat.exception.WeixinException;
import com.nlf.extend.wechat.message.batch.bean.BatchMessage;
import com.nlf.extend.wechat.message.batch.bean.BatchMessageResponse;
import com.nlf.extend.wechat.util.HttpsClient;
import com.nlf.log.Logger;
import com.nlf.serialize.json.JSON;

/**
 * 群发消息工具类
 * 
 * @author 6tail
 *
 */
public class BatchMessageHelper {

  protected BatchMessageHelper(){}

  /**
   * 发送群发消息
   * 
   * @param accessToken 调用接口凭证
   * @param message 群发消息
   * @return 群发消息结果
   * @see com.nlf.extend.wechat.base.BaseHelper
   * @see com.nlf.extend.wechat.base.bean.AccessToken
   * @throws WeixinException
   */
  public static BatchMessageResponse send(String accessToken, BatchMessage message) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.message_batch_send",accessToken);
      String data = JSON.fromObject(message);
      Logger.getLog().debug(App.getProperty("nlf.weixin.send",data));
      String result = HttpsClient.post(url,data);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
      BatchMessageResponse res = new BatchMessageResponse();
      res.setMsg_id(o.getString("msg_id"));
      res.setMsg_data_id(o.getString("msg_data_id"));
      return res;
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}