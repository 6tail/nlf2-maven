package com.nlf.core.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.nlf.App;
import com.nlf.core.AbstractProxy;

/**
 * 框架默认代理，反射调用，不支持AOP哦
 * @author 6tail
 *
 */
public class DefaultProxy extends AbstractProxy{
  @SuppressWarnings("unchecked")
  public <T>T newInstance(String interfaceOrClassName){
    String implClass = App.getImplement(interfaceOrClassName);
    if(null==implClass){
      return null;
    }
    try{
      return (T)Class.forName(implClass).newInstance();
    }catch(InstantiationException e){
      throw new RuntimeException(e);
    }catch(IllegalAccessException e){
      throw new RuntimeException(e);
    }catch(ClassNotFoundException e){
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public <T>T execute(String interfaceOrClassName,String method,Object... args){
    Object o = newInstance(interfaceOrClassName);
    try{
      Method m = o.getClass().getMethod(method);
      return (T)m.invoke(o,args);
    }catch(SecurityException e){
      throw new RuntimeException(e);
    }catch(NoSuchMethodException e){
      throw new RuntimeException(e);
    }catch(IllegalArgumentException e){
      throw new RuntimeException(e);
    }catch(IllegalAccessException e){
      throw new RuntimeException(e);
    }catch(InvocationTargetException e){
      throw new RuntimeException(e);
    }
  }
}