package com.nlf.extend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.nlf.App;
import com.nlf.bytecode.ByteCodeReader;
import com.nlf.core.impl.DefaultProxy;
import com.nlf.log.Logger;
import com.nlf.resource.klass.ClassResource;

/**
 * 热部署代理
 * @author 6tail
 *
 */
public class HotSwapProxy extends DefaultProxy{
  /** 类缓存 */
  protected static final Map<String,Class<?>> CLASS_POOL = new HashMap<String,Class<?>>();
  /** class文件最后修改时间缓存 */
  protected static final Map<String,Long> CLASS_LAST_MODIFIED = new HashMap<String,Long>();
  @Override
  @SuppressWarnings("unchecked")
  public <T>T newInstance(String interfaceOrClassName){
    String implClass = App.getImplement(interfaceOrClassName);
    if(null==implClass) return null;
    ClassResource res = App.CLASS.get(implClass);
    long time;
    try{
      time = res.lastModified();
    }catch(IOException e){
      throw new RuntimeException(e);
    }
    Long cachedTime = CLASS_LAST_MODIFIED.get(implClass);
    Class<?> klass = CLASS_POOL.get(implClass);
    if(null==cachedTime){
      CLASS_LAST_MODIFIED.put(implClass,time);
    }else{
      if(time!=cachedTime){
        CLASS_LAST_MODIFIED.put(implClass,time);
        Logger.getLog().debug(App.getProperty("nlf.extend.proxy.hotswap",implClass));
        HotSwapClassLoader classLoader = new HotSwapClassLoader(Thread.currentThread().getContextClassLoader());
        try{
          klass = classLoader.load(implClass,new ByteCodeReader().readClass(res));
        }catch(IOException e){
          throw new RuntimeException(e);
        }
      }
    }
    try{
      if(null==klass) klass = Class.forName(implClass);
      CLASS_POOL.put(implClass,klass);
      return (T)klass.newInstance();
    }catch(InstantiationException e){
      throw new RuntimeException(e);
    }catch(IllegalAccessException e){
      throw new RuntimeException(e);
    }catch(ClassNotFoundException e){
      throw new RuntimeException(e);
    }
  }

}