package com.nlf;

import com.nlf.util.DataTypes;
import com.nlf.util.StringUtil;
import com.nlf.util.Strings;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * 通用对象封装，支持链式调用
 *
 * @author 6tail
 *
 */
public class Bean implements Map<String,Object>,java.io.Serializable{
  private static final long serialVersionUID = 1;
  private static final String TYPE_CLASS_PREFIX = "class [";
  /** 键值对 */
  private Map<String,Object> values = new LinkedHashMap<String,Object>();

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

  protected Object convertString(String object,String type){
    Object value = object;
    if (DataTypes.BYTE.equals(type)) {
      value = Byte.parseByte(object);
    } else if (DataTypes.SHORT.equals(type)) {
      value = Short.parseShort(object);
    } else if (DataTypes.INT.equals(type)) {
      value = Integer.parseInt(object);
    } else if (DataTypes.LONG.equals(type)) {
      value = Long.parseLong(object);
    } else if (DataTypes.FLOAT.equals(type)) {
      value = Float.parseFloat(object);
    } else if (DataTypes.DOUBLE.equals(type)) {
      value = Double.parseDouble(object);
    }
    return value;
  }

  protected Object convertInteger(int object,String type){
    Object value = object;
    if (DataTypes.BYTE.equals(type)) {
      value = (byte) object;
    } else if (DataTypes.SHORT.equals(type)) {
      value = (short) object;
    }
    return value;
  }

  protected Object convertDouble(double object,String type){
    Object value = object;
    if (DataTypes.BYTE.equals(type)) {
      value = (byte) object;
    } else if (DataTypes.SHORT.equals(type)) {
      value = (short) object;
    } else if (DataTypes.INT.equals(type)) {
      value = (int) object;
    } else if (DataTypes.LONG.equals(type)) {
      value = (long) object;
    } else if (DataTypes.FLOAT.equals(type)) {
      value = (float) object;
    }
    return value;
  }

  protected Object convertBigDecimal(BigDecimal object,String type){
    Object value = object;
    if (DataTypes.BYTE.equals(type)) {
      value = object.byteValue();
    } else if (DataTypes.SHORT.equals(type)) {
      value = object.shortValue();
    }else if (DataTypes.INT.equals(type)) {
      value = object.intValue();
    }else if (DataTypes.LONG.equals(type)) {
      value = object.longValue();
    }else if (DataTypes.FLOAT.equals(type)) {
      value = object.floatValue();
    }else if (DataTypes.DOUBLE.equals(type)) {
      value = object.doubleValue();
    }
    return value;
  }

  @SuppressWarnings("unchecked")
  protected Object convert(Object object,Class<?> propType,Type paramType) throws IntrospectionException,IllegalAccessException,InvocationTargetException{
    Object value = object;
    String paramTypeString = paramType+"";
    if(null!=object) {
      if (value instanceof Integer) {
        value = convertInteger((Integer)value,paramTypeString);
      } else if (value instanceof BigDecimal) {
        value = convertBigDecimal((BigDecimal)value,paramTypeString);
      } else if (value instanceof Double) {
        value = convertDouble((Double)value,paramTypeString);
      } else if (value instanceof String) {
        value = convertString((String)value,paramTypeString);
      } else if (value instanceof Bean) {
        value = ((Bean) value).toObject(propType);
      } else if (value instanceof List) {
        Type elType = propType.getComponentType();
        if(null==elType){
          elType = ((ParameterizedType) paramType).getActualTypeArguments()[0];
        }
        List l = (List) value;
        int size = l.size();
        Map<Integer, Object> cache = new HashMap<Integer, Object>(size);
        for (int i = 0, j = l.size(); i < j; i++) {
          Object el = l.get(i);
          if (el instanceof Bean) {
            cache.put(i, ((Bean) el).toObject((Class) elType));
          }
        }
        for (Entry<Integer, Object> entry : cache.entrySet()) {
          l.set(entry.getKey(), entry.getValue());
        }
        if (paramTypeString.startsWith(Set.class.getName())) {
          value = new HashSet(l);
        } else if (paramTypeString.startsWith(Queue.class.getName())) {
          value = new LinkedList(l);
        } else if (paramTypeString.startsWith(TYPE_CLASS_PREFIX)) {
          value = Array.newInstance((Class) elType, size);
          for (int i = 0; i < size; i++) {
            Array.set(value, i, l.get(i));
          }
        }
      }
    }
    return value;
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
      for (PropertyDescriptor p : props) {
        Method setterMethod = p.getWriteMethod();
        if(null==setterMethod){
          continue;
        }
        Class<?> propType = p.getPropertyType();
        Type paramType = setterMethod.getGenericParameterTypes()[0];
        String name = p.getName();
        for (String key : keySet()) {
          if (!key.equalsIgnoreCase(name)) {
            continue;
          }
          setterMethod.invoke(o, convert(get(key),propType,paramType));
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

  @Override
  public String toString(){
    return values.toString();
  }

  /**
   * 获取值
   *
   * 通过路径获取值，例如：{a:{b:{c:1}}}，使用a.b.c可得到1；{a:[{b:1}]}，使用a[0].b可得到1
   *
   * @param path 键的路径，例如：a.b.c或a.b[0].c
   * @return 值
   */
  @SuppressWarnings("unchecked")
  public <T>T select(String path){
    if(null==path){
      return null;
    }
    List<String> paths = StringUtil.list(path,Strings.SLASH_RIGHT+Strings.DOT);
    int depth = paths.size();
    if(depth<1){
      return null;
    }
    String leaf = paths.remove(depth-1);
    Bean node = this;
    for(String p:paths){
      if(p.contains(Strings.BRACKET_OPEN)&&p.contains(Strings.BRACKET_CLOSE)){
        String key = StringUtil.left(p,Strings.BRACKET_OPEN);
        int index = Integer.parseInt(StringUtil.between(p,Strings.BRACKET_OPEN,Strings.BRACKET_CLOSE));
        node = node.getList(key,Bean.class).get(index);
      }else{
        node = node.getList(p,Bean.class).get(0);
      }
    }
    if(leaf.contains(Strings.BRACKET_OPEN)&&leaf.contains(Strings.BRACKET_CLOSE)){
      String key = StringUtil.left(leaf,Strings.BRACKET_OPEN);
      int index = Integer.parseInt(StringUtil.between(leaf,Strings.BRACKET_OPEN,Strings.BRACKET_CLOSE));
      return (T)(node.getList(key).get(index));
    }else{
      return (T)(node.getList(leaf).get(0));
    }
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
   * 获取Bean值
   *
   * @param path 键的路径，例如：a.b.c或a.b[0].c
   * @return Bean，可能为null
   */
  public Bean selectBean(String path){
    return select(path);
  }

  /**
   * 获取Bean值，一般用于链式调用，如果获取失败，返回默认值，不抛出异常
   *
   * @param key 键
   * @param defaultValue 默认值
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
   * 获取Bean值，如果获取失败，返回默认值，不抛出异常
   *
   * @param path 键的路径，例如：a.b.c或a.b[0].c
   * @param defaultValue 默认值
   * @return Bean
   */
  public Bean selectBean(String path,Bean defaultValue){
    Bean v = null;
    try{
      v = selectBean(path);
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
  public short getShort(String key,short defaultValue){
    try{
      return Short.parseShort(String.valueOf(values.get(key)));
    }catch(Exception e){
      return defaultValue;
    }
  }

  /**
   * 根据路径获取short值，如果获取不到或出错，返回默认值，不抛出异常
   *
   * @param path 键的路径，例如：a.b.c或a.b[0].c
   * @param defaultValue 默认值
   * @return 值
   */
  public short selectShort(String path,short defaultValue){
    try{
      return Short.parseShort(String.valueOf(select(path)));
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
   * 根据路径获取int值，如果获取不到或出错，返回默认值，不抛出异常
   *
   * @param path 键的路径，例如：a.b.c或a.b[0].c
   * @param defaultValue 默认值
   * @return 值
   */
  public int selectInt(String path,int defaultValue){
    try{
      return Integer.parseInt(String.valueOf(select(path)));
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
   * 根据路径获取long值，如果获取不到或出错，返回默认值，不抛出异常
   *
   * @param path 键的路径，例如：a.b.c或a.b[0].c
   * @param defaultValue 默认值
   * @return 值
   */
  public long selectLong(String path,long defaultValue){
    try{
      return Long.parseLong(String.valueOf(select(path)));
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
   * 根据路径获取double值，如果获取不到或出错，返回默认值，不抛出异常
   *
   * @param path 键的路径，例如：a.b.c或a.b[0].c
   * @param defaultValue 默认值
   * @return 值
   */
  public double selectDouble(String path,double defaultValue){
    try{
      return Double.parseDouble(String.valueOf(select(path)));
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
   * 根据路径获取float值，如果获取不到或出错，返回默认值，不抛出异常
   *
   * @param path 键的路径，例如：a.b.c或a.b[0].c
   * @param defaultValue 默认值
   * @return 值
   */
  public float selectFloat(String path,float defaultValue){
    try{
      return Float.parseFloat(String.valueOf(select(path)));
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
   * 根据路径获取boolean值，如果获取不到或出错，返回默认值，不抛出异常
   *
   * @param path 键的路径，例如：a.b.c或a.b[0].c
   * @param defaultValue 默认值
   * @return 值
   */
  public boolean selectBoolean(String path,boolean defaultValue){
    try{
      return Boolean.parseBoolean(String.valueOf(select(path)));
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
   * 根据路径获取String值，如果为null,返回null
   *
   * @param path 键的路径，例如：a.b.c或a.b[0].c
   * @return 值
   */
  public String selectString(String path){
    return selectString(path,null);
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
   * 获取String值，如果为null,返回默认值
   *
   * @param path 键的路径，例如：a.b.c或a.b[0].c
   * @param defaultValue 默认值
   * @return 值
   */
  public String selectString(String path,String defaultValue){
    Object o = select(path);
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
    if(null==o){
      return l;
    }
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
   * @param path 键的路径，例如：a.b.c或a.b[0].c
   * @return List
   */
  @SuppressWarnings("unchecked")
  public <T>List<T> selectList(String path){
    List<T> l = new ArrayList<T>();
    Object o = select(path);
    if(null==o){
      return l;
    }
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
    if(null==o){
      return l;
    }
    if(o instanceof Collection){
      l.addAll((Collection<E>)o);
    }else{
      l.add((E)o);
    }
    return l;
  }

  /**
   * 强制获取List，即使是非Collection，也会强制返回只有1个元素的List。如果不存在该键，返回0个元素的List。
   *
   * @param path 键的路径，例如：a.b.c或a.b[0].c
   * @param klass 指定的返回类型
   * @return List
   */
  @SuppressWarnings("unchecked")
  public <E> List<E> selectList(String path,Class<E> klass){
    List<E> l = new ArrayList<E>();
    Object o = select(path);
    if(null==o){
      return l;
    }
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
