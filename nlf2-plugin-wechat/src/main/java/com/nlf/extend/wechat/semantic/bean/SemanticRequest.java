package com.nlf.extend.wechat.semantic.bean;

/**
 * 语义理解请求
 * 
 * @author 6tail
 *
 */
public class SemanticRequest{
  /** 输入文本串 */
  private String query;
  /** 需要使用的服务类型，多个用“，”隔开，不能为空 */
  private String category;
  /** 纬度坐标，与经度同时传入；与城市二选一传入 */
  private String latitude;
  /** 经度坐标，与纬度同时传入；与城市二选一传入 */
  private String longitude;
  /** 城市名称，与经纬度二选一传入 */
  private String city;
  /** 区域名称，在城市存在的情况下可省；与经纬度二选一传入 */
  private String region;
  /** 公众号唯一标识，用于区分公众号开发者 */
  private String appid;
  /** 用户唯一id（非开发者id），用户区分公众号下的不同用户（建议填入用户openid），如果为空，则无法使用上下文理解功能。appid和uid同时存在的情况下，才可以使用上下文理解功能。 */
  private String uid;

  public String getQuery(){
    return query;
  }

  public void setQuery(String query){
    this.query = query;
  }

  public String getCategory(){
    return category;
  }

  public void setCategory(String category){
    this.category = category;
  }

  public String getLatitude(){
    return latitude;
  }

  public void setLatitude(String latitude){
    this.latitude = latitude;
  }

  public String getLongitude(){
    return longitude;
  }

  public void setLongitude(String longitude){
    this.longitude = longitude;
  }

  public String getCity(){
    return city;
  }

  public void setCity(String city){
    this.city = city;
  }

  public String getRegion(){
    return region;
  }

  public void setRegion(String region){
    this.region = region;
  }

  public String getAppid(){
    return appid;
  }

  public void setAppid(String appid){
    this.appid = appid;
  }

  public String getUid(){
    return uid;
  }

  public void setUid(String uid){
    this.uid = uid;
  }
}