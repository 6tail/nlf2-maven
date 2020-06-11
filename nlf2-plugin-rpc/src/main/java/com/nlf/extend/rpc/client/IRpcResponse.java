package com.nlf.extend.rpc.client;

import com.nlf.core.UploadFile;

import java.util.List;

/**
 * RPC响应
 * @author 6tail
 */
public interface IRpcResponse {

  /**
   * 调用是否成功
   * @return true/false
   */
  boolean isSuccess();

  /**
   * 失败消息
   * @return 失败消息
   */
  String getMessage();

  /**
   * 成功返回的结果
   * @param <T> Bean/List<Bean>/String等
   * @return 返回结果
   */
  <T>T getData();


  /**
   * 成功传送的文件
   * @return 文件列表
   */
  List<UploadFile> getFiles();
}
