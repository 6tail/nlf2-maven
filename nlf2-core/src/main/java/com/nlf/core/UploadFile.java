package com.nlf.core;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import com.nlf.util.FileUtil;

/**
 * 已上传文件封装
 * 
 * @author 6tail
 * 
 */
public class UploadFile implements Serializable{
  private static final long serialVersionUID = 1L;
  /** 存储类型：内存中的字节数组 */
  public static final int TYPE_BYTES = 0;
  /** 存储类型：临时文件 */
  public static final int TYPE_TEMP_FILE = 1;
  /** 参数名 */
  private String key;
  /** 文件名称，含后缀 */
  private String name;
  /** 文件大小，单位：字节 */
  private long size;
  /** 文件类型 */
  private String contentType;
  /** 文件后缀，小写字母，不包括点号 */
  private String suffix = "";
  /** 存储类型 */
  private int type;
  /** 字节数组 */
  private byte[] bytes;
  /** 临时文件 */
  private File tempFile;

  /**
   * 获取输入流
   * @return 输入流
   * @throws FileNotFoundException 文件未找到异常
   */
  public java.io.InputStream getInputStream() throws java.io.FileNotFoundException{
    switch(type){
      case TYPE_BYTES:
        return new ByteArrayInputStream(bytes);
      case TYPE_TEMP_FILE:
        return new java.io.FileInputStream(tempFile);
    }
    return null;
  }

  public int getType(){
    return type;
  }

  public void setType(int type){
    this.type = type;
  }

  public byte[] getBytes(){
    return bytes;
  }

  public void setBytes(byte[] bytes){
    this.bytes = bytes;
  }

  public File getTempFile(){
    return tempFile;
  }

  public void setTempFile(File tempFile){
    this.tempFile = tempFile;
  }

  public String getKey(){
    return key;
  }

  public void setKey(String key){
    this.key = key;
  }

  /**
   * 获取文件名称
   * 
   * @return 文件名称
   */
  public String getName(){
    return name;
  }

  /**
   * 设置文件名称
   * 
   * @param name 文件名称
   */
  public void setName(String name){
    this.name = name;
  }

  /**
   * 获取文件大小
   * 
   * @return 文件大小，单位：字节
   */
  public long getSize(){
    return size;
  }

  /**
   * 设置文件大小
   * 
   * @param size 文件大小，单位：字节
   */
  public void setSize(long size){
    this.size = size;
  }

  /**
   * 获取文件类型
   * 
   * @return 文件类型
   */
  public String getContentType(){
    return contentType;
  }

  /**
   * 设置文件类型
   * 
   * @param contentType 文件类型
   */
  public void setContentType(String contentType){
    this.contentType = contentType;
  }

  /**
   * 获取文件后缀
   * 
   * @return 文件后缀：小写字母，不包括点号
   */
  public String getSuffix(){
    return suffix;
  }

  /**
   * 设置文件后缀
   * 
   * @param suffix 文件后缀：小写字母，不包括点号
   */
  public void setSuffix(String suffix){
    this.suffix = suffix;
  }

  /**
   * 保存到文件
   * @param file 文件
   * @throws IOException IO异常
   */
  public void saveTo(File file) throws java.io.IOException{
    switch(type){
      case UploadFile.TYPE_TEMP_FILE:
        FileUtil.write(new FileInputStream(tempFile),file);
        break;
      case UploadFile.TYPE_BYTES:
        FileUtil.write(new ByteArrayInputStream(bytes),file);
        break;
    }
  }
}