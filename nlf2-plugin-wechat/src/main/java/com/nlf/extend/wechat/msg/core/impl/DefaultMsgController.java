package com.nlf.extend.wechat.msg.core.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import com.nlf.App;
import com.nlf.core.IRequest;
import com.nlf.extend.web.IWebRequest;
import com.nlf.extend.wechat.msg.bean.IEventMsg;
import com.nlf.extend.wechat.msg.bean.IMsg;
import com.nlf.extend.wechat.msg.bean.IResponseMsg;
import com.nlf.extend.wechat.msg.bean.impl.ClickEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.ImageMsg;
import com.nlf.extend.wechat.msg.bean.impl.KfCloseSessionEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.KfCreateSessionEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.KfSwitchSessionEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.LinkMsg;
import com.nlf.extend.wechat.msg.bean.impl.LocationEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.LocationMsg;
import com.nlf.extend.wechat.msg.bean.impl.ScanEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.SubscribeEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.TemplateSendJobFinishEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.TextMsg;
import com.nlf.extend.wechat.msg.bean.impl.UnSubscribeEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.VideoMsg;
import com.nlf.extend.wechat.msg.bean.impl.ViewEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.VoiceMsg;
import com.nlf.extend.wechat.msg.core.IMsgController;
import com.nlf.extend.wechat.msg.core.IMsgHandler;
import com.nlf.extend.wechat.msg.core.IMsgResolver;
import com.nlf.log.Logger;
import com.nlf.util.StringUtil;

/**
 * 微信公众号消息控制器
 * 
 * @author 6tail
 *
 */
public class DefaultMsgController implements IMsgController{
  /** 微信公众号中设置的令牌，如果为null或空字符串，用于本地测试，不进行验证 */
  protected String token;
  /** 消息解析接口 */
  protected IMsgResolver resolver;

  /**
   * 构造控制器
   * 
   * @param token 微信公众号中设置的令牌
   */
  public DefaultMsgController(String token){
    this(token,new DefaultMsgResolver());
  }

  /**
   * 构造控制器
   * 
   * @param token 微信公众号中设置的令牌
   * @param resolver 消息解析器
   */
  public DefaultMsgController(String token,IMsgResolver resolver){
    this.token = token;
    this.resolver = resolver;
  }

  /**
   * sha1
   * 
   * @param s 原字符串
   * @return 结果字符串
   * @throws NoSuchAlgorithmException
   */
  private String sha1(String s) throws NoSuchAlgorithmException{
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

  /**
   * 验证
   * 
   * @return true/false 验证成功/失败
   * @throws NoSuchAlgorithmException
   */
  private boolean check() throws NoSuchAlgorithmException{
    IRequest r = App.getRequest();
    String signature = r.get("signature");
    String timestamp = r.get("timestamp");
    String nonce = r.get("nonce");
    if(signature.length()<1){
      return false;
    }
    if(timestamp.length()<1){
      return false;
    }
    if(nonce.length()<1){
      return false;
    }
    String[] a = new String[]{token,timestamp,nonce};
    Arrays.sort(a);
    return signature.equals(sha1(StringUtil.join(a,"")));
  }

  /**
   * get处理，用于首次验证
   * 
   * @return 响应内容
   * @throws NoSuchAlgorithmException
   */
  protected String onGet() throws NoSuchAlgorithmException{
    IRequest r = App.getRequest();
    String echostr = r.get("echostr");
    if(echostr.length()<1){
      return "";
    }
    return check()?echostr:"";
  }

  /**
   * post处理
   * 
   * @param handler 消息处理接口
   * @return 响应内容
   * @throws NoSuchAlgorithmException
   * @throws IOException
   */
  protected String onPost(IMsgHandler handler) throws NoSuchAlgorithmException,IOException{
    //如果token为null或空字符串，用于本地测试，不进行验证
    if(null!=token&&token.length()>0){
      if(!check()){
        return "";
      }
    }
    IRequest r = App.getRequest();
    HttpServletRequest or = ((IWebRequest)r).getServletRequest();
    BufferedReader br = new BufferedReader(new InputStreamReader(or.getInputStream(),"utf-8"));
    StringBuilder s = new StringBuilder();
    String line = "";
    while(null!=(line = br.readLine())){
      s.append(line);
    }
    Logger.getLog().debug(App.getProperty("nlf.weixin.request",s));
    if(null==handler){
      return "";
    }
    IMsg msg = resolver.decode(s.toString());
    if(null==msg){
      return "";
    }
    IResponseMsg responseMsg = null;
    switch(msg.getMsgType()){
      case event:
        IEventMsg em = (IEventMsg)msg;
        switch(em.getEventType()){
          case CLICK:
            responseMsg = handler.onClick((ClickEventMsg)em);
            break;
          case LOCATION:
            responseMsg = handler.onLocation((LocationEventMsg)em);
            break;
          case SCAN:
            responseMsg = handler.onScan((ScanEventMsg)em);
            break;
          case subscribe:
            responseMsg = handler.onSubscribe((SubscribeEventMsg)em);
            break;
          case unsubscribe:
            responseMsg = handler.onUnSubscribe((UnSubscribeEventMsg)em);
            break;
          case VIEW:
            responseMsg = handler.onView((ViewEventMsg)em);
            break;
          case TEMPLATESENDJOBFINISH:
            responseMsg = handler.onTemplateSendJobFinish((TemplateSendJobFinishEventMsg)em);
            break;
          case kf_create_session:
            responseMsg = handler.onKfCreateSession((KfCreateSessionEventMsg)em);
            break;
          case kf_close_session:
            responseMsg = handler.onKfCloseSession((KfCloseSessionEventMsg)em);
            break;
          case kf_switch_session:
            responseMsg = handler.onKfSwitchSession((KfSwitchSessionEventMsg)em);
            break;
          default:break;
        }
        break;
      case text:
        responseMsg = handler.onText((TextMsg)msg);
        break;
      case image:
        responseMsg = handler.onImage((ImageMsg)msg);
        break;
      case voice:
        responseMsg = handler.onVoice((VoiceMsg)msg);
        break;
      case video:
        responseMsg = handler.onVideo((VideoMsg)msg);
        break;
      case location:
        responseMsg = handler.onLocation((LocationMsg)msg);
        break;
      case link:
        responseMsg = handler.onLink((LinkMsg)msg);
        break;
      default:break;
    }
    String responseStr = resolver.encode(responseMsg);
    Logger.getLog().debug(App.getProperty("nlf.weixin.response",responseStr));
    return responseStr;
  }

  public String handle(IMsgHandler handler){
    try{
      IRequest r = App.getRequest();
      HttpServletRequest or = ((IWebRequest)r).getServletRequest();
      String method = or.getMethod();
      if("GET".equalsIgnoreCase(method)){
        return onGet();
      }
      if("POST".equals(method)){
        return onPost(handler);
      }
    }catch(Throwable e){
      Logger.getLog().error(App.getProperty("nlf.weixin.error"),e);
    }
    return "";
  }
}