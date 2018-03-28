package com.nlf.extend.wechat.msg.core.impl;

import com.nlf.Bean;
import com.nlf.extend.serialize.xml.XML;
import com.nlf.extend.wechat.msg.bean.IEventMsg;
import com.nlf.extend.wechat.msg.bean.IRequestMsg;
import com.nlf.extend.wechat.msg.bean.IResponseMsg;
import com.nlf.extend.wechat.msg.bean.impl.ClickEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.ImageMsg;
import com.nlf.extend.wechat.msg.bean.impl.KfCloseSessionEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.KfCreateSessionEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.KfSwitchSessionEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.LinkMsg;
import com.nlf.extend.wechat.msg.bean.impl.LocationEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.LocationMsg;
import com.nlf.extend.wechat.msg.bean.impl.MusicMsg;
import com.nlf.extend.wechat.msg.bean.impl.NewsItem;
import com.nlf.extend.wechat.msg.bean.impl.NewsMsg;
import com.nlf.extend.wechat.msg.bean.impl.ScanEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.SubscribeEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.TemplateSendJobFinishEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.TextMsg;
import com.nlf.extend.wechat.msg.bean.impl.TransferCustomerMsg;
import com.nlf.extend.wechat.msg.bean.impl.UnSubscribeEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.VideoMsg;
import com.nlf.extend.wechat.msg.bean.impl.ViewEventMsg;
import com.nlf.extend.wechat.msg.bean.impl.VoiceMsg;
import com.nlf.extend.wechat.msg.core.IMsgResolver;
import com.nlf.extend.wechat.msg.type.EventType;
import com.nlf.extend.wechat.msg.type.MsgType;

/**
 * 默认微信公众号消息解析器
 * 
 * @author 6tail
 *
 */
public class DefaultMsgResolver implements IMsgResolver{
  /**
   * 解析文本
   * 
   * @param o 数据Bean
   * @return 请求消息
   */
  private IRequestMsg decodeTextMsg(Bean o){
    TextMsg m = new TextMsg();
    m.setContent(o.getString("Content"));
    m.setMsgId(o.getString("MsgId"));
    return m;
  }

  /**
   * 解析链接
   * 
   * @param o 数据Bean
   * @return 请求消息
   */
  private IRequestMsg decodeLinkMsg(Bean o){
    LinkMsg m = new LinkMsg();
    m.setTitle(o.getString("Title"));
    m.setDescription(o.getString("Description"));
    m.setUrl(o.getString("Url"));
    m.setMsgId(o.getString("MsgId"));
    return m;
  }

  /**
   * 解析图片
   * 
   * @param o 数据Bean
   * @return 请求消息
   */
  private IRequestMsg decodeImage(Bean o){
    ImageMsg m = new ImageMsg();
    m.setMsgId(o.getString("MsgId"));
    m.setMediaId(o.getString("MediaId"));
    m.setPicUrl(o.getString("PicUrl"));
    return m;
  }

  /**
   * 解析语音
   * 
   * @param o 数据Bean
   * @return 请求消息
   */
  private IRequestMsg decodeVoice(Bean o){
    VoiceMsg m = new VoiceMsg();
    m.setMsgId(o.getString("MsgId"));
    m.setMediaId(o.getString("MediaId"));
    m.setFormat(o.getString("Format"));
    m.setRecognition(o.getString("Recognition"));
    return m;
  }

  /**
   * 解析视频
   * 
   * @param o 数据Bean
   * @return 请求消息
   */
  private IRequestMsg decodeVideo(Bean o){
    VideoMsg m = new VideoMsg();
    m.setMsgId(o.getString("MsgId"));
    m.setMediaId(o.getString("MediaId"));
    m.setThumbMediaId(o.getString("ThumbMediaId"));
    return m;
  }

  /**
   * 解析地理位置
   * 
   * @param o 数据Bean
   * @return 请求消息
   */
  private IRequestMsg decodeLocation(Bean o){
    LocationMsg m = new LocationMsg();
    m.setMsgId(o.getString("MsgId"));
    m.setLabel(o.getString("Label"));
    m.setLocationX(o.getString("Location_X"));
    m.setLocationY(o.getString("Location_Y"));
    m.setScale(o.getString("Scale"));
    return m;
  }

  /**
   * 解析关注事件
   * 
   * @param o 数据Bean
   * @return 事件消息
   */
  private IEventMsg decodeSubscribeMsg(Bean o){
    SubscribeEventMsg m = new SubscribeEventMsg();
    m.setEventKey(o.getString("EventKey"));
    m.setTicket(o.getString("Ticket"));
    return m;
  }

  /**
   * 解析取消关注事件
   * 
   * @param o 数据Bean
   * @return 事件消息
   */
  private IEventMsg decodeUnSubscribeMsg(Bean o){
    return new UnSubscribeEventMsg();
  }

  /**
   * 解析菜单点击事件
   * 
   * @param o 数据Bean
   * @return 事件消息
   */
  private IEventMsg decodeClickMsg(Bean o){
    ClickEventMsg m = new ClickEventMsg();
    m.setEventKey(o.getString("EventKey"));
    return m;
  }

  /**
   * 解析菜单点击跳转链接事件
   * 
   * @param o 数据Bean
   * @return 事件消息
   */
  private IEventMsg decodeViewMsg(Bean o){
    ViewEventMsg m = new ViewEventMsg();
    m.setEventKey(o.getString("EventKey"));
    return m;
  }

  /**
   * 解析上报地理位置事件
   * 
   * @param o 数据Bean
   * @return 事件消息
   */
  private IEventMsg decodeLocationMsg(Bean o){
    LocationEventMsg m = new LocationEventMsg();
    m.setLatitude(o.getString("Latitude"));
    m.setLongitude(o.getString("Longitude"));
    m.setPrecision(o.getString("Precision"));
    return m;
  }

  /**
   * 解析二维码扫描事件
   * 
   * @param o 数据Bean
   * @return 事件消息
   */
  private IEventMsg decodeScanMsg(Bean o){
    ScanEventMsg m = new ScanEventMsg();
    m.setEventKey(o.getString("EventKey"));
    m.setTicket(o.getString("Ticket"));
    return m;
  }
  
  /**
   * 解析二维码扫描事件
   * 
   * @param o 数据Bean
   * @return 事件消息
   */
  private IEventMsg decodeTemplateSendJobMsg(Bean o){
    TemplateSendJobFinishEventMsg m = new TemplateSendJobFinishEventMsg();
    m.setMsgId(o.getString("MsgID"));
    m.setStatus(o.getString("Status"));
    return m;
  }

  /**
   * 解析多客服接入会话事件
   * @param o 数据Bean
   * @return 事件消息
   */
  private IEventMsg decodeKfCreateSessionMsg(Bean o){
    KfCreateSessionEventMsg m = new KfCreateSessionEventMsg();
    m.setKfAccount(o.getString("KfAccount"));
    return m;
  }
  
  private IEventMsg decodeKfCloseSessionMsg(Bean o){
    KfCloseSessionEventMsg m = new KfCloseSessionEventMsg();
    m.setKfAccount(o.getString("KfAccount"));
    return m;
  }
  
  private IEventMsg decodeKfSwitchSessionMsg(Bean o){
    KfSwitchSessionEventMsg m = new KfSwitchSessionEventMsg();
    m.setFromKfAccount(o.getString("FromKfAccount"));
    m.setToKfAccount(o.getString("ToKfAccount"));
    return m;
  }
  
  /**
   * 解析事件
   * 
   * @param o 数据Bean
   * @return 事件消息
   */
  private IEventMsg decodeEvent(Bean o){
    EventType type = EventType.valueOf(o.getString("Event"));
    IEventMsg msg = null;
    switch(type){
      case subscribe:
        msg = decodeSubscribeMsg(o);
        break;
      case unsubscribe:
        msg = decodeUnSubscribeMsg(o);
        break;
      case CLICK:
        msg = decodeClickMsg(o);
        break;
      case VIEW:
        msg = decodeViewMsg(o);
        break;
      case LOCATION:
        msg = decodeLocationMsg(o);
        break;
      case SCAN:
        msg = decodeScanMsg(o);
        break;
      case TEMPLATESENDJOBFINISH:
        msg = decodeTemplateSendJobMsg(o);
        break;
      case kf_create_session:
        msg = decodeKfCreateSessionMsg(o);
        break;
      case kf_close_session:
        msg = decodeKfCloseSessionMsg(o);
        break;
      case kf_switch_session:
        msg = decodeKfSwitchSessionMsg(o);
        break;
    }
    return msg;
  }

  public IRequestMsg decode(String str){
    Bean o = XML.toBean(str);
    MsgType type = MsgType.valueOf(o.getString("MsgType"));
    IRequestMsg msg = null;
    switch(type){
      case text:
        msg = decodeTextMsg(o);
        break;
      case image:
        msg = decodeImage(o);
        break;
      case voice:
        msg = decodeVoice(o);
        break;
      case video:
        msg = decodeVideo(o);
        break;
      case location:
        msg = decodeLocation(o);
        break;
      case link:
        msg = decodeLinkMsg(o);
        break;
      case event:
        msg = decodeEvent(o);
        break;
      default:
        break;
    }
    if(null==msg){
      return null;
    }
    msg.setFromUser(o.getString("FromUserName"));
    msg.setToUser(o.getString("ToUserName"));
    msg.setCreateTime(o.getString("CreateTime"));
    return msg;
  }

  /**
   * 构造文本消息字符串
   * 
   * @param msg 消息
   * @return 字符串
   */
  private String encodeTextMsg(TextMsg msg){
    StringBuilder s = new StringBuilder();
    s.append("<Content><![CDATA[");
    s.append(msg.getContent());
    s.append("]]></Content>");
    return s.toString();
  }
  
  /**
   * 构造转发客服消息字符串
   * 
   * @param msg 消息
   * @return 字符串
   */
  private String encodeTransferCustomerMsg(TransferCustomerMsg msg){
    StringBuilder s = new StringBuilder();
    if(null!=msg.getKfAccount()){
      s.append("<TransInfo><KfAccount><![CDATA[");
      s.append(msg.getKfAccount());
      s.append("]]></KfAccount></TransInfo>");
    }
    return s.toString();
  }
  
  

  /**
   * 构造图片消息字符串
   * 
   * @param msg 消息
   * @return 字符串
   */
  private String encodeImageMsg(ImageMsg msg){
    StringBuilder s = new StringBuilder();
    s.append("<Image><MediaId><![CDATA[");
    s.append(msg.getMediaId());
    s.append("]]></MediaId></Image>");
    return s.toString();
  }

  /**
   * 构造语音消息字符串
   * 
   * @param msg 消息
   * @return 字符串
   */
  private String encodeVoiceMsg(VoiceMsg msg){
    StringBuilder s = new StringBuilder();
    s.append("<Voice><MediaId><![CDATA[");
    s.append(msg.getMediaId());
    s.append("]]></MediaId></Voice>");
    return s.toString();
  }

  /**
   * 构造视频消息字符串
   * 
   * @param msg 消息
   * @return 字符串
   */
  private String encodeVideoMsg(VideoMsg msg){
    StringBuilder s = new StringBuilder();
    s.append("<Video><MediaId><![CDATA[");
    s.append(msg.getMediaId());
    s.append("]]></MediaId><Title><![CDATA[");
    s.append(msg.getTitle());
    s.append("]]></MediaId><Description><![CDATA[");
    s.append(msg.getDescription());
    s.append("]]></Description></Video>");
    return s.toString();
  }

  /**
   * 构造音乐消息字符串
   * 
   * @param msg 消息
   * @return 字符串
   */
  private String encodeMusicMsg(MusicMsg msg){
    StringBuilder s = new StringBuilder();
    s.append("<Music><Title><![CDATA[");
    s.append(msg.getTitle());
    s.append("]]></Title><Description><![CDATA[");
    s.append(msg.getDescription());
    s.append("]]></Description><MusicURL><![CDATA[");
    s.append(msg.getMusicUrl());
    s.append("]]></MusicURL><HQMusicUrl><![CDATA[");
    s.append(msg.getHqMusicUrl());
    s.append("]]></HQMusicUrl><ThumbMediaId><![CDATA[");
    s.append(msg.getThumbMediaId());
    s.append("]]></ThumbMediaId></Music>");
    return s.toString();
  }

  /**
   * 构造图文消息字符串
   * 
   * @param msg 消息
   * @return 字符串
   */
  private String encodeNewsMsg(NewsMsg msg){
    StringBuilder s = new StringBuilder();
    s.append("<ArticleCount>");
    s.append(msg.getItems().size());
    s.append("</ArticleCount>");
    s.append("<Articles>");
    for(NewsItem it:msg.getItems()){
      s.append("<item>");
      s.append("<Title><![CDATA[");
      s.append(it.getTitle());
      s.append("]]></Title>");
      s.append("<Description><![CDATA[");
      s.append(it.getDescription());
      s.append("]]></Description>");
      s.append("<PicUrl><![CDATA[");
      s.append(it.getPicUrl());
      s.append("]]></PicUrl>");
      s.append("<Url><![CDATA[");
      s.append(it.getUrl());
      s.append("]]></Url>");
      s.append("</item>");
    }
    s.append("</Articles>");
    return s.toString();
  }

  public String encode(IResponseMsg msg){
    if(null==msg){
      return "";
    }
    String time = System.currentTimeMillis()+"";
    time = time.substring(0,time.length()-3);
    StringBuilder s = new StringBuilder();
    s.append("<xml>");
    s.append("<ToUserName><![CDATA[");
    s.append(msg.getToUser());
    s.append("]]></ToUserName>");
    s.append("<FromUserName><![CDATA[");
    s.append(msg.getFromUser());
    s.append("]]></FromUserName>");
    s.append("<MsgType><![CDATA[");
    s.append(msg.getMsgType());
    s.append("]]></MsgType>");
    s.append("<CreateTime>");
    s.append(time);
    s.append("</CreateTime>");
    switch(msg.getMsgType()){
      case text:
        s.append(encodeTextMsg((TextMsg)msg));
        break;
      case transfer_customer_service:
        s.append(encodeTransferCustomerMsg((TransferCustomerMsg)msg));
        break;
      case image:
        s.append(encodeImageMsg((ImageMsg)msg));
        break;
      case voice:
        s.append(encodeVoiceMsg((VoiceMsg)msg));
        break;
      case video:
        s.append(encodeVideoMsg((VideoMsg)msg));
        break;
      case music:
        s.append(encodeMusicMsg((MusicMsg)msg));
        break;
      case news:
        s.append(encodeNewsMsg((NewsMsg)msg));
        break;
      default:
        break;
    }
    s.append("</xml>");
    return s.toString();
  }
}