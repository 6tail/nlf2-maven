package com.nlf.extend.web.view;

import java.io.InputStream;
import com.nlf.extend.web.WebView;
import com.nlf.util.ContentTypes;

/**
 * 流
 * 
 * @author 6tail
 *
 */
public class StreamView extends WebView{
  /** 输入流 */
  protected InputStream inputStream;
  /** 名称 */
  protected String name;
  /** Content-Type */
  protected String contentType = ContentTypes.DEFAULT;
  /** 大小 */
  protected long size = -1;

  public StreamView(InputStream inputStream){
    setInputStream(inputStream);
  }

  public StreamView(InputStream inputStream,String name){
    this(inputStream);
    setName(name);
  }

  public StreamView(InputStream inputStream,String name,long size){
    this(inputStream,name);
    setSize(size);
  }

  public StreamView(InputStream inputStream,String name,String contentType){
    this(inputStream,name);
    setContentType(contentType);
  }

  public StreamView(InputStream inputStream,String name,String contentType,long size){
    this(inputStream,name,contentType);
    setSize(size);
  }

  public String getName(){
    return name;
  }

  public StreamView setName(String name){
    this.name = name;
    if(name.contains(".")){
      String ext = name.substring(name.lastIndexOf("."));
      setContentType(ContentTypes.getContentType(ext));
    }
    return this;
  }

  public String getContentType(){
    return contentType;
  }

  public StreamView setContentType(String contentType){
    this.contentType = contentType;
    return this;
  }

  public long getSize(){
    return size;
  }

  public StreamView setSize(long size){
    this.size = size;
    return this;
  }

  public InputStream getInputStream(){
    return inputStream;
  }

  public StreamView setInputStream(InputStream inputStream){
    this.inputStream = inputStream;
    return this;
  }
}