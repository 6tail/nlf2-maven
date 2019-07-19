package com.nlf.serialize.node;

import java.util.HashMap;
import java.util.Map;

/**
 * 抽象节点
 *
 * @author 6tail
 */
public abstract class AbstractNode implements INode{
  private static final long serialVersionUID = 1;
  protected String name;
  protected Map<String,String> attributes = new HashMap<String,String>();

  public String getName(){
    return name;
  }

  public void setName(String name){
    this.name = name;
  }

  public Map<String,String> getAttributes(){
    return attributes;
  }

  public void setAttributes(Map<String,String> attributes){
    this.attributes = attributes;
  }
}