package com.nlf.extend.web.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 文件
 * 
 * @author 6tail
 *
 */
public class FileView extends StreamView{
  /** 文件 */
  protected File file;

  public FileView(File file) throws FileNotFoundException{
    this(file,file.getName());
  }

  public FileView(File file,String name) throws FileNotFoundException{
    super(null);
    setFile(file,name);
  }

  public FileView setFile(File file) throws FileNotFoundException{
    setFile(file,file.getName());
    return this;
  }

  public FileView setFile(File file,String name) throws FileNotFoundException{
    setInputStream(new FileInputStream(file)).setName(name).setSize(file.length());
    return this;
  }
}