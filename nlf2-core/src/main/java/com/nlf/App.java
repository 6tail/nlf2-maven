package com.nlf;

import java.util.*;

import com.nlf.core.IProxy;
import com.nlf.core.IRequest;
import com.nlf.core.IResponse;
import com.nlf.core.ScannerFactory;
import com.nlf.core.Statics;
import com.nlf.core.Var;
import com.nlf.core.impl.DefaultProxy;
import com.nlf.resource.i18n.I18nResource;
import com.nlf.resource.klass.ClassResource;

/**
 * 应用信息
 *
 * @author 6tail
 *
 */
public class App{
  /** 应用根目录 */
  public static String root;
  /** 调用者所在路径，可能是目录，也可能是jar文件 */
  public static String caller;
  /** 框架所在路径，可能是目录，也可能是jar文件 */
  public static String frame;
  /** 扫描到的目录 */
  public static final Set<String> DIRECTORIES = new LinkedHashSet<String>();
  /** i18n文件名缓存 */
  public static final Set<String> I18N = new LinkedHashSet<String>();
  /** 框架包名 */
  public static final String PACKAGE = App.class.getPackage().getName();
  /** 所有扫描到的i18n缓存 */
  public static final List<I18nResource> I18N_RESOURCE = new ArrayList<I18nResource>();
  /** 所有扫描到的类缓存 */
  public static final Map<String,ClassResource> CLASS = new HashMap<String,ClassResource>();
  /** 所有扫描到的接口实现类列表缓存 */
  public static final Map<String,List<String>> INTERFACE_IMPLEMENTS = new HashMap<String,List<String>>();
  /** i18n值缓存 */
  protected static final Map<String,Map<Locale,String>> I18N_CACHE = new HashMap<String,Map<Locale,String>>();
  /** 代理 */
  private static volatile IProxy proxy;
  /** 上下文变量 */
  private static final ThreadLocal<Var> VAR = new ThreadLocal<Var>(){
    @Override
    public Var initialValue(){
      return new Var();
    }
  };

  static{
    ScannerFactory.startScan();
  }

  protected App(){}

  /**
   * 在任何地方获取输入请求
   *
   * @return 输入请求
   */
  public static IRequest getRequest(){
    return get(Statics.REQUEST);
  }

  /**
   * 在任何地方获取输出接口
   *
   * @return 输出接口
   */
  public static IResponse getResponse(){
    return get(Statics.RESPONSE);
  }

  /**
   * 设置线程变量
   * @param key 键
   * @param value 变量值
   */
  public static void set(String key,Object value){
    VAR.get().set(key,value);
  }

  /**
   * 获取线程变量
   * @param key 键
   * @return 变量值
   */
  @SuppressWarnings("unchecked")
  public static <T>T get(String key){
    return (T)VAR.get().get(key);
  }

  /**
   * 获取类或接口的实现类列表的交集（如果是接口，则它的所有实现类参与交集；如果不是接口，它自身参与交集）。
   *
   * @param interfaceOrClassNames 完整类或接口名
   * @return 实现类列表
   */
  public static List<String> getImplements(String... interfaceOrClassNames){
    List<String> l = null;
    for(String name:interfaceOrClassNames){
      ClassResource r = CLASS.get(name);
      if(null==r){
        return new ArrayList<String>();
      }
      List<String> sub = new ArrayList<String>();
      if(r.isInterfaceClass()){
        List<String> impls = INTERFACE_IMPLEMENTS.get(name);
        if(null!=impls){
          sub.addAll(impls);
        }
      }else{
        sub.add(name);
      }
      if(null==l){
        l = sub;
      }else{
        l.retainAll(sub);
      }
    }
    return l;
  }

  /**
   * 获取类或接口的实现类列表的交集（如果是接口，则它的所有实现类参与交集；如果不是接口，它自身参与交集）。
   *
   * @param interfaceOrClasses 类或接口们
   * @return 实现类列表
   */
  public static List<String> getImplements(Class<?>... interfaceOrClasses){
    List<String> l = new ArrayList<String>();
    for(Class<?> c:interfaceOrClasses){
      l.add(c.getName());
    }
    String[] arr = new String[l.size()];
    l.toArray(arr);
    return getImplements(arr);
  }

  /**
   * 从类或接口的实现类列表的交集（如果是接口，则它的所有实现类参与交集；如果不是接口，它自身参与交集）中取得一个默认实现类，默认实现类的挑选规则由扫描器指定。
   *
   * @param interfaceOrClassNames 完整类或接口名
   * @return 默认实现类
   */
  public static String getImplement(String... interfaceOrClassNames){
    List<String> impls = getImplements(interfaceOrClassNames);
    return impls.size()<1?null:impls.get(0);
  }

  /**
   * 从类或接口的实现类列表的交集（如果是接口，则它的所有实现类参与交集；如果不是接口，它自身参与交集）中取得一个默认实现类，默认实现类的挑选规则由扫描器指定。
   *
   * @param interfaceOrClasses 类或接口们
   * @return 默认实现类
   */
  public static String getImplement(Class<?>... interfaceOrClasses){
    List<String> impls = getImplements(interfaceOrClasses);
    return impls.size()<1?null:impls.get(0);
  }

  /**
   * 获取代理接口，建议所有调用入口都通过代理，便于扩展AOP等功能。
   *
   * @return 代理接口
   */
  public static IProxy getProxy(){
    if(null==proxy){
      synchronized(DefaultProxy.class){
        if(null==proxy){
          proxy = new DefaultProxy().newInstance(IProxy.class.getName());
        }
      }
    }
    return proxy;
  }

  /**
   * 获取properties值，默认使用请求客户端的locale，如果未设置则使用默认locale
   * @param key key
   * @param params 传入参数
   * @return 值
   */
  public static String getProperty(String key,Object... params){
    IRequest r = getRequest();
    Locale locale = null==r?Locale.getDefault():r.getClient().getLocale();
    return getProperty(locale,key,params);
  }

  /**
   * 获取properties值
   * @param locale locale
   * @param key key
   * @param params 传入参数
   * @return 值
   */
  public static String getProperty(Locale locale,String key,Object... params){
    String value = null;
    Map<Locale,String> values = I18N_CACHE.get(key);
    if(null==values){
      values = new HashMap<Locale,String>(2);
      I18N_CACHE.put(key,values);
    }
    if(!values.containsKey(locale)){
      for(String i18n:I18N){
        try{
          ResourceBundle rb = ResourceBundle.getBundle(i18n,locale);
          value = rb.getString(key);
          break;
        }catch(Exception e){}
      }
      values.put(locale,value);
    }else{
      value = values.get(locale);
    }
    return null==value?null:params.length>0?java.text.MessageFormat.format(value,params):value;
  }

  /**
   * 获取properties的short值，如果获取不到或出错，返回默认值，不抛出异常
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public static short getPropertyShort(String key,short defaultValue){
    try{
      return Short.parseShort(getProperty(key));
    }catch(Exception e){
      return defaultValue;
    }
  }

  /**
   * 获取properties的int值，如果获取不到或出错，返回默认值，不抛出异常
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public static int getPropertyInt(String key,int defaultValue){
    try{
      return Integer.parseInt(getProperty(key));
    }catch(Exception e){
      return defaultValue;
    }
  }

  /**
   * 获取properties的long值，如果获取不到或出错，返回默认值，不抛出异常
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public static long getPropertyLong(String key,long defaultValue){
    try{
      return Long.parseLong(getProperty(key));
    }catch(Exception e){
      return defaultValue;
    }
  }

  /**
   * 获取properties的float值，如果获取不到或出错，返回默认值，不抛出异常
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public static float getPropertyFloat(String key,float defaultValue){
    try{
      return Float.parseFloat(getProperty(key));
    }catch(Exception e){
      return defaultValue;
    }
  }

  /**
   * 获取properties的double值，如果获取不到或出错，返回默认值，不抛出异常
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public static double getPropertyDouble(String key,double defaultValue){
    try{
      return Double.parseDouble(getProperty(key));
    }catch(Exception e){
      return defaultValue;
    }
  }

  /**
   * 获取properties的boolean值，如果获取不到或出错，返回默认值，不抛出异常
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public static boolean getPropertyBoolean(String key,boolean defaultValue){
    try{
      String value = getProperty(key);
      return null == value ? defaultValue : Boolean.parseBoolean(value);
    }catch(Exception e){
      return defaultValue;
    }
  }

  /**
   * 获取properties的字符串值，如果获取不到或出错，返回默认值，不抛出异常
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public static String getPropertyString(String key,String defaultValue){
    try{
      String ret = getProperty(key);
      return null==ret?defaultValue:ret;
    }catch(Exception e){
      return defaultValue;
    }
  }

  /**
   * 获取properties的字符串值，如果获取不到或出错，返回默认值，不抛出异常
   * @param locale locale
   * @param key 键
   * @param defaultValue 默认值
   * @return 值
   */
  public static String getPropertyString(Locale locale,String key,String defaultValue){
    try{
      String ret = getProperty(locale,key);
      return null==ret?defaultValue:ret;
    }catch(Exception e){
      return defaultValue;
    }
  }
}
