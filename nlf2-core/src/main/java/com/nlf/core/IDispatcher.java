package com.nlf.core;

/**
 * 调度器接口
 * @author 6tail
 *
 */
public interface IDispatcher{
  void init();
  void service(IRequest request,IResponse response,IFilterChain filterChain) throws java.io.IOException;
}