package com.nlf.extend.log.commons.logging;

import com.nlf.log.ILog;
import com.nlf.log.ILogAdapter;

/**
 * commons-logging日志适配器
 * 
 * @author 6tail
 * 
 */
public class LoggingAdapter implements ILogAdapter{
  private static boolean supported;
  static{
    try{
      Class.forName("org.apache.commons.logging.LogFactory");
      supported = true;
    }catch(ClassNotFoundException e){}
  }

  public ILog getLog(String name){
    return new LoggingLog(name);
  }

  public boolean isSupported(){
    return supported;
  }
}