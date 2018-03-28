package com.nlf.extend.wechat.msg.core;

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

/**
 * 微信公众号消息处理接口
 * 
 * @author 6tail
 *
 */
public interface IMsgHandler{
  /**
   * 当用户关注时
   * 
   * @param event 关注事件
   * @return 响应消息
   */
  IResponseMsg onSubscribe(SubscribeEventMsg event);

  /**
   * 当用户取消关注时
   * 
   * @param event 取消关注事件
   * @return 响应消息
   */
  IResponseMsg onUnSubscribe(UnSubscribeEventMsg event);

  /**
   * 当用户上报地理位置
   * 
   * @param event 上报地理位置事件
   * @return 响应消息
   */
  IResponseMsg onLocation(LocationEventMsg event);

  /**
   * 当用户扫描二维码
   * 
   * @param event 扫描二维码事件
   * @return 响应消息
   */
  IResponseMsg onScan(ScanEventMsg event);

  /**
   * 当用户点击菜单
   * 
   * @param event 点击菜单事件
   * @return 响应消息
   */
  IResponseMsg onClick(ClickEventMsg event);
  
  /**
   * 当用户点击菜单跳转到URL
   * 
   * @param event 点击菜单跳转URL事件
   * @return 响应消息
   */
  IResponseMsg onView(ViewEventMsg event);

  /**
   * 当用户发来文字消息
   * 
   * @param msg 用户发来的消息
   * @return 响应消息
   */
  IResponseMsg onText(TextMsg msg);

  /**
   * 当用户发来图片消息
   * 
   * @param msg 用户发来的消息
   * @return 响应消息
   */
  IResponseMsg onImage(ImageMsg msg);

  /**
   * 当用户发来语音消息
   * 
   * @param msg 用户发来的消息
   * @return 响应消息
   */
  IResponseMsg onVoice(VoiceMsg msg);

  /**
   * 当用户发来视频消息
   * 
   * @param msg 用户发来的消息
   * @return 响应消息
   */
  IResponseMsg onVideo(VideoMsg msg);

  /**
   * 当用户发来地理位置消息
   * 
   * @param msg 用户发来的消息
   * @return 响应消息
   */
  IResponseMsg onLocation(LocationMsg msg);

  /**
   * 当用户发来链接消息
   * 
   * @param msg 用户发来的消息
   * @return 响应消息
   */
  IResponseMsg onLink(LinkMsg msg);
  
  /**
   * 当收到模板消息发送结果通知
   * @param event 模板消息发送结果事件
   * @return 响应消息
   */
  IResponseMsg onTemplateSendJobFinish(TemplateSendJobFinishEventMsg event);
  
  /**
   * 当多客服接入会话
   * 
   * @param msg 多客服接入会话事件
   * @return 响应消息
   */
  IResponseMsg onKfCreateSession(KfCreateSessionEventMsg msg);
  
  /**
   * 当多客服关闭会话
   * 
   * @param msg 多客服关闭会话事件
   * @return 响应消息
   */
  IResponseMsg onKfCloseSession(KfCloseSessionEventMsg msg);
  
  /**
   * 当多客服转接会话
   * 
   * @param msg 多客服转接会话事件
   * @return 响应消息
   */
  IResponseMsg onKfSwitchSession(KfSwitchSessionEventMsg msg);
}