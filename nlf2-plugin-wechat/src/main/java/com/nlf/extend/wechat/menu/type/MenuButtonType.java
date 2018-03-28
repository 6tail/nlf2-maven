package com.nlf.extend.wechat.menu.type;

/**
 * 菜单按钮类型
 * 
 * @author 6tail
 * 
 */
public enum MenuButtonType{
  /** 点击推事件 */
  click,
  /** 跳转URL */
  view,
  /** 扫码推事件 */
  scancode_push,
  /** 扫码推事件且弹出“消息接收中”提示框 */
  scancode_waitmsg,
  /** 弹出系统拍照发图 */
  pic_sysphoto,
  /** 弹出拍照或者相册发图 */
  pic_photo_or_album,
  /** 弹出微信相册发图器 */
  pic_weixin,
  /** 弹出地理位置选择器 */
  location_select
}