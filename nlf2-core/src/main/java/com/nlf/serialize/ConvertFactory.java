package com.nlf.serialize;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.nlf.App;
import com.nlf.exception.NlfException;

/**
 * 序列化/反序列化工厂
 * 
 * @author 6tail
 *
 */
public class ConvertFactory{
  protected ConvertFactory(){}

  /** 解析器映射 */
  public static final Map<String,String> PARSERS = new HashMap<String,String>();
  /** 包装器映射 */
  public static final Map<String,String> WRAPPERS = new HashMap<String,String>();

  /**
   * 获取解析器
   * 
   * @param format 格式，如json、xml等
   * @return 解析器
   */
  public static IParser getParser(String format){
    if(!PARSERS.containsKey(format)){
      List<String> impls = App.getImplements(IParser.class);
      for(String klass:impls){
        IParser parser = App.getProxy().newInstance(klass);
        if(parser.support(format)){
          PARSERS.put(format,klass);
          return parser;
        }
      }
      PARSERS.put(format,null);
    }else{
      String impl = PARSERS.get(format);
      if(null!=impl){
        return App.getProxy().newInstance(impl);
      }
    }
    throw new NlfException(App.getProperty("nlf.serialize.parser.not_found",format));
  }

  /**
   * 获取包装器
   * 
   * @param format 格式，如json、xml等
   * @return 包装器
   */
  public static IWrapper getWrapper(String format){
    if(!WRAPPERS.containsKey(format)){
      List<String> impls = App.getImplements(IWrapper.class);
      for(String klass:impls){
        IWrapper wrapper = App.getProxy().newInstance(klass);
        if(wrapper.support(format)){
          WRAPPERS.put(format,klass);
          return wrapper;
        }
      }
      WRAPPERS.put(format,null);
    }else{
      String impl = WRAPPERS.get(format);
      if(null!=impl){
        return App.getProxy().newInstance(impl);
      }
    }
    throw new NlfException(App.getProperty("nlf.serialize.wrapper.not_found",format));
  }
}