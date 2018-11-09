package com.nlf.extend.rpc.server.impl.http;

import com.nlf.core.AbstractFilterChain;
import com.nlf.core.IRequest;
import com.nlf.core.IResponse;
import com.sun.net.httpserver.Filter;

import java.io.IOException;

/**
 * 抽象HTTP RPC过滤链
 * 
 * @author 6tail
 *
 */
public abstract class AbstractHttpRpcFilterChain extends AbstractFilterChain implements IHttpRpcFilterChain{
  /** 原生过滤链 */
  protected Filter.Chain filterChain;

  public void setFilterChain(Filter.Chain filterChain){
    this.filterChain = filterChain;
  }

  public void doFilter(IRequest request,IResponse response) throws IOException{
    IHttpRpcRequest httpRequest = (IHttpRpcRequest)request;
    filterChain.doFilter(httpRequest.getHttpExchange());
  }
}