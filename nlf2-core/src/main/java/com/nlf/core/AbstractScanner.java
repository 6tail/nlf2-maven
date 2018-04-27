package com.nlf.core;

import java.util.*;

/**
 * 扫描接口抽象
 * 
 * @author 6tail
 *
 */
public abstract class AbstractScanner implements IScanner{
  /** 调用者路径 */
  protected String caller;
  /** 添加的绝对路径 */
  protected Set<String> addedAbsolutePaths = new HashSet<String>();
  /** 添加的相对路径 */
  protected Set<String> addedRelativePaths = new HashSet<String>();
  /** 忽略的路径 */
  protected Set<String> ignoredPaths = new HashSet<String>();
  /** 优先允许的路径 */
  protected Set<String> allowPaths = new HashSet<String>();
  /** 被忽略jar的manifest属性 */
  protected Map<String,Set<String>> ignoredManifestAttributes = new HashMap<String,Set<String>>();
  /** 优先允许jar的manifest属性 */
  protected Map<String,Set<String>> allowManifestAttributes = new HashMap<String,Set<String>>();

  public IScanner setCaller(String caller){
    this.caller = caller;
    return this;
  }

  public IScanner addAbsolutePath(String... path){
    Collections.addAll(addedAbsolutePaths,path);
    return this;
  }

  public IScanner addRelativePath(String... path){
    Collections.addAll(addedRelativePaths,path);
    return this;
  }

  public IScanner ignore(String... path){
    Collections.addAll(ignoredPaths,path);
    return this;
  }

  public IScanner allow(String... path){
    Collections.addAll(allowPaths,path);
    return this;
  }

  public IScanner ignoreJarByManifestAttribute(String key,String... value){
    Set<String> values = ignoredManifestAttributes.get(key);
    if(null==values){
      values = new HashSet<String>();
      ignoredManifestAttributes.put(key,values);
    }
    Collections.addAll(values,value);
    return this;
  }

  public IScanner allowJarByManifestAttribute(String key,String... value){
    Set<String> values = allowManifestAttributes.get(key);
    if(null==values){
      values = new HashSet<String>();
      allowManifestAttributes.put(key,values);
    }
    Collections.addAll(values,value);
    return this;
  }
}