package com.nlf.extend.wechat.pay.bean;

/**
 * 退款申请
 * 
 * @author 6tail
 *
 */
public class RefundRequest{
  private String appid;
  private String mch_id;
  private String device_info;
  private String nonce_str;
  private String sign;
  private String transaction_id;
  private String out_trade_no;
  private String out_refund_no;
  private int total_fee;
  private int refund_fee;
  private String op_user_id;

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

  public int getTotal_fee(){
    return total_fee;
  }

  public void setTotal_fee(int total_fee){
    this.total_fee = total_fee;
  }

  public int getRefund_fee(){
    return refund_fee;
  }

  public void setRefund_fee(int refund_fee){
    this.refund_fee = refund_fee;
  }

  public String getOp_user_id(){
    return op_user_id;
  }

  public void setOp_user_id(String op_user_id){
    this.op_user_id = op_user_id;
  }
}