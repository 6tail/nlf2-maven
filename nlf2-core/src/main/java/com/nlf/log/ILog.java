package com.nlf.log;

/**
 * 日志接口
 *
 * @author 6tail
 *
 */
public interface ILog{
  /**
   * debug
   *
   * @param msg 内容
   */
  void debug(String msg);

  /**
   * debug
   *
   * @param msg 内容
   * @param e 异常
   */
  void debug(String msg,Throwable e);

  /**
   * info
   *
   * @param msg 内容
   */
  void info(String msg);

  /**
   * debug
   *
   * @param msg 内容
   * @param e 异常
   */
  void info(String msg,Throwable e);

  /**
   * warn
   *
   * @param msg 内容
   */
  void warn(String msg);

  /**
   * warn
   *
   * @param msg 内容
   * @param e 异常
   */
  void warn(String msg,Throwable e);

  /**
   * error
   *
   * @param msg 内容
   */
  void error(String msg);

  /**
   * error
   *
   * @param msg 内容
   * @param e 异常
   */
  void error(String msg,Throwable e);

  /**
   * trace
   *
   * @param msg 内容
   */
  void trace(String msg);

  /**
   * trace
   *
   * @param msg 内容
   * @param e 异常
   */
  void trace(String msg,Throwable e);

  /**
   * 是否启用debug
   * @return true:启用 false:未启用
   */
  boolean isDebugEnabled();

  /**
   * 是否启用info
   * @return true:启用 false:未启用
   */
  boolean isInfoEnabled();

  /**
   * 是否启用warn
   * @return true:启用 false:未启用
   */
  boolean isWarnEnabled();

  /**
   * 是否启用error
   * @return true:启用 false:未启用
   */
  boolean isErrorEnabled();

  /**
   * 是否启用trace
   * @return true:启用 false:未启用
   */
  boolean isTraceEnabled();
}