package com.nlf.core;

/**
 * 会话接口
 * 
 * @author 6tail
 *
 */
public interface ISession{
  /**
   * 获取会话中存储的对象
   * @param name 对象名称
   * @return 值
   */
  <T>T getAttribute(String name);

  /**
   * 获取会话中存储的所有对象名称
   * @return 对象名称枚举
   */
  java.util.Enumeration<String> getAttributeNames();

  /**
   * 获取会话创建时间
   * @return 会话创建时间
   */
  long getCreationTime();

  /**
   * 获取会话ID
   * @return 会话ID
   */
  String getId();

  /**
   * 获取会话最近访问时间
   * @return 最近访问时间
   */
  long getLastAccessedTime();

  /**
   * 获取会话超时时间（单位：秒）
   * @return 超时时间（单位：秒）
   */
  int getMaxInactiveInterval();

  /**
   * 终止会话，释放会话中的所有对象
   */
  void invalidate();

  /**
   * 是否新建的会话，即客户端还没有请求第二次
   * @return true/false
   */
  boolean isNew();

  /**
   * 移除指定对象
   * @param name 对象名称
   */
  void removeAttribute(String name);

  /**
   * 设置对象
   * @param name 对象名称
   * @param value 值
   */
  void setAttribute(String name,Object value);

  /**
   * 设置会话超时时间（单位：秒）
   * @param interval 超时时间（单位：秒），如果设置为0或负数，则永不超时
   */
  void setMaxInactiveInterval(int interval);
}