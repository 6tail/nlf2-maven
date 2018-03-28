package com.nlf.extend.wechat.pay.core.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import javax.servlet.http.HttpServletRequest;
import com.nlf.App;
import com.nlf.core.IRequest;
import com.nlf.extend.web.IWebRequest;
import com.nlf.extend.wechat.pay.bean.PayNotifyRequest;
import com.nlf.extend.wechat.pay.core.IPayNotifyController;
import com.nlf.extend.wechat.pay.core.IPayNotifyHandler;
import com.nlf.extend.wechat.pay.core.IPayNotifyResolver;
import com.nlf.log.Logger;

/**
 * 微信支付通知控制器
 * 
 * @author 6tail
 *
 */
public class DefaultPayNotifyController implements IPayNotifyController{
  /** 通知解析接口 */
  protected IPayNotifyResolver resolver;

  /**
   * 构造控制器
   * 
   */
  public DefaultPayNotifyController(){
    this(new DefaultPayNotifyResolver());
  }

  /**
   * 构造控制器
   * 
   * @param resolver 通知解析器
   */
  public DefaultPayNotifyController(IPayNotifyResolver resolver){
    this.resolver = resolver;
  }

  public String handle(IPayNotifyHandler handler){
    try{
      IRequest r = App.getRequest();
      HttpServletRequest or = ((IWebRequest)r).getServletRequest();
      BufferedReader br = new BufferedReader(new InputStreamReader(or.getInputStream(),"utf-8"));
      StringBuilder s = new StringBuilder();
      String line = "";
      while(null!=(line = br.readLine())){
        s.append(line);
      }
      Logger.getLog().debug(App.getProperty("nlf.weixin.request",s));
      PayNotifyRequest req = resolver.decode(s.toString());
      String responseStr = resolver.encode(handler.onHandle(req));
      Logger.getLog().debug(App.getProperty("nlf.weixin.response",responseStr));
      return responseStr;
    }catch(Throwable e){
      Logger.getLog().error(App.getProperty("nlf.weixin.error"),e);
    }
    return "";
  }
}