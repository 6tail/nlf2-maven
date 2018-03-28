package com.nlf.extend.wechat.qrcode;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.extend.wechat.exception.WeixinException;
import com.nlf.extend.wechat.qrcode.bean.QrCodeRequest;
import com.nlf.extend.wechat.qrcode.bean.QrCodeResponse;
import com.nlf.extend.wechat.qrcode.type.QrCodeType;
import com.nlf.extend.wechat.util.HttpsClient;
import com.nlf.log.Logger;
import com.nlf.serialize.json.JSON;

/**
 * 二维码工具类
 * 
 * @author 6tail
 *
 */
public class QrCodeHelper{

  private QrCodeHelper(){}

  /**
   * 创建二维码
   * 
   * @param request 二维码请求
   * @param accessToken
   * @return 二维码响应
   * @throws WeixinException
   */
  public static QrCodeResponse createQrCode(QrCodeRequest request,String accessToken) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.qrcode_create",accessToken);
      Bean dataBean = new Bean();
      dataBean.set("action_name",request.getType().toString());
      dataBean.set("action_info",new Bean().set("scene",new Bean().set("scene_id",request.getSceneId())));
      if(QrCodeType.QR_SCENE==request.getType()){
        dataBean.set("expire_seconds",request.getExpireIn());
      }
      String data = JSON.fromObject(dataBean);
      Logger.getLog().debug(App.getProperty("nlf.weixin.send",data));
      String result = HttpsClient.post(url,data);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
      QrCodeResponse response = new QrCodeResponse();
      response.setExpireIn(o.getInt("expire_seconds",1800));
      response.setTicket(o.getString("ticket"));
      response.setUrl(o.getString("url"));
      return response;
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }

  /**
   * 获取二维码图片URL
   * 
   * @param ticket 二维码ticket
   * @return 二维码图片的URL
   * @throws WeixinException
   */
  public static String showQrCode(String ticket){
    return App.getProperty("nlf.weixin.url.qrcode_show",ticket);
  }
}