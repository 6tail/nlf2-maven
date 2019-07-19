package com.nlf.serialize.node.impl;

import com.nlf.serialize.node.AbstractNode;
import com.nlf.serialize.node.NodeType;

/**
 * 字符串类型节点
 *
 * @author 6tail
 */
public class NodeString extends AbstractNode{
  private static final long serialVersionUID = 1;
  private String o;

  public NodeString(String o){
    setValue(o);
  }

  public void setValue(String o){
    this.o = o;
  }

  @Override
  public String toString(){
    return o;
  }

  public NodeType type(){
    return NodeType.STRING;
  }
}