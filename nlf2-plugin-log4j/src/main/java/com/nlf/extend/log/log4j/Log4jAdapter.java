package com.nlf.extend.log.log4j;

import com.nlf.log.ILog;
import com.nlf.log.ILogAdapter;

/**
 * log4j日志适配器
 * 
 * @author 6tail
 * 
 */
public class Log4jAdapter implements ILogAdapter{
  private static boolean supported;
  static{
    try{
      Class.forName("org.apache.log4j.Logger");
      supported = true;
    }catch(ClassNotFoundException e){}
  }

  public ILog getLog(String name){
    return new Log4jLog(name);
  }

  public boolean isSupported(){
    return supported;
  }
}