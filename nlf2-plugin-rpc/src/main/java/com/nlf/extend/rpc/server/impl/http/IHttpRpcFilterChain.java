package com.nlf.extend.rpc.server.impl.http;

import com.nlf.core.IFilterChain;
import com.sun.net.httpserver.Filter;

public interface IHttpRpcFilterChain extends IFilterChain{
  void setFilterChain(Filter.Chain filterChain);
}