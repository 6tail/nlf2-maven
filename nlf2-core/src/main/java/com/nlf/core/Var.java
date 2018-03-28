package com.nlf.core;

/**
 * 上下文变量
 * 
 * @author liat6
 * 
 */
public final class Var{

  /** 值 */
  private java.util.Map<Object,Object> value = new java.util.HashMap<Object,Object>();

  /**
   * 设置
   * 
   * @param k 键
   * @param v 值
   */
  public void set(Object k,Object v){
    value.put(k,v);
  }

  /**
   * 获取
   * 
   * @param k 键
   * @return 值
   */
  public Object get(Object k){
    return value.get(k);
  }

  /**
   * 移除
   * 
   * @param k 键
   */
  public void remove(Object k){
    value.remove(k);
  }

  /**
   * 清空
   */
  public void clear(){
    value.clear();
  }

  /**
   * 是否存在
   * 
   * @param k 键
   * @return true/false 是/否
   */
  public boolean contains(Object k){
    return value.containsKey(k);
  }
}