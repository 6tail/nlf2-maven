package com.nlf.extend.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import com.nlf.View;
import com.nlf.exception.NlfException;
import com.nlf.extend.web.view.FileView;
import com.nlf.extend.web.view.PageView;
import com.nlf.extend.web.view.RedirectView;
import com.nlf.extend.web.view.StreamView;

/**
 * WEB视图
 * @author 6tail
 *
 */
public abstract class WebView extends View{

  public static PageView page(String uri){
    return new PageView(uri);
  }

  public static RedirectView redirect(String uri){
    return new RedirectView(uri);
  }

  public static StreamView stream(InputStream inputStream){
    return new StreamView(inputStream);
  }

  public static StreamView stream(InputStream inputStream,String name){
    return new StreamView(inputStream,name);
  }

  public static StreamView stream(InputStream inputStream,String name,long size){
    return new StreamView(inputStream,name,size);
  }

  public static StreamView stream(InputStream inputStream,String name,String contentType){
    return new StreamView(inputStream,name,contentType);
  }

  public static StreamView stream(InputStream inputStream,String name,String contentType,long size){
    return new StreamView(inputStream,name,contentType,size);
  }

  public static FileView file(File file){
    try{
      return new FileView(file);
    }catch(FileNotFoundException e){
      throw new NlfException(e);
    }
  }

  public static FileView file(File file,String name){
    try{
      return new FileView(file,name);
    }catch(FileNotFoundException e){
      throw new NlfException(e);
    }
  }

}