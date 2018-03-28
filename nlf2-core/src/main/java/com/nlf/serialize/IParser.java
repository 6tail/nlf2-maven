package com.nlf.serialize;


/**
 * 解析接口
 * 
 * @author 6tail
 *
 */
public interface IParser{
  /**
   * 将指定字符串解析为Bean或者List<Bean>
   * 
   * @param s 字符串
   * @return Bean或者List<Bean>
   */
  <T>T parse(String s);

  /**
   * 是否支持该格式
   * 
   * @param format 格式，如json、xml
   * @return true支持；false不支持
   */
  boolean support(String format);
}