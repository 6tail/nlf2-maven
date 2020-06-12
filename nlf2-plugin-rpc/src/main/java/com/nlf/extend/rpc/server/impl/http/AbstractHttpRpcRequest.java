package com.nlf.extend.rpc.server.impl.http;

import com.nlf.core.Statics;
import com.nlf.util.IOUtil;
import com.nlf.util.InputStreamCache;
import com.nlf.util.Strings;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;

/**
 * 抽象HTTP RPC请求
 *
 * @author 6tail
 *
 */
public abstract class AbstractHttpRpcRequest extends com.nlf.core.AbstractRequest implements IHttpRpcRequest{
  protected HttpExchange exchange;
  /** 输入流缓存 */
  protected InputStreamCache inputStreamCache;

  public HttpExchange getHttpExchange() {
    return exchange;
  }

  public void setHttpExchange(HttpExchange exchange) {
    this.exchange = exchange;
  }

  public String getPath(){
    String path = exchange.getRequestURI().getPath();
    if(path.startsWith(HttpRpcServer.contextPath+Strings.SLASH_LEFT)){
      path = path.substring(HttpRpcServer.contextPath.length());
    }
    if(!path.startsWith(Strings.SLASH_LEFT)){
      path = Strings.SLASH_LEFT+path;
    }
    return path;
  }

  public InputStream getInputStream() throws IOException {
    if(null == inputStreamCache){
      inputStreamCache = new InputStreamCache(exchange.getRequestBody());
    }
    return inputStreamCache.getInputStream();
  }

  public String getBodyString(){
    String body = "";
    try {
      body = new String(IOUtil.toBytes(getInputStream()), Statics.ENCODE);
    }catch(IOException ignore){
    }
    return body;
  }
}
