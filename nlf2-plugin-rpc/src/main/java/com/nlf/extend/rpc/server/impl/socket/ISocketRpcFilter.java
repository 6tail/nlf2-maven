package com.nlf.extend.rpc.server.impl.socket;

import java.io.IOException;
import java.net.Socket;

/**
 * Socket RPC过滤器
 * @author 6tail
 */
public interface ISocketRpcFilter {

  /**
   * 获取描述
   * @return 过滤器描述
   */
  String description();

  /**
   * 销毁
   */
  void destroy();

  /**
   * 执行
   * @param socket Socket
   * @param filterChain 过滤链
   * @throws IOException IOException
   */
  void doFilter(Socket socket,ISocketRpcFilterChain filterChain) throws IOException;

  /**
   * 初始化
   */
  void init();
}
