package com.nlf.extend.rpc.client.impl.http.impl;

import com.nlf.Bean;
import com.nlf.extend.rpc.client.IRpcResponse;
import com.nlf.extend.rpc.client.impl.DefaultRpcResponse;
import com.nlf.extend.rpc.client.impl.http.AbstractHttpRpcClient;
import com.nlf.serialize.json.JSON;
import com.nlf.util.IOUtil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * 默认Http RPC客户端
 *
 * @author 6tail
 */
public class DefaultHttpRpcClient extends AbstractHttpRpcClient{

  protected static String RESPONSE_SUCCESS = "success";
  protected static String RESPONSE_DATA = "data";
  protected static String RESPONSE_ERR_MSG = "data";
  protected static String ENCODE = "utf-8";

  public IRpcResponse call(String host, int port, String path, Map<String, String> args, String body, File... file){
    HttpURLConnection conn = null;
    DataOutputStream out = null;
    BufferedReader in = null;
    DefaultRpcResponse response = new DefaultRpcResponse();
    boolean existsBody = null!=body&&body.length()>0;
    boolean existsParam = null!=args&&!args.isEmpty();
    try {
      String paramStr = "";
      if(existsParam){
        StringBuilder params = new StringBuilder();
        for(Map.Entry<String, String> entry : args.entrySet()){
          params.append("&");
          params.append(entry.getKey());
          params.append("=");
          params.append(URLEncoder.encode(entry.getValue(),ENCODE));
        }
        paramStr = params.deleteCharAt(0).toString();
      }
      String url = String.format("http://%s:%s%s", host, port, path);
      if(existsBody&&existsParam){
        url += (url.contains("?")?"&":"?")+paramStr;
      }
      conn = (HttpURLConnection) new URL(url).openConnection();
      conn.setDoInput(true);
      conn.setDoOutput(true);
      conn.setUseCaches(false);
      conn.setRequestMethod("POST");
      out = new DataOutputStream(conn.getOutputStream());
      if(existsBody){
        out.write(body.getBytes(ENCODE));
      }else if(existsParam){
        out.write(paramStr.getBytes(ENCODE));
      }
      out.flush();
      in = new BufferedReader(new InputStreamReader(conn.getInputStream(), ENCODE));
      String line = null;
      StringBuilder sb = new StringBuilder();
      while(null!=(line = in.readLine())){
        sb.append(line);
      }
      Bean ret = JSON.toBean(sb.toString());
      if(ret.getBoolean(RESPONSE_SUCCESS,false)){
        response.setSuccess(true);
        response.setData(ret.get(RESPONSE_DATA));
      }else{
        response.setSuccess(false);
        response.setMessage(ret.getString(RESPONSE_ERR_MSG));
      }
    }catch(Exception e){
      response.setSuccess(false);
      response.setMessage(e.getMessage());
    }finally{
      IOUtil.closeQuietly(out);
      IOUtil.closeQuietly(in);
      if(null!=conn){
        conn.disconnect();
      }
    }
    return response;
  }
}
