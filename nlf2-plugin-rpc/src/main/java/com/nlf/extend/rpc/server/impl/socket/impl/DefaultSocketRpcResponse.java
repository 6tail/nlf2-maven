package com.nlf.extend.rpc.server.impl.socket.impl;

import com.nlf.View;
import com.nlf.extend.rpc.server.impl.socket.AbstractSocketRpcResponse;
import com.nlf.extend.web.view.StreamView;
import com.nlf.util.ContentTypes;
import com.nlf.util.IOUtil;
import com.nlf.util.InputStreamCache;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * 默认Socket RPC响应
 *
 * @author 6tail
 */
public class DefaultSocketRpcResponse extends AbstractSocketRpcResponse {
  @Override
  public void send(Object o) throws IOException {
    if (null == o) {
      return;
    }
    if (o instanceof StreamView) {
      sendStream((StreamView) o);
    }else if(o instanceof RuntimeException){
      send(View.json(((RuntimeException) o).getMessage()).setSuccess(false));
    } else if(o instanceof Throwable) {
      send(View.json(((Throwable) o).getMessage()).setSuccess(false));
    } else if(o instanceof Number || o instanceof Boolean || o instanceof Character || o instanceof String) {
      send(View.json(o));
    } else if(o instanceof com.nlf.view.JsonView) {
      sendString(o + "");
    } else if(o instanceof Date) {
      send(View.json(com.nlf.util.DateUtil.ymdhms((Date) o)));
    }
  }

  public void sendString(String s) throws IOException {
    sendString(s, ContentTypes.JSON);
  }

  public void sendString(String s, String contentType) throws IOException {
    DataOutputStream os = new DataOutputStream(socket.getOutputStream());
    os.writeUTF(MAGIC);
    os.writeShort(TYPE_JSON);
    os.writeUTF(s);
    os.writeShort(TYPE_END);
  }

  public void sendStream(InputStream inputStream) throws IOException {
    InputStreamCache cache = new InputStreamCache(inputStream);
    InputStream in = cache.getInputStream();
    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
    out.writeUTF(MAGIC);
    out.writeShort(TYPE_FILE_NAME);
    out.writeUTF("unknown");
    out.writeShort(TYPE_FILE_SIZE);
    out.writeLong(cache.getSize());
    int n;
    byte[] buffer = new byte[IOUtil.BUFFER_SIZE];
    while(-1!=(n=in.read(buffer))){
      out.write(buffer,0,n);
    }
    out.writeShort(TYPE_END);
  }

  public void sendStream(InputStream in,String name,long size) throws IOException {
    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
    out.writeUTF(MAGIC);
    out.writeShort(TYPE_FILE_NAME);
    out.writeUTF(name);
    out.writeShort(TYPE_FILE_SIZE);
    out.writeLong(size);
    int n;
    byte[] buffer = new byte[IOUtil.BUFFER_SIZE];
    while(-1!=(n=in.read(buffer))){
      out.write(buffer,0,n);
    }
    out.writeShort(TYPE_END);
  }

  public void sendStream(StreamView streamView) throws IOException {
    sendStream(streamView.getInputStream(),streamView.getName(),streamView.getSize());
  }

}
