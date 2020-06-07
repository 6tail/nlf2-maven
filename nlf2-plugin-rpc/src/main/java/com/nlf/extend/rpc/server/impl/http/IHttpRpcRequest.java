package com.nlf.extend.rpc.server.impl.http;

import com.nlf.core.IHttpRequest;

/**
 * HTTP RPC请求接口
 * @author 6tail
 */
public interface IHttpRpcRequest extends IHttpRequest,IHttpRpcExchange {
  void init();
}
