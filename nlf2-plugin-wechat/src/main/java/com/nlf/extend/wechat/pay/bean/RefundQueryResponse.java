package com.nlf.extend.wechat.pay.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * 退款查询返回结果
 * 
 * @author 6tail
 *
 */
public class RefundQueryResponse{
  /** SUCCESS/FAIL */
  private String return_code;
  /** 返回信息，如非空，为错误原因 */
  private String return_msg;
  private String result_code;
  private String err_code;
  private String err_code_des;
  private String appid;
  private String mch_id;
  private String device_info;
  private String nonce_str;
  private String sign;
  private String transaction_id;
  private String out_trade_no;
  private int refund_count;
  private List<RefoundDetail> details = new ArrayList<RefoundDetail>();

  public String getReturn_code(){
    return return_code;
  }

  public void setReturn_code(String return_code){
    this.return_code = return_code;
  }

  public String getReturn_msg(){
    return return_msg;
  }

  public void setReturn_msg(String return_msg){
    this.return_msg = return_msg;
  }

  public String getResult_code(){
    return result_code;
  }

  public void setResult_code(String result_code){
    this.result_code = result_code;
  }

  public String getErr_code(){
    return err_code;
  }

  public void setErr_code(String err_code){
    this.err_code = err_code;
  }

  public String getErr_code_des(){
    return err_code_des;
  }

  public void setErr_code_des(String err_code_des){
    this.err_code_des = err_code_des;
  }

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

  public int getRefund_count(){
    return refund_count;
  }

  public void setRefund_count(int refund_count){
    this.refund_count = refund_count;
  }

  public List<RefoundDetail> getDetails(){
    return details;
  }

  public void setDetails(List<RefoundDetail> details){
    this.details = details;
  }

  public void addDetail(RefoundDetail detail){
    details.add(detail);
  }
}