package com.nlf.extend.wechat.pay;

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
import com.nlf.extend.wechat.pay.bean.RefundRequest;
import com.nlf.extend.wechat.pay.bean.RefundResponse;
import com.nlf.extend.wechat.util.HttpsClient;
import com.nlf.log.Logger;
import com.nlf.serialize.json.JSON;
import com.nlf.util.Md5Util;
import com.nlf.util.StringUtil;

/**
 * 退款工具类
 * 
 * @author 6tail
 *
 */
public class RefundHelper{
  protected RefundHelper(){}

  /**
   * 签名
   * 
   * @param refundRequest 退款申请
   * @param paySecret 密钥
   * @return MD5签名
   * @throws NoSuchAlgorithmException
   */
  public static String sign(RefundRequest refundRequest,String paySecret) throws NoSuchAlgorithmException{
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
   * 退款申请
   * 
   * @param refundRequest 已签名的退款申请
   * @return 退款申请反馈
   * @throws WeixinException
   */
  public static RefundResponse refund(RefundRequest refundRequest,File certFile) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.pay_refund");
      Bean bean = JSON.toBean(JSON.fromObject(refundRequest));
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
      String result = HttpsClient.post(certFile,refundRequest.getMch_id(),url,data);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
      Bean o = XML.toBean(result);
      RefundResponse r = new RefundResponse();
      r.setAppid(o.getString("appid"));
      r.setCoupon_refund_fee(o.getInt("coupon_refund_fee",0));
      r.setDevice_info(o.getString("device_info"));
      r.setErr_code(o.getString("err_code"));
      r.setErr_code_des(o.getString("err_code_des"));
      r.setMch_id(o.getString("mch_id"));
      r.setNonce_str(o.getString("nonce_str"));
      r.setOut_refund_no(o.getString("out_refund_no"));
      r.setOut_trade_no(o.getString("out_trade_no"));
      r.setRefund_channel(o.getString("refund_channel"));
      r.setRefund_fee(o.getInt("refund_fee",0));
      r.setRefund_id(o.getString("refund_id"));
      r.setResult_code(o.getString("result_code"));
      r.setReturn_code(o.getString("return_code"));
      r.setReturn_msg(o.getString("return_msg"));
      r.setSign(o.getString("sign"));
      r.setTransaction_id(o.getString("transaction_id"));
      return r;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}