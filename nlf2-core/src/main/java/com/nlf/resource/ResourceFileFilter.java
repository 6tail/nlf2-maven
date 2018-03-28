package com.nlf.resource;

import java.io.File;
import java.io.FileFilter;

/**
 * resource文件过滤器
 * 
 * @author 6tail
 * 
 */
public class ResourceFileFilter implements FileFilter{

  public boolean accept(File f){
    if(f.isDirectory()) return true;
    String name = f.getName();
    if(name.endsWith(".class")||name.endsWith(".properties")) return true;
    return false;
  }
}