package com.nlf.serialize.json.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import com.nlf.dao.paging.PageData;
import com.nlf.serialize.AbstractWrapper;
import com.nlf.util.Base64Util;
import com.nlf.util.DateUtil;

/**
 * 默认json包装器
 * 
 * @author 6tail
 *
 */
public class DefaultJsonWrapper extends AbstractWrapper{
  protected String quote = "\"";

  protected String wrapNumber(Object o){
    return o+"";
  }

  protected String wrapBool(Object o){
    return o+"";
  }

  protected String wrapString(Object o){
    String s = o+"";
    s = s.replace("\\","\\\\");
    s = s.replace("\b","\\b");
    s = s.replace("\t","\\t");
    s = s.replace("\n","\\n");
    s = s.replace("\f","\\f");
    s = s.replace("\r","\\r");
    //s = s.replace("\'","\\\'");
    s = s.replace("\"","\\\"");
    return quote+s+quote;
  }

  protected String wrapDate(Object o){
    return wrapString(DateUtil.ymdhms((Date)o));
  }

  protected String wrapByteArray(Object o){
    byte[] d = (byte[])o;
    return wrapString(Base64Util.encode(d));
  }

  protected String wrapArray(Object o){
    if(o instanceof byte[]){
      return wrapByteArray(o);
    }
    Object[] l = (Object[])o;
    return wrapCollection(Arrays.asList(l));
  }

  protected String wrapCollection(Object o){
    Collection<?> l = (Collection<?>)o;
    StringBuilder s = new StringBuilder();
    s.append("[");
    Iterator<?> it = l.iterator();
    while(it.hasNext()){
      s.append(wrap(it.next()));
      if(it.hasNext()){
        s.append(",");
      }
    }
    s.append("]");
    return s.toString();
  }

  protected String wrapEnumeration(Object o){
    Enumeration<?> l = (Enumeration<?>)o;
    StringBuilder s = new StringBuilder();
    s.append("[");
    while(l.hasMoreElements()){
      s.append(wrap(l.nextElement()));
      if(l.hasMoreElements()){
        s.append(",");
      }
    }
    s.append("]");
    return s.toString();
  }

  protected String wrapMap(Object o){
    Map<?,?> m = (Map<?,?>)o;
    StringBuilder s = new StringBuilder();
    s.append("{");
    Iterator<?> it = m.keySet().iterator();
    while(it.hasNext()){
      Object key = it.next();
      s.append(wrapString(key+""));
      s.append(":");
      s.append(wrap(m.get(key)));
      if(it.hasNext()){
        s.append(",");
      }
    }
    s.append("}");
    return s.toString();
  }

  protected String wrapObject(Object o){
    try{
      StringBuilder s = new StringBuilder();
      s.append("{");
      BeanInfo info = Introspector.getBeanInfo(o.getClass(),Object.class);
      PropertyDescriptor[] props = info.getPropertyDescriptors();
      for(int i = 0,j=props.length;i<j;i++){
        if(i>0){
          s.append(",");
        }
        PropertyDescriptor desc = props[i];
        s.append(wrapString(desc.getName()));
        s.append(":");
        Method method = desc.getReadMethod();
        if(null==method){
          s.append(wrap(null));
        }else{
          s.append(wrap(method.invoke(o)));
        }
      }
      s.append("}");
      return s.toString();
    }catch(IllegalArgumentException e){
      throw new RuntimeException(e);
    }catch(IllegalAccessException e){
      throw new RuntimeException(e);
    }catch(InvocationTargetException e){
      throw new RuntimeException(e);
    }catch(IntrospectionException e){
      throw new RuntimeException(e);
    }
  }

  public String wrap(Object o){
    StringBuilder s = new StringBuilder();
    if(null==o){
      s.append("null");
    }else if(o instanceof Number){
      s.append(wrapNumber(o));
    }else if(o instanceof Boolean){
      s.append(wrapBool(o));
    }else if(o instanceof Character||o instanceof String){
      s.append(wrapString(o));
    }else if(o instanceof Date){
      s.append(wrapDate(o));
    }else if(o.getClass().isArray()){
      s.append(wrapArray(o));
    }else if(o instanceof PageData){
      s.append(wrapObject(o));
    }else if(o instanceof Collection){
      s.append(wrapCollection(o));
    }else if(o instanceof Map){
      s.append(wrapMap(o));
    }else if(o instanceof Enumeration){
      s.append(wrapEnumeration(o));
    }else if(o instanceof Enum){
      s.append(wrapString(o));
    }else{
      s.append(wrapObject(o));
    }
    return s.toString();
  }

  public boolean support(String format){
    return "json".equalsIgnoreCase(format);
  }
}