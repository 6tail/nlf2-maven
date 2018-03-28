package com.nlf.bytecode;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.zip.ZipFile;
import com.nlf.App;
import com.nlf.resource.klass.ClassResource;
import com.nlf.util.IOUtil;

/**
 * 字节码读取
 * 
 * @author 6tail
 *
 */
public class ByteCodeReader{
  /**
   * 读取类字节码
   * 
   * @param r 资源：类
   * @return 字节码
   */
  public byte[] readClass(ClassResource r) throws IOException{
    String className = r.getClassName();
    if(r.isInJar()){
      ZipFile zip = null;
      try{
        zip = new ZipFile(r.getRoot());
        java.util.zip.ZipEntry entry = zip.getEntry(className.replace(".","/")+".class");
        InputStream in = zip.getInputStream(entry);
        return IOUtil.toBytes(in);
      }finally{
        IOUtil.closeQuietly(zip);
      }
    }else{
      File classFile = new File(r.getRoot()+File.separator+className.replace(".",File.separator)+".class");
      InputStream in = new java.io.FileInputStream(classFile);
      return IOUtil.toBytes(in);
    }
  }

  /**
   * 读取类实现的接口
   * 
   * @param r 资源：类
   * @return 实现的接口集合
   */
  public Set<String> readInterfaces(ClassResource r){
    Set<String> interfaces = new java.util.HashSet<String>();
    try{
      byte[] byteCodes = readClass(r);
      Klass klass = new Klass(byteCodes);
      r.setMethods(klass.getMethods());
      if(klass.isAbstract()){
        r.setAbstractClass(true);
      }
      if(klass.isInterface()){
        r.setInterfaceClass(true);
      }
      Set<String> fathers = klass.getInterfaces();
      interfaces.addAll(fathers);
      for(String father:fathers){
        ClassResource c = App.CLASS.get(father);
        if(null!=c){
          interfaces.addAll(readInterfaces(c));
        }
      }
      String superClass = klass.getSuperClass();
      if(!"java.lang.Object".equals(superClass)){
        ClassResource c = App.CLASS.get(superClass);
        if(null!=c){
          interfaces.addAll(readInterfaces(c));
        }
      }
    }catch(Throwable e){
      throw new com.nlf.exception.NlfException(r+"",e);
    }
    return interfaces;
  }
}