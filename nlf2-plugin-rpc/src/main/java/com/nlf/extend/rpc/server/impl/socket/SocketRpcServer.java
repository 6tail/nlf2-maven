package com.nlf.extend.rpc.server.impl.socket;

import com.nlf.extend.rpc.server.AbstractRpcServer;
import com.nlf.util.IOUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SocketRpcServer extends AbstractRpcServer implements Runnable{

  private ExecutorService pool;
  private ServerSocket serverSocket;

  public void bind(int port) throws IOException{
    serverSocket = new ServerSocket(port);
    serverSocket.setSoTimeout(0);
    pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()*100);
    new Thread(this).start();
  }

  public void run() {
    while(true){
      Socket socket = null;
      try {
        socket = serverSocket.accept();
      }catch (IOException e){
        IOUtil.closeQuietly(socket);
      }
    }
  }

  public boolean support(String type) {
    return "SOCKET".equalsIgnoreCase(type);
  }
}