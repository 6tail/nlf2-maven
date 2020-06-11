package com.nlf.extend.rpc.client.impl.socket;

import com.nlf.extend.rpc.client.AbstractRpcClient;

import java.net.Socket;

/**
 * 抽象Socket RPC客户端
 * @author 6tail
 */
public abstract class AbstractSocketRpcClient extends AbstractRpcClient implements ISocketRpcClient{

  protected Socket socket;

  public Socket getSocket() {
    return socket;
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  public boolean support(String type) {
    return "SOCKET".equalsIgnoreCase(type);
  }
}
