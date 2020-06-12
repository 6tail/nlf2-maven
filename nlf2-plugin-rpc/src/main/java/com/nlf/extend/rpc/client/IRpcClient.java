package com.nlf.extend.rpc.client;

import com.nlf.extend.rpc.socket.ISocketRpcExchange;

import java.io.File;
import java.util.Map;

/**
 * RPC客户端接口
 *
 * @author 6tail
 */
public interface IRpcClient extends ISocketRpcExchange{

  /**
   * 是否支持
   * @param type 类型
   * @return true/false
   */
  boolean support(String type);

  /**
   * 调用
   * @param host 主机地址
   * @param port 主机端口
   * @param path 请求路径，以/开头
   * @param args 请求参数
   * @param body 数据体
   * @param file 上传文件
   * @return 响应结果
   */
  IRpcResponse call(String host, int port, String path, Map<String,String> args, String body, File... file);

  /**
   * 调用
   * @param host 主机地址
   * @param port 主机端口
   * @param path 请求路径，以/开头
   * @param args 请求参数
   * @return 响应结果
   */
  IRpcResponse call(String host, int port, String path, Map<String,String> args);

  /**
   * 调用
   * @param host 主机地址
   * @param port 主机端口
   * @param path 请求路径，以/开头
   * @param body 数据体
   * @return 响应结果
   */
  IRpcResponse call(String host, int port, String path, String body);

  /**
   * 调用
   * @param host 主机地址
   * @param port 主机端口
   * @param path 请求路径，以/开头
   * @return 响应结果
   */
  IRpcResponse call(String host, int port, String path);
}
