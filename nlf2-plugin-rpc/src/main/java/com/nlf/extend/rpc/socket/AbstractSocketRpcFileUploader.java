package com.nlf.extend.rpc.socket;

import com.nlf.core.UploadFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象Socket RPC文件上传
 * @author 6tail
 */
public abstract class AbstractSocketRpcFileUploader implements ISocketRpcFileUploader {

  /** 传输的文件列表 */
  protected List<UploadFile> files = new ArrayList<UploadFile>();

  public List<UploadFile> getFiles() {
    return files;
  }
}
