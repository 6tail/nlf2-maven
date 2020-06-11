package com.nlf.extend.rpc.client;

import com.nlf.core.UploadFile;

import java.util.ArrayList;
import java.util.List;

/**
 * 抽象RPC响应
 * @author 6tail
 */
public abstract class AbstractRpcResponse implements IRpcResponse{
  protected boolean success;
  protected String message;
  protected Object data;
  protected List<UploadFile> files = new ArrayList<UploadFile>();

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @SuppressWarnings("unchecked")
  public <T>T getData() {
    return (T)data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public List<UploadFile> getFiles() {
    return null==files?new ArrayList<UploadFile>():files;
  }

  public void setFiles(List<UploadFile> files) {
    this.files = files;
  }

  public void addFile(UploadFile file){
    if(null==files){
      files = new ArrayList<UploadFile>();
    }
    files.add(file);
  }
}
