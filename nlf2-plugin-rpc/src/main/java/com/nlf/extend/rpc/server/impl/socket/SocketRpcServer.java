package com.nlf.extend.rpc.server.impl.socket;

import com.nlf.App;
import com.nlf.extend.rpc.server.AbstractRpcServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * 基于Socket的RPC服务端
 *
 * @author 6tail
 */
public class SocketRpcServer extends AbstractRpcServer implements Runnable {

  private ExecutorService executor;
  private ServerSocket serverSocket;

  public void bind(int port) throws IOException {
    serverSocket = new ServerSocket(port);
    serverSocket.setSoTimeout(0);
    executor = getExecutor();
    executor.execute(this);
  }

  public void run() {
    ISocketRpcFilter filter = App.getProxy().newInstance(ISocketRpcFilter.class.getName());
    filter.init();
    while (Thread.currentThread().isAlive()) {
      Socket socket;
      try {
        socket = serverSocket.accept();
        executor.execute(new SocketRpcHandler(socket));
      } catch (IOException ignore) {
      }
    }
  }

  public boolean support(String type) {
    return "SOCKET".equalsIgnoreCase(type);
  }
}
