package com.nlf.core;

/**
 * 过滤链接口
 *
 * @author 6tail
 */
public interface IFilterChain{

  /**
   * 执行过滤
   * @param request 请求
   * @param response 响应
   * @throws java.io.IOException IO异常
   */
  void doFilter(IRequest request,IResponse response) throws java.io.IOException;
}