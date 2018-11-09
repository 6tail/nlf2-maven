package com.nlf.extend.rpc.server.impl.http.impl;

import com.nlf.core.Statics;
import com.nlf.extend.rpc.server.impl.http.AbstractHttpRpcResponse;
import com.nlf.extend.web.view.StreamView;
import com.nlf.util.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 默认HTTP RPC响应
 * 
 * @author 6tail
 *
 */
public class DefaultHttpRpcResponse extends AbstractHttpRpcResponse {
  public void send(Object o) throws IOException{
    if(null==o) return;
    if(o instanceof StreamView){
      sendStream((StreamView)o);
    }else{
      super.send(o);
    }
  }

  public void sendString(String s) throws IOException{
    sendString(s,"text/plain");
  }

  public void sendString(String s,String contentType) throws IOException{
    exchange.getResponseHeaders().add("Content-Type",contentType+";charset="+Statics.ENCODE);
    byte[] bytes = s.getBytes(Statics.ENCODE);
    int len = bytes.length;
    exchange.getResponseHeaders().add("Content-Length",len+"");
    exchange.sendResponseHeaders(200,len);
    OutputStream os = null;
    try{
      os = exchange.getResponseBody();
      os.write(bytes);
      os.flush();
    }finally{
      IOUtil.closeQuietly(os);
    }
  }

  public void sendStream(StreamView streamView) throws IOException{
    InputStream inputStream = streamView.getInputStream();
    if(null!=streamView.getName()){
      exchange.getResponseHeaders().add("Content-Disposition","attachment;filename="+ URLEncoder.encode(streamView.getName(),"utf-8"));
    }
    String contentType = streamView.getContentType();
    if(null==contentType||contentType.length()<1){
      contentType = "application/octet-stream";
    }
    if(streamView.getSize()>-1){
      exchange.getResponseHeaders().add("Content-Length",streamView.getSize()+"");
    }
    sendStream(inputStream,contentType);
  }

  protected  void sendStream(InputStream inputStream,String contentType) throws IOException{
    exchange.getResponseHeaders().add("Content-Type",contentType);
    OutputStream os = null;
    try{
      os = exchange.getResponseBody();
      int n = 0;
      byte b[] = new byte[IOUtil.BUFFER_SIZE];
      while((n = inputStream.read(b))!=-1){
        os.write(b,0,n);
      }
      os.flush();
    }finally{
      IOUtil.closeQuietly(os);
      IOUtil.closeQuietly(inputStream);
    }
  }

  public void sendStream(InputStream inputStream) throws IOException{
    sendStream(inputStream,"application/octet-stream");
  }
}