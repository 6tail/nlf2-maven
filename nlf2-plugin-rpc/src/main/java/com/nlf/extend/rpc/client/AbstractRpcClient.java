package com.nlf.extend.rpc.client;

import java.util.Map;

/**
 * 抽象RPC客户端
 *
 * @author 6tail
 */
public abstract class AbstractRpcClient implements IRpcClient{

  public IRpcResponse call(String host, int port, String path){
    return call(host, port, path, "");
  }

  public IRpcResponse call(String host, int port, String path, String body){
    return call(host, port, path, null, body);
  }

  public IRpcResponse call(String host, int port, String path, Map<String, String> args) {
    return call(host, port, path, args, null);
  }
}
