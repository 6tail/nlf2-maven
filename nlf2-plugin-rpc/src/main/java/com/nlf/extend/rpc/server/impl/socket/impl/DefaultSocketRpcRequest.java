package com.nlf.extend.rpc.server.impl.socket.impl;

import com.nlf.App;
import com.nlf.core.Client;
import com.nlf.core.ISession;
import com.nlf.core.Statics;
import com.nlf.core.UploadFile;
import com.nlf.extend.rpc.server.impl.socket.AbstractSocketRpcRequest;
import com.nlf.extend.rpc.server.impl.socket.SocketRpcServer;
import com.nlf.extend.rpc.socket.ISocketRpcFileUploader;
import com.nlf.util.StringUtil;
import com.nlf.util.Strings;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.*;

/**
 * 默认Socket RPC请求
 *
 * @author 6tail
 *
 */
public class DefaultSocketRpcRequest extends AbstractSocketRpcRequest {

  protected ISocketRpcFileUploader uploader = App.getProxy().newInstance(ISocketRpcFileUploader.class.getName());

  protected String getIp(){
    String r = socket.getInetAddress().getHostAddress();
    if(null!=r){
      if(LOCAL_IP_V6.equals(r)){
        r = LOCAL_IP_V4;
      }
    }
    return null==r?"":r;
  }

  protected String buildPath(String path){
    if(path.startsWith(SocketRpcServer.contextPath+Strings.SLASH_LEFT)){
      path = path.substring(SocketRpcServer.contextPath.length());
    }
    if(!path.startsWith(Strings.SLASH_LEFT)){
      path = Strings.SLASH_LEFT+path;
    }
    return path;
  }

  @SuppressWarnings("unchecked")
  public void init(){
    try{
      DataInputStream in = new DataInputStream(getInputStream());
      String magic = in.readUTF();
      if(!MAGIC.equals(magic)){
        throw new RuntimeException("magic error");
      }
      Map<String,Object> params = new HashMap<String, Object>(16);
      String name = null;
      String fileName = null;
      long fileSize = 0;
      short type = in.readShort();
      while(type!=TYPE_END) {
        switch (type) {
          case TYPE_LOCALE:
            locale = in.readUTF();
            break;
          case TYPE_PATH:
            path = buildPath(in.readUTF());
            break;
          case TYPE_PARAM_NAME:
            name = in.readUTF();
            break;
          case TYPE_PARAM_VALUE:
            if(null!=name) {
              String value = in.readUTF();
              if(params.containsKey(name)){
                Object v = params.get(name);
                if(v instanceof String){
                  List<String> vs = new ArrayList<String>();
                  vs.add((String)v);
                  vs.add(value);
                  params.put(name,vs);
                }else{
                  List<String> vs = (List<String>)v;
                  vs.add(value);
                  params.put(name,vs);
                }
              }else{
                params.put(name,value);
              }
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
      for(String key:params.keySet()){
        Object v = params.get(key);
        String value = null;
        String[] values = null;
        if(v instanceof String){
          value = (String)v;
        }else{
          List<String> vs = (List<String>)v;
          values = new String[vs.size()];
          vs.toArray(values);
        }
        if(null==value){
          value = "";
        }
        if(null==values){
          values = new String[]{};
        }
        param.set(key,value);
        if(values.length>1){
          param.set(key,values);
        }
      }
    }catch(IOException e){
      throw new RuntimeException(e);
    }
    initPaging();
  }

  protected void initPaging(){
    int pageNumber = param.getInt(Statics.PARAM_PAGE_NUMBER,this.pageNumber);
    int pageSize = param.getInt(Statics.PARAM_PAGE_SIZE,this.pageSize);
    setPageNumber(pageNumber);
    setPageSize(pageSize);
  }

  @Override
  public Client getClient(){
    if(null==client){
      client = new Client();
      client.setIp(getIp());
      if(null==locale){
        client.setLocale(Locale.getDefault());
      }else{
        String language = StringUtil.left(locale,"-");
        String country = StringUtil.right(locale,"-");
        if(2!=country.length()){
          client.setLocale(new Locale(language));
        }else{
          client.setLocale(new Locale(language,country));
        }
      }
    }
    return client;
  }

  public ISession getSession(boolean autoCreate) {
    return null;
  }

  public List<UploadFile> getFiles(){
    return uploader.getFiles();
  }
}
