package com.nlf.log;

/**
 * 日志接口
 *
 * @author 6tail
 *
 */
public interface ILog{
  void debug(String msg);

  void debug(String msg,Throwable e);

  void info(String msg);

  void info(String msg,Throwable e);

  void warn(String msg);

  void warn(String msg,Throwable e);

  void error(String msg);

  void error(String msg,Throwable e);

  void trace(String msg);

  void trace(String msg,Throwable e);

  boolean isDebugEnabled();

  boolean isInfoEnabled();

  boolean isWarnEnabled();

  boolean isErrorEnabled();

  boolean isTraceEnabled();
}