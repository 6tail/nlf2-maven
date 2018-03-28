package com.nlf.core;

/**
 * 代理接口
 * @author 6tail
 *
 */
public interface IProxy{
  
  /**
   * 实例化指定类或接口
   * @param interfaceOrClassName 完整接口或类名
   * @return 实例
   */
  <T>T newInstance(String interfaceOrClassName);
  
  /**
   * 调用指定类或接口的方法
   * @param interfaceOrClassName 完整接口或类名
   * @param method 方法名
   * @param args 参数列表
   * @return 结果
   */
  <T>T execute(String interfaceOrClassName,String method,Object... args);
}