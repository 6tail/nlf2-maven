package com.nlf.extend.wechat.menu.bean;

import java.util.ArrayList;
import java.util.List;
import com.nlf.extend.wechat.menu.type.MenuButtonType;

/**
 * 菜单按钮
 * 
 * @author 6tail
 *
 */
public class MenuButton{
  /** 按钮类型 */
  private MenuButtonType type;
  /** 菜单标题，不超过16个字节，子菜单不超过40个字节 */
  private String name;
  /** 菜单KEY值，用于消息接口推送，不超过128字节 */
  private String key;
  /** 网页链接，用户点击菜单可打开链接，不超过256字节 */
  private String url;
  /** 下级菜单 */
  private List<MenuButton> subs = new ArrayList<MenuButton>();

  public MenuButtonType getType(){
    return type;
  }

  public void setType(MenuButtonType type){
    this.type = type;
  }

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public String getKey(){
    return key;
  }

  public void setKey(String key){
    this.key = key;
  }

  public String getUrl(){
    return url;
  }

  public void setUrl(String url){
    this.url = url;
  }

  public List<MenuButton> getSubs(){
    return subs;
  }

  public void setSubs(List<MenuButton> subs){
    this.subs = subs;
  }

  public void addSub(MenuButton sub){
    subs.add(sub);
  }
}