package com.nlf.extend.wechat.semantic.bean;

/**
 * 语义识别：航班
 * 
 * @author 6tail
 *
 */
public class SemanticResponseFlight extends SemanticResponse{
  /** 航空公司 */
  private String airline;
  /** 出发城市名称 */
  private String depCityName;
  /** 出发城市简称 */
  private String depCityNameSimple;
  /** 用户输入的出发城市 */
  private String dep;
  /** 到达城市名称 */
  private String arrCityName;
  /** 到达城市简称 */
  private String arrCityNameSimple;
  /** 用户输入的到达城市 */
  private String arr;
  /** 用户输入的日期 */
  private String day;
  /** 识别出的日期 */
  private String dayInYmd;

  public String getAirline(){
    return airline;
  }

  public void setAirline(String airline){
    this.airline = airline;
  }

  public String getDepCityName(){
    return depCityName;
  }

  public void setDepCityName(String depCityName){
    this.depCityName = depCityName;
  }

  public String getDepCityNameSimple(){
    return depCityNameSimple;
  }

  public void setDepCityNameSimple(String depCityNameSimple){
    this.depCityNameSimple = depCityNameSimple;
  }

  public String getDep(){
    return dep;
  }

  public void setDep(String dep){
    this.dep = dep;
  }

  public String getArrCityName(){
    return arrCityName;
  }

  public void setArrCityName(String arrCityName){
    this.arrCityName = arrCityName;
  }

  public String getArrCityNameSimple(){
    return arrCityNameSimple;
  }

  public void setArrCityNameSimple(String arrCityNameSimple){
    this.arrCityNameSimple = arrCityNameSimple;
  }

  public String getArr(){
    return arr;
  }

  public void setArr(String arr){
    this.arr = arr;
  }

  public String getDay(){
    return day;
  }

  public void setDay(String day){
    this.day = day;
  }

  public String getDayInYmd(){
    return dayInYmd;
  }

  public void setDayInYmd(String dayInYmd){
    this.dayInYmd = dayInYmd;
  }
}