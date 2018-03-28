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
import com.nlf.extend.wechat.pay.bean.RefoundDetail;
import com.nlf.extend.wechat.pay.bean.RefundQueryRequest;
import com.nlf.extend.wechat.pay.bean.RefundQueryResponse;
import com.nlf.extend.wechat.util.HttpsClient;
import com.nlf.log.Logger;
import com.nlf.serialize.json.JSON;
import com.nlf.util.Md5Util;
import com.nlf.util.StringUtil;

/**
 * 退款查询工具类
 * 
 * @author 6tail
 *
 */
public class RefundQueryHelper{

  protected RefundQueryHelper(){}

  /**
   * 签名
   * 
   * @param refundQueryRequest 退款查询请求
   * @param paySecret 密钥
   * @return MD5签名
   * @throws NoSuchAlgorithmException
   */
  public static String sign(RefundQueryRequest refundQueryRequest,String paySecret) throws NoSuchAlgorithmException{
    Bean o = JSON.toBean(JSON.fromObject(refundQueryRequest));
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
   * 退款查询
   * 
   * @param signedRefundQueryRequest 已签名的退款查询请求
   * @return 退款查询结果
   * @throws WeixinException
   */
  public static RefundQueryResponse query(RefundQueryRequest signedRefundQueryRequest,File certFile) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.pay_refund_query");
      Bean bean = JSON.toBean(JSON.fromObject(signedRefundQueryRequest));
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
      String result = HttpsClient.post(certFile,signedRefundQueryRequest.getMch_id(),url,data);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
      Bean o = XML.toBean(result);
      RefundQueryResponse r = new RefundQueryResponse();
      r.setReturn_code(o.getString("return_code"));
      r.setReturn_msg(o.getString("return_msg"));
      r.setAppid(o.getString("appid"));
      r.setMch_id(o.getString("mch_id"));
      r.setDevice_info(o.getString("device_info"));
      r.setNonce_str(o.getString("nonce_str"));
      r.setSign(o.getString("sign"));
      r.setResult_code(o.getString("result_code"));
      r.setErr_code(o.getString("err_code"));
      r.setErr_code_des(o.getString("err_code_des"));
      r.setTransaction_id(o.getString("transaction_id"));
      r.setOut_trade_no(o.getString("out_trade_no"));
      r.setRefund_count(o.getInt("refund_count",0));
      for(int i=0,j=r.getRefund_count();i<j;i++){
        RefoundDetail detail = new RefoundDetail();
        detail.setOut_refund_no(o.getString("out_refund_no_"+i));
        detail.setRefund_id(o.getString("refund_id_"+i));
        detail.setRefund_channel(o.getString("refund_channel_"+i));
        detail.setRefund_fee(o.getInt("refund_fee_"+i,0));
        detail.setCoupon_refund_fee(o.getInt("coupon_refund_fee_"+i,0));
        detail.setRefund_status(o.getString("refund_status_"+i));
        r.addDetail(detail);
      }
      return r;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}