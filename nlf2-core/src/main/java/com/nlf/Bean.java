package com.nlf;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.*;

/**
 * 通用对象封装，支持链式调用
 *
 * @author 6tail
 *
 */
public class Bean implements Map<String,Object>,java.io.Serializable{
  private static final long serialVersionUID = 1;
  /** 键值对 */
  private Map<String,Object> values = new HashMap<String,Object>();

  public Bean(){}

  public Bean(String key,Object value){
    values.put(key,value);
  }

  /**
   * 是否存在指定键
   *
   * @param key 键
   * @return true/false 存在/不存在
   */
  public boolean containsKey(String key){
    return values.containsKey(key);
  }

  /**
   * 获取值
   *
   * @param key 键
   * @return 值
   */
  @SuppressWarnings("unchecked")
  public <T>T get(String key){
    return (T)values.get(key);
  }

  /**
   * 获取Object值，可能返回null
   *
   * @param key 键
   * @param klass 指定返回类型
   * @return 值
   */
  @SuppressWarnings("unchecked")
  public <E>E get(String key,Class<E> klass){
    return (E)values.get(key);
  }

  /**
   * 获取Object值，如果为null,返回默认值
   *
   * @param key 键
   * @param klass 指定返回类型
   * @param defaultValue 默认值
   * @return 值
   */
  @SuppressWarnings("unchecked")
  public <E>E get(String key,Class<E> klass,E defaultValue){
    Object o = values.get(key);
    return null==o?defaultValue:(E)o;
  }

  /**
   * 转换为指定类的实例
   * @param klass 指定类
   * @return 实例
   */
  @SuppressWarnings("unchecked")
  public <E>E toObject(Class<E> klass) throws IntrospectionException,IllegalAccessException,InvocationTargetException {
    if(Bean.class.equals(klass)||Map.class.equals(klass)){
      return (E)this;
    }
    Object ret = null;
    int lastMatchedKeyCount = 0;
    List<String> impls = App.getImplements(klass);
    for (String c : impls) {
      int matchedKeyCount = 0;
      Object o = App.getProxy().newInstance(c);
      BeanInfo info = Introspector.getBeanInfo(o.getClass(), Object.class);
      PropertyDescriptor[] props = info.getPropertyDescriptors();
      for (PropertyDescriptor desc : props) {
        Method writeMethod = desc.getWriteMethod();
        Type type = writeMethod.getGenericParameterTypes()[0];
        String typeString = type+"";
        String key = desc.getName();
        for (String bKey : keySet()) {
          if (!bKey.equalsIgnoreCase(key)) {
            continue;
          }
          Object value = get(bKey);
          if(null!=value) {
            if (value instanceof Integer) {
              int v = (Integer) value;
              if ("byte".equals(typeString)) {
                value = (byte) v;
              } else if ("short".equals(typeString)) {
                value = (short) v;
              }
            } else if (value instanceof Double) {
              double v = (Double) value;
              if ("byte".equals(typeString)) {
                value = (byte) v;
              } else if ("short".equals(typeString)) {
                value = (short) v;
              } else if ("int".equals(typeString)) {
                value = (int) v;
              } else if ("long".equals(typeString)) {
                value = (long) v;
              } else if ("float".equals(typeString)) {
                value = (float) v;
              }
            } else if (value instanceof String) {
              String v = value + "";
              if ("byte".equals(typeString)) {
                value = Byte.parseByte(v);
              } else if ("short".equals(typeString)) {
                value = Short.parseShort(v);
              } else if ("int".equals(typeString)) {
                value = Integer.parseInt(v);
              } else if ("long".equals(typeString)) {
                value = Long.parseLong(v);
              } else if ("float".equals(typeString)) {
                value = Float.parseFloat(v);
              } else if ("double".equals(typeString)) {
                value = Double.parseDouble(v);
              }
            } else if (value instanceof Bean) {
              value = ((Bean) value).toObject(desc.getPropertyType());
            } else if (value instanceof List) {
              Type elType = desc.getPropertyType().getComponentType();
              if(null==elType){
                elType = ((ParameterizedType) type).getActualTypeArguments()[0];
              }
              Map<Integer, Object> cache = new HashMap<Integer, Object>();
              List l = (List) value;
              for (int i = 0, j = l.size(); i < j; i++) {
                Object el = l.get(i);
                if (el instanceof Bean) {
                  cache.put(i, ((Bean) el).toObject((Class) elType));
                }
              }
              for (Entry<Integer, Object> entry : cache.entrySet()) {
                l.set(entry.getKey(), entry.getValue());
              }
              if (typeString.startsWith(Set.class.getName())) {
                value = new HashSet(l);
              } else if (typeString.startsWith(Queue.class.getName())) {
                value = new LinkedList(l);
              } else if (typeString.startsWith("class [")) {
                int size = l.size();
                value = Array.newInstance((Class) elType, size);
                for (int i = 0; i < size; i++) {
                  Array.set(value, i, l.get(i));
                }
              }
            }
          }
          writeMethod.invoke(o, value);
          matchedKeyCount++;
          break;
        }
      }
      if (matchedKeyCount > lastMatchedKeyCount) {
        lastMatchedKeyCount = matchedKeyCount;
        ret = o;
      }
    }
    return (E)ret;
  }

  /**
   * 设置值
   *
   * @param key 键
   * @param value 值
   * @return 自己
   */
  public Bean set(String key,Object value){
    values.put(key,value);
    return this;
  }

  /**
   * 当条件满足时设置值
   *
   * @param key 键
   * @param value 值
   * @param condition 条件
   * @return 自己
   */
  public Bean setIf(String key,Object value,boolean condition){
    if(condition){
      values.put(key, value);
    }
    return this;
  }

  /**
   * 移除指定键值
   *
   * @param key 键
   * @return 自己
   */
  public Bean remove(String key){
    values.remove(key);
    return this;
  }

  /**
   * 获取键的集合
   *
   * @return 键集合
   */
  public Set<String> keySet(){
    return values.keySet();
  }

  public String toString(){
    return values.toString();
  }

  /**
   * 获取Bean值，一般用于链式调用，可能返回null
   * 
   * @param key 键
   * @return Bean，可能为null
   */
  public Bean getBean(String key){
    return get(key);
  }

  /**
   * 获取Bean值，一般用于链式调用，如果获取失败，返回默认值，不抛出异常
   * 
   * @param key 键
   * @return Bean
   */
  public Bean getBean(String key,Bean defaultValue){
    Bean v = null;
    try{
      v = get(key);
    }catch(Exception e){}
    return null==v?defaultValue:v;
  }

  /**
   * 获取short值，如果获取不到或出错，返回默认值，不抛出异常
   *
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public short getInt(String key,short defaultValue){
    try{
      return Short.parseShort(String.valueOf(values.get(key)));
    }catch(Exception e){
      return defaultValue;
    }
  }

  /**
   * 获取int值，如果获取不到或出错，返回默认值，不抛出异常
   *
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public int getInt(String key,int defaultValue){
    try{
      return Integer.parseInt(String.valueOf(values.get(key)));
    }catch(Exception e){
      return defaultValue;
    }
  }

  /**
   * 获取long值，如果获取不到或出错，返回默认值，不抛出异常
   *
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public long getLong(String key,long defaultValue){
    try{
      return Long.parseLong(String.valueOf(values.get(key)));
    }catch(Exception e){
      return defaultValue;
    }
  }

  /**
   * 获取double值，如果获取不到或出错，返回默认值，不抛出异常
   *
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public double getDouble(String key,double defaultValue){
    try{
      return Double.parseDouble(String.valueOf(values.get(key)));
    }catch(Exception e){
      return defaultValue;
    }
  }

  /**
   * 获取float值，如果获取不到或出错，返回默认值，不抛出异常
   *
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public float getFloat(String key,float defaultValue){
    try{
      return Float.parseFloat(String.valueOf(values.get(key)));
    }catch(Exception e){
      return defaultValue;
    }
  }

  /**
   * 获取boolean值，如果获取不到或出错，返回默认值，不抛出异常
   *
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public boolean getBoolean(String key,boolean defaultValue){
    try{
      return Boolean.parseBoolean(String.valueOf(values.get(key)));
    }catch(Exception e){
      return defaultValue;
    }
  }

  /**
   * 获取String值，如果为null,返回null
   *
   * @param key 键
   * @return 值
   */
  public String getString(String key){
    return getString(key,null);
  }

  /**
   * 获取String值，如果为null,返回默认值
   *
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public String getString(String key,String defaultValue){
    Object o = values.get(key);
    return null==o?defaultValue:o.toString();
  }

  /**
   * 强制获取List，即使是非Collection，也会强制返回只有1个元素的List。如果不存在该键，返回0个元素的List。
   * 
   * @param key 键
   * @return List
   */
  @SuppressWarnings("unchecked")
  public <T>List<T> getList(String key){
    List<T> l = new ArrayList<T>();
    Object o = values.get(key);
    if(null==o) return l;
    if(o instanceof Collection){
      l.addAll((Collection<T>)o);
    }else{
      l.add((T)o);
    }
    return l;
  }

  /**
   * 强制获取List，即使是非Collection，也会强制返回只有1个元素的List。如果不存在该键，返回0个元素的List。
   *
   * @param key 键
   * @param klass 指定的返回类型
   * @return List
   */
  @SuppressWarnings("unchecked")
  public <E> List<E> getList(String key,Class<E> klass){
    List<E> l = new ArrayList<E>();
    Object o = values.get(key);
    if(null==o) return l;
    if(o instanceof Collection){
      l.addAll((Collection<E>)o);
    }else{
      l.add((E)o);
    }
    return l;
  }

  @Deprecated
  public boolean containsKey(Object key){
    return containsKey(key+"");
  }

  public boolean containsValue(Object value){
    return values.containsValue(value);
  }

  public Set<java.util.Map.Entry<String,Object>> entrySet(){
    return values.entrySet();
  }

  @Deprecated
  public Object get(Object key){
    return values.get(key);
  }

  public boolean isEmpty(){
    return values.isEmpty();
  }

  @Deprecated
  public Object put(String key,Object value){
    return values.put(key,value);
  }

  public void putAll(Map<? extends String,?> map){
    values.putAll(map);
  }

  @Deprecated
  public Object remove(Object key){
    return values.remove(key);
  }

  public int size(){
    return values.size();
  }

  public Collection<Object> values(){
    return values.values();
  }

  public void clear(){
    values.clear();
  }
}