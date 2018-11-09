package com.nlf.extend.rpc.server;

import java.io.IOException;

public interface IRpcServer {
  void bind(int port) throws IOException;
  boolean support(String type);
}