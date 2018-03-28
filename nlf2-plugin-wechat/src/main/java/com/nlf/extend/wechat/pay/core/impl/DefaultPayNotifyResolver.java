package com.nlf.extend.wechat.pay.core.impl;

import com.nlf.Bean;
import com.nlf.extend.serialize.xml.XML;
import com.nlf.extend.wechat.pay.bean.PayNotifyRequest;
import com.nlf.extend.wechat.pay.bean.PayNotifyResponse;
import com.nlf.extend.wechat.pay.core.IPayNotifyResolver;
import com.nlf.util.StringUtil;

/**
 * 默认微信支付通知解析器
 * 
 * @author 6tail
 *
 */
public class DefaultPayNotifyResolver implements IPayNotifyResolver{
  public PayNotifyRequest decode(String str){
    Bean o = XML.toBean(str);
    PayNotifyRequest req = new PayNotifyRequest();
    req.setAppid(o.getString("appid"));
    req.setAttach(o.getString("attach"));
    req.setBank_type(o.getString("bank_type"));
    req.setCoupon_fee(o.getInt("coupon_fee",0));
    req.setDevice_info(o.getString("device_info"));
    req.setErr_code(o.getString("err_code"));
    req.setErr_code_des(o.getString("err_code_des"));
    req.setFee_type(o.getString("fee_type"));
    req.setIs_subscribe(o.getString("is_subscribe"));
    req.setMch_id(o.getString("mch_id"));
    req.setNonce_str(o.getString("nonce_str"));
    req.setOpenid(o.getString("openid"));
    req.setOut_trade_no(o.getString("out_trade_no"));
    req.setResult_code(o.getString("result_code"));
    req.setReturn_code(o.getString("return_code"));
    req.setReturn_msg(o.getString("return_msg"));
    req.setSign(o.getString("sign"));
    req.setTime_end(o.getString("time_end"));
    req.setTrade_type(o.getString("trade_type"));
    req.setTransaction_id(o.getString("transaction_id"));
    req.setTotal_fee(o.getInt("total_fee",0));
    return req;
  }

  public String encode(PayNotifyResponse msg){
    if(null==msg){
      return "";
    }
    String data = XML.fromObject(msg);
    data = StringUtil.right(data,">");
    return data;
  }
}