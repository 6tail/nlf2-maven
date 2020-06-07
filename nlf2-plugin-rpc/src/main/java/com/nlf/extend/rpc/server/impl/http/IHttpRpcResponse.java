package com.nlf.extend.rpc.server.impl.http;

import com.nlf.core.IResponse;

import java.io.IOException;

/**
 * HTTP RPC响应接口
 * @author 6tail
 */
public interface IHttpRpcResponse extends IResponse,IHttpRpcExchange{

  /**
   * 发送资源
   * @param path 资源路径
   * @param dynamic 是否动态资源
   * @throws IOException IO异常
   */
  void sendResource(String path,boolean dynamic) throws IOException;
}
