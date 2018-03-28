package com.nlf.serialize.node.impl;

import com.nlf.serialize.node.AbstractNode;
import com.nlf.serialize.node.NodeType;

/**
 * 布尔类型节点
 * 
 * @author 6tail
 * 
 */
public class NodeBool extends AbstractNode{
  private static final long serialVersionUID = 1;
  private boolean o;

  public NodeBool(boolean o){
    this.o = o;
  }

  public String toString(){
    return o?"true":"false";
  }

  public NodeType type(){
    return NodeType.BOOL;
  }

  public boolean value(){
    return o;
  }
}