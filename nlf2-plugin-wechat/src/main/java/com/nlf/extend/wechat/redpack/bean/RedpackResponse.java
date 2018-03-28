package com.nlf.extend.wechat.redpack.bean;

/**
 * 发送红包反馈
 * 
 * @author 6tail
 *
 */
public class RedpackResponse{
  /** SUCCESS/FAIL */
  private String return_code;
  /** 返回信息，如非空，为错误原因 */
  private String return_msg;
  private String result_code;
  private String err_code;
  private String err_code_des;
  private String sign;
  private String wxappid;
  private String mch_id;
  private String mch_billno;
  private String re_openid;
  private int total_amount;

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

  public String getSign(){
    return sign;
  }

  public void setSign(String sign){
    this.sign = sign;
  }

  public String getWxappid(){
    return wxappid;
  }

  public void setWxappid(String wxappid){
    this.wxappid = wxappid;
  }

  public String getMch_id(){
    return mch_id;
  }

  public void setMch_id(String mch_id){
    this.mch_id = mch_id;
  }

  public String getMch_billno(){
    return mch_billno;
  }

  public void setMch_billno(String mch_billno){
    this.mch_billno = mch_billno;
  }

  public String getRe_openid(){
    return re_openid;
  }

  public void setRe_openid(String re_openid){
    this.re_openid = re_openid;
  }

  public int getTotal_amount(){
    return total_amount;
  }

  public void setTotal_amount(int total_amount){
    this.total_amount = total_amount;
  }
}