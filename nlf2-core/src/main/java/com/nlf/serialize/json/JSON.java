package com.nlf.serialize.json;

import com.nlf.serialize.ConvertFactory;
import com.nlf.util.Strings;

/**
 * json序列化工具
 *
 * @author 6tail
 *
 */
public class JSON{
  /**
   * 将指定对象转换为json字符串
   *
   * @param o 对象
   * @return json字符串
   */
  public static String fromObject(Object o){
    return ConvertFactory.getWrapper(Strings.JSON).wrap(o);
  }

  /**
   * 将字符串转换为Bean或者List<Bean>
   *
   * @param s 字符串
   * @return Bean或者List<Bean>
   */
  public static <T>T toBean(String s){
    return ConvertFactory.getParser(Strings.JSON).parse(s);
  }
}
