package com.nlf.extend.serialize.xml;

import com.nlf.serialize.ConvertFactory;

/**
 * XML序列化工具
 * 
 * @author 6tail
 *
 */
public class XML{
  /**
   * 将指定对象转换为xml字符串
   * 
   * @param o 对象
   * @return xml字符串
   */
  public static String fromObject(Object o){
    return ConvertFactory.getWrapper("xml").wrap(o);
  }

  /**
   * 将字符串转换为Bean或者List<Bean>
   * 
   * @param s 字符串
   * @return Bean或者List<Bean>
   */
  public static <T>T toBean(String s){
    return ConvertFactory.getParser("xml").parse(s);
  }
}