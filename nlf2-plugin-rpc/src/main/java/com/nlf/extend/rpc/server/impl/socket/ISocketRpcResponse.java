package com.nlf.extend.rpc.server.impl.socket;

import com.nlf.core.IResponse;
import com.nlf.extend.rpc.socket.ISocketRpcExchange;

import java.net.Socket;

/**
 * Socket RPC响应接口
 * @author 6tail
 */
public interface ISocketRpcResponse extends IResponse,ISocketRpcExchange{
  void setSocket(Socket socket);
}
