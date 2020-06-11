package com.nlf.extend.rpc.server.impl.socket;

import com.nlf.core.IRequest;
import com.nlf.extend.rpc.socket.ISocketRpcExchange;

import java.net.Socket;

/**
 * Socket RPC请求接口
 * @author 6tail
 */
public interface ISocketRpcRequest extends IRequest,ISocketRpcExchange {
  void init();
  void setSocket(Socket socket);
}
