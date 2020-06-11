package com.nlf.extend.rpc.server.impl.socket.impl;

import com.nlf.App;
import com.nlf.core.IDispatcher;
import com.nlf.core.Statics;
import com.nlf.extend.rpc.server.impl.http.IHttpRpcDispatcher;
import com.nlf.extend.rpc.server.impl.socket.*;

import java.io.IOException;
import java.net.Socket;

/**
 * 默认Socket RPC过滤器
 *
 * @author 6tail
 */
public class DefaultSocketRpcFilter implements ISocketRpcFilter {

  public String description() {
    return "Default Socket Rpc Filter";
  }

  public void destroy() {}

  public void doFilter(Socket socket,ISocketRpcFilterChain filterChain) throws IOException {
    ISocketRpcRequest request = App.getProxy().newInstance(ISocketRpcRequest.class.getName());
    ISocketRpcResponse response = App.getProxy().newInstance(ISocketRpcResponse.class.getName());
    request.setSocket(socket);
    response.setSocket(socket);
    App.set(Statics.REQUEST,request);
    App.set(Statics.RESPONSE,response);
    try {
      request.init();
      IDispatcher dispatcher = App.getProxy().newInstance(IHttpRpcDispatcher.class.getName());
      dispatcher.service(request, response, filterChain);
    }catch(Throwable e){
      response.send(e);
    }
  }

  public void init() {
    IDispatcher dispatcher = App.getProxy().newInstance(ISocketRpcDispatcher.class.getName());
    dispatcher.init();
    start();
  }

  /**
   * 启动完成后调用
   */
  protected void start(){}
}
