package com.nlf.extend.wechat.menu;

import java.util.ArrayList;
import java.util.List;
import com.nlf.App;
import com.nlf.Bean;
import com.nlf.extend.wechat.exception.WeixinException;
import com.nlf.extend.wechat.menu.bean.MenuButton;
import com.nlf.extend.wechat.menu.type.MenuButtonType;
import com.nlf.extend.wechat.util.HttpsClient;
import com.nlf.log.Logger;
import com.nlf.serialize.json.JSON;

/**
 * 菜单工具类
 * 
 * @author 6tail
 *
 */
public class MenuHelper{
  protected MenuHelper(){}

  /**
   * 创建菜单
   * 
   * @param buttons 菜单
   * @param accessToken
   * @throws WeixinException
   */
  public static void createMenu(List<MenuButton> buttons,String accessToken) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.menu_create",accessToken);
      //重新构造json
      List<Bean> btns = JSON.toBean(JSON.fromObject(buttons));
      for(Bean btn:btns){
        if(!MenuButtonType.view.toString().equals(btn.getString("type"))){
          btn.remove("url");
        }
        List<Bean> subs = btn.get("subs");
        if(subs.size()>0){
          for(Bean sub:subs){
            if(!MenuButtonType.view.toString().equals(sub.getString("type"))){
              sub.remove("url");
            }
            sub.remove("subs");
          }
          btn.set("sub_button",subs);
        }
        btn.remove("subs");
      }
      Bean dataBean = new Bean().set("button",btns);
      String data = JSON.fromObject(dataBean);
      Logger.getLog().debug(App.getProperty("nlf.weixin.send",data));
      String result = HttpsClient.post(url,data);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }

  /**
   * 删除菜单
   * 
   * @param accessToken
   * @throws WeixinException
   */
  public static void deleteMenu(String accessToken) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.menu_delete",accessToken);
      String result = HttpsClient.get(url);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }

  /**
   * 查询菜单
   * 
   * @param accessToken
   * @throws WeixinException
   */
  public static List<MenuButton> getMenu(String accessToken) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.menu_get",accessToken);
      String result = HttpsClient.get(url);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
      o = o.get("menu");
      List<Bean> btns = o.get("button");
      List<MenuButton> l = new ArrayList<MenuButton>(btns.size());
      for(Bean btn:btns){
        MenuButton b = new MenuButton();
        b.setKey(btn.getString("key"));
        b.setName(btn.getString("name"));
        b.setType(MenuButtonType.valueOf(btn.getString("type")));
        b.setUrl(btn.getString("url"));
        List<Bean> subs = btn.get("sub_button");
        if(null!=subs){
          for(Bean sub:subs){
            MenuButton sb = new MenuButton();
            sb.setKey(sub.getString("key"));
            sb.setName(sub.getString("name"));
            sb.setType(MenuButtonType.valueOf(sub.getString("type")));
            sb.setUrl(sub.getString("url"));
            b.addSub(sb);
          }
        }
      }
      return l;
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}