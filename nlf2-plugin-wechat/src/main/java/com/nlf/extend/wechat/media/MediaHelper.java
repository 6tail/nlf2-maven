package com.nlf.extend.wechat.media;

import java.io.File;
import com.nlf.App;
import com.nlf.Bean;
import com.nlf.extend.wechat.exception.WeixinException;
import com.nlf.extend.wechat.media.bean.Media;
import com.nlf.extend.wechat.util.HttpClient;
import com.nlf.log.Logger;
import com.nlf.serialize.json.JSON;

/**
 * 媒体工具
 * 
 * @author 6tail
 *
 */
public class MediaHelper{

  protected MediaHelper(){}

  /**
   * 媒体上传
   * 
   * @param file 文件
   * @param accessToken
   * @param type 媒体类型
   * @return 媒体信息
   * @throws WeixinException
   */
  public static Media upload(File file,String accessToken,MediaType type) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.media_upload",accessToken,type);
      String result = HttpClient.upload(url,file);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,o.getString("errmsg"));
      }
      Media media = new Media();
      media.setMediaId(o.getString("media_id"));
      media.setType(MediaType.valueOf(o.getString("type")));
      media.setCreatedAt(o.getString("created_at"));
      return media;
    }catch(WeixinException e){
      throw e;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
  
  /**
   * 下载媒体文件
   * @param accessToken
   * @param mediaId 媒体ID
   * @param file 保存文件
   * @throws WeixinException
   */
  public static void download(String accessToken,String mediaId,File file) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.media_get",accessToken,mediaId);
      HttpClient.download(url,file);
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}