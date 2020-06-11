package com.nlf.extend.rpc.server.impl.socket;

import com.nlf.App;
import com.nlf.View;
import com.nlf.extend.rpc.socket.ISocketRpcExchange;
import com.nlf.log.Logger;
import com.nlf.serialize.json.JSON;
import com.nlf.util.IOUtil;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Socket RPC处理
 * @author 6tail
 */
public class SocketRpcHandler implements Runnable {
  private Socket socket;

  public SocketRpcHandler(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    InputStream in = null;
    OutputStream out = null;
    try {
      in = socket.getInputStream();
      out = socket.getOutputStream();
      ISocketRpcFilterChain filterChain = App.getProxy().newInstance(ISocketRpcFilterChain.class.getName());
      ISocketRpcFilter filter = App.getProxy().newInstance(ISocketRpcFilter.class.getName());
      filter.doFilter(socket,filterChain);
    } catch (Exception e) {
      Logger.getLog().error(ISocketRpcExchange.DEFAULT_MSG_500,e);
      try {
        DataOutputStream os = new DataOutputStream(out);
        os.writeUTF(ISocketRpcExchange.MAGIC);
        os.writeShort(ISocketRpcExchange.TYPE_JSON);
        os.writeUTF(JSON.fromObject(View.json(ISocketRpcExchange.DEFAULT_MSG_500).setSuccess(false)));
        os.writeShort(ISocketRpcExchange.TYPE_END);
      }catch(Exception ignore){}
    }finally {
      IOUtil.closeQuietly(in);
      IOUtil.closeQuietly(out);
      IOUtil.closeQuietly(socket);
    }
  }
}
