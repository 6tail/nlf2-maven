package com.nlf.extend.wechat.pay.bean;

/**
 * 退款查询请求
 * 
 * @author 6tail
 *
 */
public class RefundQueryRequest{
  private String appid;
  private String mch_id;
  private String device_info;
  private String nonce_str;
  private String sign;
  private String transaction_id;
  private String out_trade_no;
  private String out_refund_no;
  private String refund_id;

  public String getAppid(){
    return appid;
  }

  public void setAppid(String appid){
    this.appid = appid;
  }

  public String getMch_id(){
    return mch_id;
  }

  public void setMch_id(String mch_id){
    this.mch_id = mch_id;
  }

  public String getDevice_info(){
    return device_info;
  }

  public void setDevice_info(String device_info){
    this.device_info = device_info;
  }

  public String getNonce_str(){
    return nonce_str;
  }

  public void setNonce_str(String nonce_str){
    this.nonce_str = nonce_str;
  }

  public String getSign(){
    return sign;
  }

  public void setSign(String sign){
    this.sign = sign;
  }

  public String getTransaction_id(){
    return transaction_id;
  }

  public void setTransaction_id(String transaction_id){
    this.transaction_id = transaction_id;
  }

  public String getOut_trade_no(){
    return out_trade_no;
  }

  public void setOut_trade_no(String out_trade_no){
    this.out_trade_no = out_trade_no;
  }

  public String getOut_refund_no(){
    return out_refund_no;
  }

  public void setOut_refund_no(String out_refund_no){
    this.out_refund_no = out_refund_no;
  }

  public String getRefund_id(){
    return refund_id;
  }

  public void setRefund_id(String refund_id){
    this.refund_id = refund_id;
  }
}