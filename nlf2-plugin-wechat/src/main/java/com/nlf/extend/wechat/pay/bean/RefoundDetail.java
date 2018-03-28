package com.nlf.extend.wechat.pay.bean;

/**
 * 退款详情
 * 
 * @author 6tail
 *
 */
public class RefoundDetail{
  private String out_refund_no;
  private String refund_id;
  private String refund_channel;
  private int refund_fee;
  private int coupon_refund_fee;
  private String refund_status;

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

  public String getRefund_channel(){
    return refund_channel;
  }

  public void setRefund_channel(String refund_channel){
    this.refund_channel = refund_channel;
  }

  public int getRefund_fee(){
    return refund_fee;
  }

  public void setRefund_fee(int refund_fee){
    this.refund_fee = refund_fee;
  }

  public int getCoupon_refund_fee(){
    return coupon_refund_fee;
  }

  public void setCoupon_refund_fee(int coupon_refund_fee){
    this.coupon_refund_fee = coupon_refund_fee;
  }

  public String getRefund_status(){
    return refund_status;
  }

  public void setRefund_status(String refund_status){
    this.refund_status = refund_status;
  }
}