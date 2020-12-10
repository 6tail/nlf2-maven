package com.nlf.util;

import java.io.*;

/**
 * 输入流缓存，以便多次使用
 *
 * @author 6tail
 *
 */
public class InputStreamCache{
  /** 内存数据 */
  private byte[] data;
  /** 临时文件 */
  private File tempFile;
  /** 临时文件前缀 */
  private static final String TMP_PREFIX = "___";
  /** 临时文件后缀 */
  private static final String TMP_SUFFIX = ".tmp";

  public InputStreamCache(InputStream inputStream) throws IOException{
    if(null==inputStream){
      return;
    }
    boolean mem = true;
    byte[] buffer = new byte[IOUtil.BUFFER_SIZE];
    int l;
    ByteArrayOutputStream byteOut = null;
    try {
      byteOut = new ByteArrayOutputStream();
      while (-1 != (l = inputStream.read(buffer))) {
        byteOut.write(buffer, 0, l);
        byteOut.flush();
        if (byteOut.size() > IOUtil.BUFFER_SIZE) {
          mem = false;
          break;
        }
      }
    }finally {
      IOUtil.closeQuietly(byteOut);
    }
    if(mem){
      data = byteOut.toByteArray();
    }else{
      BufferedOutputStream fileOut = null;
      try {
        tempFile = File.createTempFile(TMP_PREFIX, TMP_SUFFIX);
        fileOut = new BufferedOutputStream(new FileOutputStream(tempFile));
        fileOut.write(byteOut.toByteArray());
        while (-1 != (l = inputStream.read(buffer))) {
          fileOut.write(buffer, 0, l);
        }
        fileOut.flush();
      }finally {
        IOUtil.closeQuietly(fileOut);
      }
    }
  }

  public long getSize(){
    if(null!=data){
      return data.length;
    }
    if(null!=tempFile){
      return tempFile.length();
    }
    return 0;
  }

  /**
   * 获取输入流，可重复获取
   *
   * @return 输入流
   */
  public InputStream getInputStream() throws IOException {
    if(null!=data){
      return new ByteArrayInputStream(data);
    }
    if(null!=tempFile){
      return new FileInputStream(tempFile);
    }
    return null;
  }
}
