package com.nlf.extend.rpc.socket;

import com.nlf.core.IFileUploader;
import com.nlf.core.UploadFile;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * Socket RPC文件上传接口
 *
 * @author 6tail
 *
 */
public interface ISocketRpcFileUploader extends IFileUploader{

  /**
   * 解析传送的文件
   * @param fileName 文件名
   * @param fileSize 文件大小
   * @param in 输入流
   * @return 文件
   * @throws IOException IOException
   */
  UploadFile parseFile(String fileName, long fileSize, DataInputStream in) throws IOException;
}
