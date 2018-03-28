package com.nlf.extend.serialize.obj;

import com.nlf.serialize.ConvertFactory;

public class OBJ{
  /**
   * 将指定对象转换为字符串
   * 
   * @param o 对象
   * @return 字符串
   */
  public static String fromObject(Object o){
    return ConvertFactory.getWrapper("obj").wrap(o);
  }

  /**
   * 将字符串转换为Bean或者List<Bean>
   * 
   * @param s 字符串
   * @return Bean或者List<Bean>
   */
  public static <T>T toBean(String s){
    return ConvertFactory.getParser("obj").parse(s);
  }

}