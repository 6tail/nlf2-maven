package com.nlf.core;

/**
 * 调度器接口
 *
 * @author 6tail
 */
public interface IDispatcher{
  /**
   * 初始化
   */
  void init();

  /**
   * 执行
   * @param request 请求
   * @param response 响应
   * @param filterChain 过滤链
   * @throws java.io.IOException IO异常
   */
  void service(IRequest request,IResponse response,IFilterChain filterChain) throws java.io.IOException;
}