package com.nlf.resource;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import com.nlf.resource.klass.ClassResource;

/**
 * 资源
 * 
 * @author 6tail
 *
 */
public class Resource{
  public static final String PACKAGE_SEPARATOR = ".";
  /** 是否位于jar中 */
  protected boolean inJar;
  /** 所在路径 */
  protected String root;
  /** 文件名 */
  protected String fileName;
  /** 资源类型 */
  protected ResourceType type;

  public boolean isInJar(){
    return inJar;
  }

  public void setInJar(boolean inJar){
    this.inJar = inJar;
  }

  public String getRoot(){
    return root;
  }

  public void setRoot(String root){
    this.root = root;
  }

  public String getFileName(){
    return fileName;
  }

  public void setFileName(String fileName){
    this.fileName = fileName;
  }

  public ResourceType getType(){
    return type;
  }

  public void setType(ResourceType type){
    this.type = type;
  }

  /**
   * 获取文件最后修改时间
   * @return 文件最后修改时间
   * @throws IOException IOException
   */
  public long lastModified() throws IOException{
    if(inJar){
      ZipFile zip = new ZipFile(root);
      ZipEntry en = zip.getEntry(fileName);
      return en.getTime();
    }else{
      switch(type){
        case klass:
          ClassResource res = (ClassResource)this;
          String pkg = res.getClassName();
          if(pkg.contains(PACKAGE_SEPARATOR)){
            pkg = pkg.substring(0,pkg.lastIndexOf(PACKAGE_SEPARATOR));
          }
          return new File(res.getRoot()+File.separator+pkg.replace(".",File.separator)+File.separator+res.getFileName()).lastModified();
        default:
          return new File(root+File.separator+fileName).lastModified();
      }
    }
  }

  @Override
  public String toString(){
    return "type="+type+" inJar="+inJar+" root="+root+" fileName="+fileName;
  }
}