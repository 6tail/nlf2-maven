package com.nlf.core;

/**
 * 请求映射
 * @author 6tail
 *
 */
public class RequestMapping{
  protected static final java.util.Map<String,ClassMethod> MAPPING = new java.util.HashMap<String,ClassMethod>();

  public java.util.Set<String> getUriList(){
    return MAPPING.keySet();
  }

  public void add(String uri,ClassMethod cm){
    MAPPING.put(uri,cm);
  }

  /**
   * 根据uri获取对应的类方法，仅仅是获取副本而已
   * @param uri URI
   * @return 类方法
   */
  public ClassMethod get(String uri){
    ClassMethod o = MAPPING.get(uri);
    if(null==o){
      return o;
    }else{
      ClassMethod cm = new ClassMethod();
      cm.setKlass(o.getKlass());
      cm.setMethod(o.getMethod());
      cm.setRet(o.getRet());
      return cm;
    }
  }

  public void remove(String uri){
    java.util.Iterator<java.util.Map.Entry<String,ClassMethod>> it = MAPPING.entrySet().iterator();
    while(it.hasNext()){
      if(com.nlf.util.StringUtil.matches(it.next().getKey(),uri)){
        it.remove();
      }
    }
  }

  public void clear(){
    MAPPING.clear();
  }
}