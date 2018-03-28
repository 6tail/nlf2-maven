package com.nlf.core;

/**
 * 文件上传接口
 * @author 6tail
 *
 */
public interface IFileUploader{
  /**
   * 获取上传的文件
   * @return 文件列表
   */
  java.util.List<UploadFile> getFiles();
}