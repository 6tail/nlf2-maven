package com.nlf.core;

/**
 * 类和方法
 *
 * @author 6tail
 * 
 */
public class ClassMethod{
  private String klass;
  private String method;
  private String ret;

  public String getKlass(){
    return klass;
  }

  public void setKlass(String klass){
    this.klass = klass;
  }

  public String getMethod(){
    return method;
  }

  public void setMethod(String method){
    this.method = method;
  }

  public String getRet(){
    return ret;
  }

  public void setRet(String ret){
    this.ret = ret;
  }

  public Object proceed(){
    return com.nlf.App.getProxy().execute(klass,method);
  }

  @Override
  public String toString(){
    return klass+" public "+ret+" "+method+"()";
  }
}