package com.nlf.extend.serialize.xml.impl;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import com.nlf.serialize.AbstractWrapper;
import com.nlf.util.DateUtil;

/**
 * 默认xml包装器
 * 
 * @author 6tail
 *
 */
public class DefaultXmlWrapper extends AbstractWrapper{
  protected String rootTag = "data";

  protected String wrapNumber(Object o,String tag){
    return "<"+tag+">"+o+"</"+tag+">";
  }

  protected String wrapBool(Object o,String tag){
    return "<"+tag+">"+o+"</"+tag+">";
  }

  protected String wrapString(Object o,String tag){
    StringBuilder s = new StringBuilder();
    s.append("<");
    s.append(tag);
    s.append(">");
    String os = o+"";
    if(os.contains("<")||os.contains(">")){
      s.append("<![CDATA[");
      s.append(os);
      s.append("]]>");
    }else{
      s.append(os);
    }
    s.append("</");
    s.append(tag);
    s.append(">");
    return s.toString();
  }

  protected String wrapDate(Object o,String tag){
    return "<"+tag+">"+DateUtil.ymdhms((Date)o)+"</"+tag+">";
  }

  protected String wrapArray(Object o,String tag){
    StringBuilder s = new StringBuilder();
    Object[] arr = (Object[])o;
    s.append("<");
    s.append(tag);
    s.append(">");
    for(Object obj:arr){
      s.append(wrap(obj,tag.toLowerCase().endsWith("s")?tag.substring(0,tag.length()-1):tag));
    }
    s.append("</");
    s.append(tag);
    s.append(">");
    return s.toString();
  }

  protected String wrapCollection(Object o,String tag){
    StringBuilder s = new StringBuilder();
    s.append("<");
    s.append(tag);
    s.append(">");
    Collection<?> c = (Collection<?>)o;
    for(Object obj:c){
      s.append(wrap(obj,tag.toLowerCase().endsWith("s")?tag.substring(0,tag.length()-1):tag));
    }
    s.append("</");
    s.append(tag);
    s.append(">");
    return s.toString();
  }

  protected String wrapMap(Object o,String tag){
    StringBuilder s = new StringBuilder();
    s.append("<");
    s.append(tag);
    s.append(">");
    Map<?,?> m = (Map<?,?>)o;
    for(Map.Entry entry:m.entrySet()){
      s.append(wrap(entry.getValue(),entry.getKey()+""));
    }
    s.append("</");
    s.append(tag);
    s.append(">");
    return s.toString();
  }

  protected String wrapObject(Object o){
    try{
      StringBuilder s = new StringBuilder();
      BeanInfo info = Introspector.getBeanInfo(o.getClass(),Object.class);
      PropertyDescriptor[] props = info.getPropertyDescriptors();
      for(PropertyDescriptor desc:props){
        Method method = desc.getReadMethod();
        if(null==method){
          s.append("<");
          s.append(desc.getName());
          s.append(">");
          s.append("</");
          s.append(desc.getName());
          s.append(">");
        }else{
          s.append(wrap(method.invoke(o),desc.getName()));
        }
      }
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

  protected String wrap(Object o,String tag){
    StringBuilder s = new StringBuilder();
    if(null==o){
      s.append(wrapNumber("",tag));
    }else if(o instanceof Number){
      s.append(wrapNumber(o,tag));
    }else if(o instanceof Boolean){
      s.append(wrapBool(o,tag));
    }else if(o instanceof Character||o instanceof String){
      s.append(wrapString(o,tag));
    }else if(o instanceof Date){
      s.append(wrapDate(o,tag));
    }else if(o.getClass().isArray()){
      s.append(wrapArray(o,tag));
    }else if(o instanceof Collection){
      s.append(wrapCollection(o,tag));
    }else if(o instanceof Map){
      s.append(wrapMap(o,tag));
    }else if(o instanceof Enum){
      s.append(wrapString(o,tag));
    }else{
      s.append(wrapObject(o));
    }
    return s.toString();
  }

  public String wrap(Object o){
    return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+wrap(o,rootTag);
  }

  public boolean support(String format){
    return "xml".equalsIgnoreCase(format);
  }
}