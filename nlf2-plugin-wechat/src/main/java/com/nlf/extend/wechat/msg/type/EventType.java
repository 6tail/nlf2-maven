package com.nlf.extend.wechat.msg.type;

/**
 * 事件类型
 * 
 * @author 6tail
 * 
 */
public enum EventType{
  /** 用户关注 */
  subscribe,
  /** 用户取消关注 */
  unsubscribe,
  /** 二维码扫描 */
  SCAN,
  /** 地理位置 */
  LOCATION,
  /** 菜单点击 */
  CLICK,
  /** 网页 */
  VIEW,
  /** 模板消息发送完成 */
  TEMPLATESENDJOBFINISH,
  /** 多客服接入会话 */
  kf_create_session,
  /** 多客服关闭会话 */
  kf_close_session,
  /** 多客服转接会话 */
  kf_switch_session
}