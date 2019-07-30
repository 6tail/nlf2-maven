package com.nlf.extend.rpc.server.impl.http;

import com.nlf.App;
import com.nlf.extend.rpc.server.AbstractRpcServer;
import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * 基于HTTP的RPC服务端
 *
 * @author 6tail
 */
public class HttpRpcServer extends AbstractRpcServer {
  public void bind(int port) throws IOException {
    HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
    server.setExecutor(getExecutor());
    HttpContext context = server.createContext("/", new HttpRpcHandler());
    IHttpRpcFilter filter = App.getProxy().newInstance(App.getImplement(IHttpRpcFilter.class));
    context.getFilters().add((Filter) filter);
    server.start();
    filter.init();
  }

  public boolean support(String type) {
    return "HTTP".equalsIgnoreCase(type);
  }
}
