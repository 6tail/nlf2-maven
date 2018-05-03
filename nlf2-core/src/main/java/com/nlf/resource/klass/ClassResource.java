package com.nlf.resource.klass;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.nlf.bytecode.Method;
import com.nlf.resource.Resource;
import com.nlf.resource.ResourceType;

/**
 * 资源：类
 * 
 * @author 6tail
 * 
 */
public class ClassResource extends Resource{
  /** 是否interface */
  private boolean interfaceClass;
  /** 是否abstract */
  private boolean abstractClass;
  /** 类名 */
  private String className;
  /** 实现的接口 */
  private Set<String> interfaces = new HashSet<String>();
  /** 方法列表 */
  private List<Method> methods = new ArrayList<Method>();

  public ClassResource(){
    type = ResourceType.klass;
  }

  public boolean isInterfaceClass(){
    return interfaceClass;
  }

  public void setInterfaceClass(boolean interfaceClass){
    this.interfaceClass = interfaceClass;
  }

  public String getClassName(){
    return className;
  }

  public void setClassName(String className){
    this.className = className;
  }

  public Set<String> getInterfaces(){
    return interfaces;
  }

  public void setInterfaces(Set<String> interfaces){
    this.interfaces = interfaces;
  }

  public boolean isAbstractClass(){
    return abstractClass;
  }

  public void setAbstractClass(boolean abstractClass){
    this.abstractClass = abstractClass;
  }

  public List<Method> getMethods(){
    return methods;
  }

  public void setMethods(List<Method> methods){
    this.methods = methods;
  }

  public void addMethod(Method method){
    methods.add(method);
  }

  public String toString(){
    return super.toString()+" className="+className+" interface="+interfaceClass+" abstract="+abstractClass;
  }
}