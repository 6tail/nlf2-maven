package com.nlf.extend.rpc.server.impl.socket;

import com.nlf.core.AbstractResponse;

import java.net.Socket;

/**
 * 抽象Socket RPC响应
 *
 * @author 6tail
 *
 */
public abstract class AbstractSocketRpcResponse extends AbstractResponse implements ISocketRpcResponse{
  protected Socket socket;

  public Socket getSocket() {
    return socket;
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }
}
