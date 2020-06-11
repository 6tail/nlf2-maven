package com.nlf.extend.rpc.client;

/**
 * 抽象RPC客户端
 *
 * @author 6tail
 */
public abstract class AbstractRpcClient implements IRpcClient{

  public IRpcResponse call(String host, int port, String path){
    return call(host, port, path, null);
  }

}
