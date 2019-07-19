package com.nlf.extend.wechat.js;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.extend.wechat.exception.WeixinException;
import com.nlf.extend.wechat.js.bean.Ticket;
import com.nlf.extend.wechat.util.HttpsClient;
import com.nlf.log.Logger;
import com.nlf.serialize.json.JSON;

/**
 * Ticket工具
 *
 * @author 6tail
 *
 */
public class TicketHelper{

  protected TicketHelper(){}

  /**
   * 获取jsapi的ticket
   *
   * @param accessToken accessToken
   * @see com.nlf.extend.wechat.base.BaseHelper
   * @see com.nlf.extend.wechat.base.bean.AccessToken
   * @throws WeixinException WeixinException
   */
  public static Ticket getTicket4JsApi(String accessToken) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.jsapi_ticket",accessToken);
      String result = HttpsClient.get(url);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
      Ticket ticket = new Ticket();
      ticket.setTicket(o.getString("ticket"));
      ticket.setExpiresIn(o.getInt("expires_in",7200));
      ticket.setCreateTime(System.currentTimeMillis());
      return ticket;
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}
