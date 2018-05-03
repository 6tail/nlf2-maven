package com.nlf.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期工具
 * 
 * @author 6tail
 */
public class DateUtil{
  /** 月份中文 */
  private static final String[] MONTH_CH = {"一","二","三","四","五","六","七","八","九","十","十一","十二"};
  /** 月份 */
  private static final Map<String,String> MONTH = new HashMap<String,String>();
  /** 星期 */
  private static final Map<String,String> WEEK = new HashMap<String,String>();
  /** 中文数字 */
  private static final Map<String,String> NUMBER = new HashMap<String,String>();
  static{
    MONTH.put("01","JAN");
    MONTH.put("02","FEB");
    MONTH.put("03","MAR");
    MONTH.put("04","APR");
    MONTH.put("05","MAY");
    MONTH.put("06","JUN");
    MONTH.put("07","JUL");
    MONTH.put("08","AUG");
    MONTH.put("09","SEP");
    MONTH.put("10","OCT");
    MONTH.put("11","NOV");
    MONTH.put("12","DEC");
    MONTH.put("JAN","01");
    MONTH.put("FEB","02");
    MONTH.put("MAR","03");
    MONTH.put("APR","04");
    MONTH.put("MAY","05");
    MONTH.put("JUN","06");
    MONTH.put("JUL","07");
    MONTH.put("AUG","08");
    MONTH.put("SEP","09");
    MONTH.put("OCT","10");
    MONTH.put("NOV","11");
    MONTH.put("DEC","12");
    WEEK.put(Calendar.SUNDAY+"","日");
    WEEK.put(Calendar.MONDAY+"","一");
    WEEK.put(Calendar.TUESDAY+"","二");
    WEEK.put(Calendar.WEDNESDAY+"","三");
    WEEK.put(Calendar.THURSDAY+"","四");
    WEEK.put(Calendar.FRIDAY+"","五");
    WEEK.put(Calendar.SATURDAY+"","六");
    NUMBER.put("0","零");
    NUMBER.put("1","一");
    NUMBER.put("2","二");
    NUMBER.put("3","三");
    NUMBER.put("4","四");
    NUMBER.put("5","五");
    NUMBER.put("6","六");
    NUMBER.put("7","七");
    NUMBER.put("8","八");
    NUMBER.put("9","九");
  }

  private DateUtil(){}

  /**
   * 获取当前时间
   * 
   * @return 当前时间
   */
  public static Date now(){
    return new Date(System.currentTimeMillis());
  }

  /**
   * 将yyyy-MM-dd或yyyy/MM/dd格式的日期字符串转换为日期类型
   * 
   * @param ymd yyyy-MM-dd或yyyy/MM/dd格式的字符串
   * @return 日期
   * @throws ParseException 日期转换异常
   */
  public static Date ymd2Date(String ymd) throws ParseException{
    String nymd = ymd.trim();
    if(!nymd.contains("-")&&!nymd.contains("/")){
      throw new ParseException("日期格式无法转换："+ymd,0);
    }
    String year = "";
    if(nymd.contains("-")){
      year = nymd.substring(0,nymd.indexOf("-"));
    }else if(nymd.contains("/")){
      year = nymd.substring(0,nymd.indexOf("/"));
    }
    if(year.length()==2){
      int ny = Integer.parseInt("20"+year);
      int ly = Integer.parseInt("19"+year);
      int ty = DateUtil.year(DateUtil.now());
      if(Math.abs(ny-ty)<Math.abs(ly-ty)){
        nymd = "20"+nymd;
      }else{
        nymd = "19"+nymd;
      }
    }
    String[] f = {"yyyy-MM-dd","yyyy/MM/dd"};
    for(String o:f){
      try{
        return parse(nymd,o);
      }catch(ParseException e){}
    }
    throw new ParseException("日期格式无法转换："+ymd,0);
  }

  /**
   * 将yyyy-MM-dd HH:mm:ss格式的日期字符串转换为日期类型
   * 
   * @param ymdhms yyyy-MM-dd HH:mm:ss格式的字符串
   * @return 日期
   * @throws ParseException 日期转换异常
   */
  public static Date ymdhms2Date(String ymdhms) throws ParseException{
    return parse(ymdhms,"yyyy-MM-dd HH:mm:ss");
  }

  /**
   * 将指定格式字符串转换为日期
   * 
   * @param s 字符串
   * @param pattern 格式
   * @return 日期
   * @throws ParseException 转换异常
   */
  public static Date parse(String s,String pattern) throws ParseException{
    return new SimpleDateFormat(pattern).parse(s);
  }

  /**
   * 将日期转换成yyyy-MM-dd格式的字符串
   * 
   * @param date 日期
   * @return yyyy-MM-dd格式的字符串
   */
  public static String ymd(Date date){
    return format(date,"yyyy-MM-dd");
  }

  /**
   * 将日期转换成HH:mm:ss格式的字符串
   * 
   * @param date 日期
   * @return HH:mm:ss格式的字符串
   */
  public static String hms(Date date){
    return format(date,"HH:mm:ss");
  }

  /**
   * 将日期转换成yyyy-MM-dd HH:mm:ss格式的字符串
   * 
   * @param date 日期
   * @return yyyy-MM-dd HH:mm:ss格式的字符串
   */
  public static String ymdhms(Date date){
    return format(date,"yyyy-MM-dd HH:mm:ss");
  }

  /**
   * 将日期转换成指定格式的字符串
   * 
   * @param date 日期
   * @return 指定格式的字符串
   */
  public static String format(Date date,String pattern){
    return new SimpleDateFormat(pattern).format(date);
  }

  /**
   * 指定类型往前推
   * 
   * @param type 类型：年、月、日等
   * @param date 参考日期
   * @param length 长度
   * @return 往前推的日期
   */
  public static Date previous(int type,Date date,int length){
    return next(type,date,0-length);
  }

  /**
   * 指定类型往后推
   * 
   * @param type 类型：年、月、日等
   * @param date 参考日期
   * @param length 长度
   * @return 往后推的日期
   */
  public static Date next(int type,Date date,int length){
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    c.add(type,length);
    return c.getTime();
  }

  /**
   * 获得前几天的日期
   * 
   * @param date 参考日期
   * @return 前几天的日期
   */
  public static Date previous(Date date,int days){
    return previous(Calendar.DATE,date,days);
  }

  /**
   * 获得后几天的日期
   * 
   * @param date 参考日期
   * @return 后几天的日期
   */
  public static Date next(Date date,int days){
    return next(Calendar.DATE,date,days);
  }

  /**
   * 月份往后推的日期
   * 
   * @param date 参考日期
   * @param months 推后几个月
   * @return 月份往后推的日期
   */
  public static Date nextMonth(Date date,int months){
    return next(Calendar.MONTH,date,months);
  }

  /**
   * 月份往前推的日期
   * 
   * @param date 参考日期
   * @param months 前推几个月
   * @return 月份往前推的日期
   */
  public static Date previousMonth(Date date,int months){
    return previous(Calendar.MONTH,date,months);
  }

  /**
   * 年份往后推的日期
   * 
   * @param date 参考日期
   * @param years 推后几年
   * @return 月份往后推的日期
   */
  public static Date nextYear(Date date,int years){
    return next(Calendar.YEAR,date,years);
  }

  /**
   * 年份往前推的日期
   * 
   * @param date 参考日期
   * @param years 前推几年
   * @return 年份往前推的日期
   */
  public static Date previousYear(Date date,int years){
    return previous(Calendar.YEAR,date,years);
  }

  /**
   * 某日期的某类型的数值
   * 
   * @param type 类型：年、月、日等
   * @param date 日期
   * @return 数值
   */
  public static int get(int type,Date date){
    Calendar c = Calendar.getInstance();
    c.setTime(date);
    return c.get(type);
  }

  /**
   * 获取年份
   * 
   * @param date 日期
   * @return 年份
   */
  public static int year(Date date){
    return get(Calendar.YEAR,date);
  }

  /**
   * 获取月份
   * 
   * @param date 日期
   * @return 月份，如01、10
   */
  public static String month(Date date){
    int m = get(Calendar.MONTH,date)+1;
    return (m<10?"0":"")+m;
  }

  /**
   * 获取天
   * 
   * @param date 日期
   * @return 天，如01、23
   */
  public static String day(Date date){
    int d = get(Calendar.DATE,date);
    return (d<10?"0":"")+d;
  }

  /**
   * 日期转换为DDMMM格式
   * 
   * @param ymd YYYY-MM-DD格式的字符串
   * @return 1MAY10 01MAY10 1MAY2010 01MAY2010
   */
  public static String dm(String ymd){
    String[] n = ymd.split("-");
    return n[2]+MONTH.get(n[1])+n[0].substring(2);
  }

  /**
   * 日期字符串转换为YYYY-MM-DD格式
   * 
   * @param dayMonthYear 支持的参数格式: 1MAY 01MAY 1MAY10 01MAY10 1MAY2010 01MAY2010，不区分大小写，以上转换后为2010-05-01
   * @return YYYY-MM-DD格式的字符串
   */
  public static String ymd(String dayMonthYear){
    return ymd(dayMonthYear,"20");
  }

  /**
   * 日期字符串转换为YYYY-MM-DD格式
   * 
   * @param dayMonthYear 支持的参数格式: 1MAY 01MAY 1MAY10 01MAY10 1MAY2010 01MAY2010
   * @param yearPrefix 年份前缀，如yearPrefix=19,1MAY09=1909-05-01
   * @return YYYY-MM-DD格式的字符串
   */
  public static String ymd(String dayMonthYear,String yearPrefix){
    String s = dayMonthYear.toUpperCase();
    switch(s.length()){
      case 4:
        s = "0"+s;
        s = s+DateUtil.year(DateUtil.now());
        break;
      case 5:
        s = s+DateUtil.year(DateUtil.now());
        break;
      case 6:
        s = "0"+s;
        s = s.substring(0,5)+yearPrefix+s.substring(5);
        break;
      case 7:
        s = s.substring(0,5)+yearPrefix+s.substring(5);
        break;
      case 8:
        s = "0"+s;
        break;
    }
    s = s.substring(5)+"-"+MONTH.get(s.substring(2,5))+"-"+s.substring(0,2);
    return s;
  }

  /**
   * 获取某日期的星期中文表示
   * 
   * @param date 日期
   * @return 日、一、二、三、四、五、六
   */
  public static String weekCH(Date date){
    return WEEK.get(get(Calendar.DAY_OF_WEEK,date)+"");
  }

  /**
   * 获取某日期的月份中文表示
   * 
   * @param date 日期
   * @return 一、二、三、四、五、六
   */
  public static String monthCH(Date date){
    return MONTH_CH[get(Calendar.MONTH,date)];
  }

  /**
   * 获取某日期的年份中文表示
   * 
   * @param date 日期
   * @return 年份，如二零一零
   */
  public static String yearCH(Date date){
    String[] y = (year(date)+"").split("");
    StringBuilder s = new StringBuilder();
    for(String o:y){
      String n = NUMBER.get(o);
      if(null!=n){
        s.append(n);
      }
    }
    return s+"";
  }
}