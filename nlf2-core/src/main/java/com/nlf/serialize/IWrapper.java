package com.nlf.serialize;

/**
 * 包装接口
 * 
 * @author 6tail
 *
 */
public interface IWrapper{
  /**
   * 将指定对象包装为字符串
   * 
   * @param o 对象
   * @return 字符串
   */
  String wrap(Object o);
  
  /**
   * 是否支持该格式
   * 
   * @param format 格式，如json、xml
   * @return true支持；false不支持
   */
  boolean support(String format);
}