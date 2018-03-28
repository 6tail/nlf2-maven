package com.nlf.extend.wechat.pay.bean;

/**
 * 支付结果通知
 * 
 * @author 6tail
 *
 */
public class PayNotifyRequest{
  /** SUCCESS/FAIL此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断 */
  private String return_code;
  /** 返回信息，如非空，为错误原因 */
  private String return_msg;
  private String appid;
  private String mch_id;
  private String device_info;
  private String nonce_str;
  private String sign;
  private String result_code;
  private String err_code;
  private String err_code_des;
  private String openid;
  private String is_subscribe;
  private String trade_type;
  private String bank_type;
  private int total_fee;
  private int coupon_fee;
  private String fee_type;
  private String transaction_id;
  private String out_trade_no;
  private String attach;
  private String time_end;

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

  public String getOpenid(){
    return openid;
  }

  public void setOpenid(String openid){
    this.openid = openid;
  }

  public String getIs_subscribe(){
    return is_subscribe;
  }

  public void setIs_subscribe(String is_subscribe){
    this.is_subscribe = is_subscribe;
  }

  public String getTrade_type(){
    return trade_type;
  }

  public void setTrade_type(String trade_type){
    this.trade_type = trade_type;
  }

  public String getBank_type(){
    return bank_type;
  }

  public void setBank_type(String bank_type){
    this.bank_type = bank_type;
  }

  public int getTotal_fee(){
    return total_fee;
  }

  public void setTotal_fee(int total_fee){
    this.total_fee = total_fee;
  }

  public int getCoupon_fee(){
    return coupon_fee;
  }

  public void setCoupon_fee(int coupon_fee){
    this.coupon_fee = coupon_fee;
  }

  public String getFee_type(){
    return fee_type;
  }

  public void setFee_type(String fee_type){
    this.fee_type = fee_type;
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

  public String getAttach(){
    return attach;
  }

  public void setAttach(String attach){
    this.attach = attach;
  }

  public String getTime_end(){
    return time_end;
  }

  public void setTime_end(String time_end){
    this.time_end = time_end;
  }
}