package com.nlf.extend.rpc.server.impl.http.impl;

import com.nlf.App;
import com.nlf.core.*;
import com.nlf.extend.rpc.server.impl.http.AbstractHttpRpcRequest;
import com.nlf.extend.rpc.server.impl.http.IHttpRpcFileUploader;
import com.nlf.log.Logger;
import com.nlf.util.StringUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * 默认HTTP RPC请求
 * 
 * @author 6tail
 *
 */
public class DefaultHttpRpcRequest extends AbstractHttpRpcRequest {
  /** 代理标识 */
  public static final String[] PROXY_HEADER = {
      "X-REAL-IP",
      "X-FORWARDED-FOR",
      "PROXY-CLIENT-IP",
      "WL-PROXY-CLIENT-IP",
      "HTTP_CLIENT_IP",
      "HTTP_X_FORWARDED_FOR",
      "HTTP_X_FORWARDED",
      "HTTP_FORWARDED_FOR",
      "HTTP_FORWARDED"
  };

  protected String getIP(){
    String r = exchange.getRemoteAddress().getHostName();
    out:for(String k:exchange.getRequestHeaders().keySet()) {
      for (String s : PROXY_HEADER) {
        if (s.equalsIgnoreCase(k)) {
          String p = exchange.getRequestHeaders().getFirst(k);
          if (null != p && p.length() > 0 && !"unknown".equalsIgnoreCase(p)) {
            r = p;
            break out;
          }
        }
      }
    }
    if(null!=r){
      if(r.contains(",")){
        String[] rs = r.split(",");
        for(String s:rs){
          if(s.length()>0&&!"unknown".equalsIgnoreCase(s)){
            r = s;
            break;
          }
        }
      }
      if("0:0:0:0:0:0:0:1".equals(r)){
        r = "127.0.0.1";
      }
    }
    if(null==r) r = "";
    return r;
  }

  public void init(){
    try{
      initParam();
    }catch(IOException e){
      throw new RuntimeException(e);
    }
    String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
    if(null!=contentType&&contentType.contains("multipart/form-data")){
      IFileUploader uploader = App.getProxy().newInstance(IHttpRpcFileUploader.class.getName());
      List<UploadFile> files = uploader.getFiles();
      param.set(Statics.PARAM_FILES,files);
    }
  }

  protected Map<String,Object> parseQuery(String s){
    Map<String,Object> params = new HashMap<String, Object>();
    if(null!=s){
      List<String> l = StringUtil.list(s,"&");
      for(String kv:l){
        if(!kv.contains("=")){
          continue;
        }
        String key = StringUtil.left(kv,"=");
        String value = StringUtil.right(kv,"=");
        if(params.containsKey(key)){
          Object v = params.get(key);
          if(v instanceof String){
            List<String> vs = new ArrayList<String>();
            vs.add((String)v);
            vs.add(value);
            params.put(key,vs);
          }else{
            List<String> vs = (List<String>)v;
            vs.add(value);
            params.put(key,vs);
          }
        }else{
          params.put(key,value);
        }
      }
    }
    return params;
  }

  protected Map<String,Object> parseParams() throws IOException {
    Map<String,Object> params = parseQuery(exchange.getRequestURI().getRawQuery());
    String reqMethod = exchange.getRequestMethod();
    if("POST".equalsIgnoreCase(reqMethod)){
      BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody()));
      Map<String,Object> postParams = parseQuery(reader.readLine());
      for(Map.Entry<String,Object> entry:postParams.entrySet()){
        params.put(entry.getKey(),entry.getValue());
      }
    }
    return params;
  }

  protected void initParam() throws IOException{
    Map<String,Object> params = parseParams();
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
      try {
        value = URLDecoder.decode(value, Statics.ENCODE);
        for (int i = 0, j = values.length; i < j; i++) {
          values[i] = URLDecoder.decode(values[i], Statics.ENCODE);
        }
      }catch (UnsupportedEncodingException e){
        Logger.getLog().warn(App.getProperty("nlf.web.request.parameter.decode_failed",key));
      }
      param.set(key,value);
      if(values.length>1){
        param.set(key,values);
      }
    }
  }

  public Client getClient(){
    if(null==client){
      client = new Client();
      client.setIp(getIP());
      String al = exchange.getRequestHeaders().getFirst("Accept-Language");
      if(null==al){
        client.setLocale(Locale.getDefault());
      }else{
        String language = StringUtil.left(al,"-");
        String country = StringUtil.right(al,"-");
        if(2!=country.length()){
          client.setLocale(new Locale(language));
        }else{
          client.setLocale(new Locale(language,country));
        }
      }
    }
    return client;
  }

  public ISession getSession(){
    return null;
  }

  public List<UploadFile> getFiles(){
    List<UploadFile> files = param.get(Statics.PARAM_FILES);
    return null==files?new ArrayList<UploadFile>(0):files;
  }
}