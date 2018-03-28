package com.nlf.log;

import java.util.List;
import com.nlf.App;

/**
 * 日志工厂
 * 
 * @author 6tail
 * 
 */
public class Logger{
  public static final String KLASS = Logger.class.getName();
  /** 日志适配器 */
  private static ILogAdapter adapter;
  static{
    List<String> l = App.getImplements(ILogAdapter.class);
    for(String c:l){
      try{
        ILogAdapter a = App.getProxy().newInstance(c);
        if(a.isSupported()){
          adapter = a;
          break;
        }
      }catch(Exception e){}
    }
  }

  private Logger(){}

  private static StackTraceElement findStackTrace(){
    int index = 0;
    StackTraceElement[] sts = Thread.currentThread().getStackTrace();
    for(StackTraceElement st:sts){
      if(KLASS.equals(st.getClassName())){
        index += 2;
        break;
      }else{
        index++;
      }
    }
    return sts[index];
  }

  /**
   * 获取日志接口，自动使用调用类名作为日志名称
   * 
   * @return 日志接口
   */
  public static ILog getLog(){
    return adapter.getLog(findStackTrace().getClassName());
  }

  /**
   * 获取日志接口
   * 
   * @param name 自定义名称
   * @return 日志接口
   */
  public static ILog getLog(String name){
    return adapter.getLog(name);
  }

  /**
   * 获取日志接口，使用类名作为日志名称
   * 
   * @param klass 类
   * @return 日志接口
   */
  public static ILog getLog(Class<?> klass){
    return adapter.getLog(klass.getName());
  }
}