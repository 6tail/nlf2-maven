package com.nlf.core;

import com.nlf.App;
import com.nlf.bytecode.Method;
import com.nlf.resource.klass.ClassResource;

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

  public ClassMethod(){}

  public ClassMethod(String klass,String method){
    this(klass,method,true);
  }

  public ClassMethod(String klass,String method,boolean autoGuessRet){
    this(klass,method,null);
    if(autoGuessRet){
      ClassResource resource = App.CLASS.get(klass);
      for(Method m:resource.getMethods()){
        if(m.getName().equals(method)){
          String ret = m.getRet();
          if(Method.RET_OBJECT.equals(ret)){
            ret = m.getRetMaybe();
          }
          this.ret = ret;
          break;
        }
      }
    }
  }

  public ClassMethod(String klass,String method,String ret){
    this.klass = klass;
    this.method = method;
    this.ret = ret;
  }

  public Object proceed(){
    return App.getProxy().execute(klass,method);
  }

  @Override
  public String toString(){
    return klass+" public "+ret+" "+method+"()";
  }
}
