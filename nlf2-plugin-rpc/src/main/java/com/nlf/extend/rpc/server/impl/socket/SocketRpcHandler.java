package com.nlf.extend.rpc.server.impl.socket;

import java.net.Socket;

/**
 * @author 6tail
 */
public class SocketRpcHandler implements Runnable {
  private Socket socket;

  public SocketRpcHandler(Socket socket) {
    this.socket = socket;
  }

  public void run() {

  }
}
