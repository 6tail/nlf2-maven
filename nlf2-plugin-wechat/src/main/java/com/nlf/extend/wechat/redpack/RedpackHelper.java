package com.nlf.extend.wechat.redpack;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import com.nlf.App;
import com.nlf.Bean;
import com.nlf.extend.serialize.xml.XML;
import com.nlf.extend.wechat.exception.WeixinException;
import com.nlf.extend.wechat.redpack.bean.RedpackRequest;
import com.nlf.extend.wechat.redpack.bean.RedpackResponse;
import com.nlf.extend.wechat.util.HttpsClient;
import com.nlf.log.Logger;
import com.nlf.serialize.json.JSON;
import com.nlf.util.Md5Util;
import com.nlf.util.StringUtil;

/**
 * 微信红包工具类
 * 
 * @author 6tail
 *
 */
public class RedpackHelper{
  
  protected RedpackHelper(){}

  /**
   * 签名
   * 
   * @param refundRequest 发送红包请求
   * @param paySecret 密钥
   * @return MD5签名
   * @throws NoSuchAlgorithmException
   */
  public static String sign(RedpackRequest refundRequest,String paySecret) throws NoSuchAlgorithmException{
    Bean o = JSON.toBean(JSON.fromObject(refundRequest));
    o.remove("sign");
    SortedMap<String,String> map = new TreeMap<String,String>();
    for(String key:o.keySet()){
      String value = o.getString(key,"");
      if(value.length()>0){
        map.put(key,o.getString(key));
      }
    }
    StringBuffer sb = new StringBuffer();
    for(String key:map.keySet()){
      sb.append(key);
      sb.append("=");
      sb.append(map.get(key));
      sb.append("&");
    }
    sb.append("key=");
    sb.append(paySecret);
    String s = sb.toString();
    Logger.getLog().debug(App.getProperty("nlf.weixin.sign_data",s));
    s = Md5Util.encode(s,"utf-8");
    Logger.getLog().debug(App.getProperty("nlf.weixin.sign_result",s));
    return s;
  }

  /**
   * 发送红包
   * 
   * @param request 已签名的发送红包请求
   * @return 发送红包反馈
   * @throws WeixinException
   */
  public static RedpackResponse sendRedpack(RedpackRequest request,File certFile) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.redpack_send");
      Bean bean = JSON.toBean(JSON.fromObject(request));
      List<String> args = new ArrayList<String>();
      Iterator<String> it = bean.keySet().iterator();
      String key;
      while(it.hasNext()){
        key = it.next();
        if(bean.getString(key,"").length()<1){
          args.add(key);
        }
      }
      for(String k:args){
        bean.remove(k);
      }
      String data = XML.fromObject(bean);
      data = StringUtil.right(data,">");
      Logger.getLog().debug(App.getProperty("nlf.weixin.send",data));
      String result = HttpsClient.post(certFile,request.getMch_id(),url,data);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
      Bean o = XML.toBean(result);
      RedpackResponse r = new RedpackResponse();
      r.setErr_code(o.getString("err_code"));
      r.setErr_code_des(o.getString("err_code_des"));
      r.setMch_billno(o.getString("mch_billno"));
      r.setMch_id(o.getString("mch_id"));
      r.setRe_openid(o.getString("re_openid"));
      r.setResult_code(o.getString("result_code"));
      r.setReturn_code(o.getString("return_code"));
      r.setReturn_msg(o.getString("return_msg"));
      r.setSign(o.getString("sign"));
      r.setTotal_amount(o.getInt("total_amount",0));
      r.setWxappid(o.getString("wxappid"));
      return r;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}