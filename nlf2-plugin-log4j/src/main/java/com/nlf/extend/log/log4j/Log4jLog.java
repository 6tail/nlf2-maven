package com.nlf.extend.log.log4j;

import org.apache.log4j.Logger;
import com.nlf.App;
import com.nlf.log.AbstractLog;

/**
 * log4j日志
 *
 * @author 6tail
 *
 */
public class Log4jLog extends AbstractLog{
  public static final String KLASS = Log4jLog.class.getName();
  private Logger logger;
  public Log4jLog(String name){
    super(name);
    logger = Logger.getLogger(name);
  }
  
  private String wrap(String msg){
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
    StackTraceElement st = sts[index];
    String prefix = App.getProperty("nlf.log.log4j.message.prefix",st);
    return prefix+msg;
  }

  public void debug(String msg){
    logger.debug(wrap(msg));
  }

  public void debug(String msg,Throwable e){
    logger.debug(wrap(msg),e);
  }

  public void info(String msg){
    logger.info(wrap(msg));
  }

  public void info(String msg,Throwable e){
    logger.info(wrap(msg),e);
  }

  public void warn(String msg){
    logger.warn(wrap(msg));
  }

  public void warn(String msg,Throwable e){
    logger.warn(wrap(msg),e);
  }

  public void error(String msg){
    logger.error(wrap(msg));
  }

  public void error(String msg,Throwable e){
    logger.error(wrap(msg),e);
  }

  public void trace(String msg){
    logger.trace(wrap(msg));
  }

  public void trace(String msg,Throwable e){
    logger.trace(wrap(msg),e);
  }

  public boolean isDebugEnabled(){
    return logger.isDebugEnabled();
  }

  public boolean isInfoEnabled(){
    return logger.isInfoEnabled();
  }

  public boolean isWarnEnabled(){
    return true;
  }

  public boolean isErrorEnabled(){
    return true;
  }

  public boolean isTraceEnabled(){
    return logger.isTraceEnabled();
  }
}