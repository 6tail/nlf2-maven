package com.nlf.extend.rpc.server.impl.socket.impl;

import com.nlf.core.IRequest;
import com.nlf.core.IResponse;
import com.nlf.extend.rpc.server.impl.socket.AbstractSocketRpcFilterChain;
import com.nlf.extend.rpc.socket.ISocketRpcExchange;

/**
 * 默认Socket RPC过滤链
 * @author 6tail
 */
public class DefaultSocketRpcFilterChain extends AbstractSocketRpcFilterChain {
  public void doFilter(IRequest request, IResponse response){
    throw new RuntimeException(ISocketRpcExchange.DEFAULT_MSG_404);
  }
}
