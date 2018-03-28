package com.nlf.log.impl;

import static java.lang.System.err;
import static java.lang.System.out;
import com.nlf.App;
import com.nlf.log.AbstractLog;
import com.nlf.util.DateUtil;

/**
 * 默认日志
 *
 * @author 6tail
 *
 */
public class DefaultLog extends AbstractLog{
  public static final String KLASS = DefaultLog.class.getName();
  public DefaultLog(String name){
    super(name);
  }

  private StackTraceElement findStackTrace(){
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

  public void debug(String msg){
    out.println(App.getProperty("nlf.log.default.debug",DateUtil.ymdhms(DateUtil.now()),name,findStackTrace(),msg));
  }

  public void debug(String msg,Throwable e){
    out.println(App.getProperty("nlf.log.default.debug",DateUtil.ymdhms(DateUtil.now()),name,findStackTrace(),msg));
    e.printStackTrace();
  }

  public void info(String msg){
    out.println(App.getProperty("nlf.log.default.info",DateUtil.ymdhms(DateUtil.now()),name,findStackTrace(),msg));
  }

  public void info(String msg,Throwable e){
    out.println(App.getProperty("nlf.log.default.info",DateUtil.ymdhms(DateUtil.now()),name,findStackTrace(),msg));
    e.printStackTrace();
  }

  public void warn(String msg){
    out.println(App.getProperty("nlf.log.default.warn",DateUtil.ymdhms(DateUtil.now()),name,findStackTrace(),msg));
  }

  public void warn(String msg,Throwable e){
    out.println(App.getProperty("nlf.log.default.warn",DateUtil.ymdhms(DateUtil.now()),name,findStackTrace(),msg));
    e.printStackTrace();
  }

  public void error(String msg){
    err.println(App.getProperty("nlf.log.default.error",DateUtil.ymdhms(DateUtil.now()),name,findStackTrace(),msg));
  }

  public void error(String msg,Throwable e){
    err.println(App.getProperty("nlf.log.default.error",DateUtil.ymdhms(DateUtil.now()),name,findStackTrace(),msg));
    e.printStackTrace();
  }

  public void trace(String msg){
    out.println(App.getProperty("nlf.log.default.trace",DateUtil.ymdhms(DateUtil.now()),name,findStackTrace(),msg));
  }

  public void trace(String msg,Throwable e){
    out.println(App.getProperty("nlf.log.default.trace",DateUtil.ymdhms(DateUtil.now()),name,findStackTrace(),msg));
    e.printStackTrace();
  }

  public boolean isDebugEnabled(){
    return true;
  }

  public boolean isInfoEnabled(){
    return true;
  }

  public boolean isWarnEnabled(){
    return true;
  }

  public boolean isErrorEnabled(){
    return true;
  }

  public boolean isTraceEnabled(){
    return true;
  }
}