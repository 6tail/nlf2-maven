package com.nlf.extend.rpc.client.impl.socket.impl;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.extend.rpc.client.IRpcResponse;
import com.nlf.extend.rpc.client.impl.DefaultRpcResponse;
import com.nlf.extend.rpc.client.impl.socket.AbstractSocketRpcClient;
import com.nlf.extend.rpc.socket.ISocketRpcFileUploader;
import com.nlf.serialize.json.JSON;
import com.nlf.util.IOUtil;

import java.io.*;
import java.net.Socket;
import java.util.Locale;
import java.util.Map;

/**
 * 默认Socket RPC客户端
 * @author 6tail
 */
public class DefaultSocketRpcClient extends AbstractSocketRpcClient {

  protected ISocketRpcFileUploader uploader = App.getProxy().newInstance(ISocketRpcFileUploader.class.getName());

  protected IRpcResponse parseResponse(DataInputStream in) throws IOException {
    String magic = in.readUTF();
    if(!MAGIC.equals(magic)){
      throw new RuntimeException("magic error");
    }
    DefaultRpcResponse response = new DefaultRpcResponse();
    String fileName = null;
    long fileSize = 0;
    short type = in.readShort();
    while(type!=TYPE_END) {
      switch (type) {
        case TYPE_JSON:
          Bean data = JSON.toBean(in.readUTF());
          if(data.getBoolean("success",false)){
            response.setData(data.get("data"));
            response.setSuccess(true);
          }else {
            response.setMessage(data.getString("data"));
            response.setSuccess(false);
          }
          break;
        case TYPE_FILE_NAME:
          fileName = in.readUTF();
          break;
        case TYPE_FILE_SIZE:
          fileSize = in.readLong();
          break;
        case TYPE_FILE_DATA:
          uploader.parseFile(fileName,fileSize,in);
          break;
        default:
      }
      type = in.readShort();
    }
    response.setFiles(uploader.getFiles());
    return response;
  }

  public IRpcResponse call(String host, int port, String path, Map<String, String> args, String body, File... file) {
    Socket socket = null;
    DataInputStream in = null;
    DataOutputStream out = null;
    try{
      socket = new Socket(host,port);
      socket.setSoTimeout(0);
      socket.setTcpNoDelay(true);
      in = new DataInputStream(socket.getInputStream());
      out = new DataOutputStream(socket.getOutputStream());
      out.writeUTF(MAGIC);
      out.writeShort(TYPE_LOCALE);
      Locale locale = Locale.getDefault();
      String language = locale.getLanguage();
      String country = locale.getCountry();
      out.writeUTF(language+"-"+country);

      out.writeShort(TYPE_PATH);
      out.writeUTF(path);

      if(null!=args){
        for(Map.Entry<String,String> entry:args.entrySet()){
          out.writeShort(TYPE_PARAM_NAME);
          out.writeUTF(entry.getKey());
          out.writeShort(TYPE_PARAM_VALUE);
          out.writeUTF(entry.getValue());
        }
      }

      if(null!=body&&body.length()>0){
        out.writeShort(TYPE_BODY);
        out.writeUTF(body);
      }

      byte[] buffer = null;
      if(file.length>0){
        buffer = new byte[IOUtil.BUFFER_SIZE];
      }
      for(File f:file){
        out.writeShort(TYPE_FILE_NAME);
        out.writeUTF(f.getName());
        out.writeShort(TYPE_FILE_SIZE);
        out.writeLong(f.length());
        out.writeShort(TYPE_FILE_DATA);

        InputStream is = new FileInputStream(f);
        int n;
        while(-1!=(n=is.read(buffer))){
          out.write(buffer,0,n);
        }
      }
      out.writeShort(TYPE_END);
      out.flush();
      return parseResponse(in);
    }catch(Exception e){
      DefaultRpcResponse response = new DefaultRpcResponse();
      response.setSuccess(false);
      response.setMessage(e.getMessage());
      return response;
    }finally{
      IOUtil.closeQuietly(in);
      IOUtil.closeQuietly(out);
      IOUtil.closeQuietly(socket);
    }
  }
}
