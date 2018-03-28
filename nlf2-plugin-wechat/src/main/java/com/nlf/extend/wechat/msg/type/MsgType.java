package com.nlf.extend.wechat.msg.type;

/**
 * 消息类型
 * 
 * @author 6tail
 * 
 */
public enum MsgType{
  /** 文本，请求和响应均可 */
  text,
  /** 图片，请求和响应均可 */
  image,
  /** 语音，请求和响应均可 */
  voice,
  /** 视频，请求和响应均可 */
  video,
  /** 地理位置，用于请求 */
  location,
  /** 链接，用于请求 */
  link,
  /** 事件，用于请求 */
  event,
  /** 音乐，用于响应 */
  music,
  /** 图文消息，用于响应 */
  news,
  /** 转移客服消息 */
  transfer_customer_service
}