package com.nlf.extend.rpc.server.impl.socket;

import com.nlf.core.AbstractRequest;
import com.nlf.util.InputStreamCache;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 抽象Socket RPC请求
 *
 * @author 6tail
 *
 */
public abstract class AbstractSocketRpcRequest extends AbstractRequest implements ISocketRpcRequest{
  protected InputStreamCache inputStreamCache;
  protected Socket socket;
  protected String path;
  protected String locale;

  public Socket getSocket() {
    return socket;
  }

  public void setSocket(Socket socket) {
    this.socket = socket;
  }

  public String getPath(){
    return path;
  }

  public InputStream getInputStream() throws IOException {
    return socket.getInputStream();
  }
}
