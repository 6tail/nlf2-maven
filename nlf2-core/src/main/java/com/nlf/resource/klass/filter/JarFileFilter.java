package com.nlf.resource.klass.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * jar文件过滤器，只允许.jar文件
 *
 * @author 6tail
 *
 */
public class JarFileFilter implements FileFilter{

  public boolean accept(File f){
    return f.getName().endsWith(".jar");
  }
}