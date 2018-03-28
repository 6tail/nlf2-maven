package com.nlf.extend.wechat.pay;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import com.nlf.App;
import com.nlf.Bean;
import com.nlf.extend.serialize.xml.XML;
import com.nlf.extend.wechat.exception.WeixinException;
import com.nlf.extend.wechat.pay.bean.PayNotifyRequest;
import com.nlf.extend.wechat.pay.bean.PrePayResult;
import com.nlf.extend.wechat.pay.bean.UnifiedOrder;
import com.nlf.extend.wechat.util.HttpsClient;
import com.nlf.log.Logger;
import com.nlf.serialize.json.JSON;
import com.nlf.util.Md5Util;
import com.nlf.util.StringUtil;

/**
 * 支付工具类
 * 
 * @author 6tail
 *
 */
public class PayHelper{
  private static final String CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";

  protected PayHelper(){}

  /**
   * 生成timestamp
   * @return timestamp
   */
  public static String genTimestamp(){
    return (System.currentTimeMillis()/1000)+"";
  }

  /**
   * 生成nonceStr
   * @return nonceStr
   */
  public static String genNonceStr(){
    int l = CHARS.length();
    Random random = new Random();
    StringBuffer sb = new StringBuffer();
    for(int i = 0;i<16;i++){
      int number = random.nextInt(l);
      sb.append(CHARS.charAt(number));
    }
    return sb.toString();
  }
  
  public static String signPayNotify(PayNotifyRequest request,String secret) throws NoSuchAlgorithmException{
    Bean o = JSON.toBean(JSON.fromObject(request));
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
    sb.append(secret);
    String s = sb.toString();
    Logger.getLog().debug(App.getProperty("nlf.weixin.sign_data",s));
    s = Md5Util.encode(s,"utf-8");
    Logger.getLog().debug(App.getProperty("nlf.weixin.sign_result",s));
    return s;
  }

  /**
   * 签名
   * 
   * @param order 未签名的预支付单
   * @param secret 密钥
   * @return MD5签名
   * @throws NoSuchAlgorithmException
   */
  public static String sign(UnifiedOrder order,String secret) throws NoSuchAlgorithmException{
    Bean o = JSON.toBean(JSON.fromObject(order));
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
    sb.append(secret);
    String s = sb.toString();
    Logger.getLog().debug(App.getProperty("nlf.weixin.sign_data",s));
    s = Md5Util.encode(s,"utf-8");
    Logger.getLog().debug(App.getProperty("nlf.weixin.sign_result",s));
    return s;
  }

  /**
   * 预支付
   * 
   * @param signedOrder 已签名的预支付单
   * @return 预支付结果
   * @throws WeixinException
   */
  public static PrePayResult prePay(UnifiedOrder signedOrder) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.pay_unifiedorder");
      Bean bean = JSON.toBean(JSON.fromObject(signedOrder));
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
      String result = HttpsClient.post(url,data);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
      Bean o = XML.toBean(result);
      PrePayResult r = new PrePayResult();
      r.setReturn_code(o.getString("return_code"));
      r.setReturn_msg(o.getString("return_msg"));
      if("SUCCESS".equals(r.getReturn_code())){
        r.setAppid(o.getString("appid"));
        r.setMch_id(o.getString("mch_id"));
        r.setDevice_info(o.getString("device_info"));
        r.setNonce_str(o.getString("nonce_str"));
        r.setSign(o.getString("sign"));
        r.setResult_code(o.getString("result_code"));
        r.setErr_code(o.getString("err_code"));
        r.setErr_code_des(o.getString("err_code_des"));
        if("SUCCESS".equals(r.getResult_code())){
          r.setTrade_type(o.getString("trade_type"));
          r.setPrepay_id(o.getString("prepay_id"));
          r.setCode_url(o.getString("code_url"));
        }
      }
      return r;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}