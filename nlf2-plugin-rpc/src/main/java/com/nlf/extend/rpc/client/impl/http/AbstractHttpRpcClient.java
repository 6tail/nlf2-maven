package com.nlf.extend.rpc.client.impl.http;

import com.nlf.extend.rpc.client.AbstractRpcClient;

/**
 * 抽象Http RPC客户端
 * @author 6tail
 */
public abstract class AbstractHttpRpcClient extends AbstractRpcClient implements IHttpRpcClient{
  public boolean support(String type) {
    return "HTTP".equalsIgnoreCase(type);
  }
}
