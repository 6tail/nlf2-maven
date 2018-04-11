package com.nlf.extend;

public class HotSwapClassLoader extends ClassLoader{

  public HotSwapClassLoader(ClassLoader parent){
    super(parent);
  }

  public Class<?> load(String className,byte[] b){
    return defineClass(className,b,0,b.length);
  }
}