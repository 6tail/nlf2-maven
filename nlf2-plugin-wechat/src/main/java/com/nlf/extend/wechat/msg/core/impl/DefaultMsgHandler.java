package com.nlf.extend.wechat.msg.core.impl;

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
import com.nlf.extend.wechat.msg.core.IMsgHandler;

/**
 * 抽象微信公众号消息处理者
 * 
 * @author 6tail
 *
 */
public abstract class DefaultMsgHandler implements IMsgHandler{
  public IResponseMsg onSubscribe(SubscribeEventMsg event){
    return null;
  }

  public IResponseMsg onUnSubscribe(UnSubscribeEventMsg event){
    return null;
  }

  public IResponseMsg onClick(ClickEventMsg event){
    return null;
  }

  public IResponseMsg onLocation(LocationEventMsg event){
    return null;
  }

  public IResponseMsg onScan(ScanEventMsg event){
    return null;
  }

  public IResponseMsg onView(ViewEventMsg event){
    return null;
  }

  public IResponseMsg onImage(ImageMsg msg){
    return null;
  }

  public IResponseMsg onLink(LinkMsg msg){
    return null;
  }

  public IResponseMsg onLocation(LocationMsg msg){
    return null;
  }

  public IResponseMsg onText(TextMsg msg){
    return null;
  }

  public IResponseMsg onVideo(VideoMsg msg){
    return null;
  }

  public IResponseMsg onVoice(VoiceMsg msg){
    return null;
  }
  
  public IResponseMsg onTemplateSendJobFinish(TemplateSendJobFinishEventMsg event){
    return null;
  }
  
  public IResponseMsg onKfCreateSession(KfCreateSessionEventMsg msg){
    return null;
  }
  
  public IResponseMsg onKfCloseSession(KfCloseSessionEventMsg msg){
    return null;
  }
  
  public IResponseMsg onKfSwitchSession(KfSwitchSessionEventMsg msg){
    return null;
  }
}