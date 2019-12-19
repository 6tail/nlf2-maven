package com.nlf.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 输入流缓存，以便多次使用
 *
 * @author 6tail
 *
 */
public class InputStreamCache{
  /** 缓存输出流 */
  private ByteArrayOutputStream cachedStream;

  public InputStreamCache(InputStream inputStream) throws IOException{
    if(null==inputStream){
      return;
    }
    cachedStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[IOUtil.BUFFER_SIZE];
    int l;
    while(-1!=(l = inputStream.read(buffer))){
      cachedStream.write(buffer,0,l);
    }
    cachedStream.flush();
  }

  /**
   * 获取输入流，可重复获取
   *
   * @return 输入流
   */
  public InputStream getInputStream(){
    if(null==cachedStream){
      return null;
    }
    return new ByteArrayInputStream(cachedStream.toByteArray());
  }
}
