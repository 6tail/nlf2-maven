package com.nlf.serialize.node.impl;

import com.nlf.serialize.node.AbstractNode;
import com.nlf.serialize.node.NodeType;

/**
 * 数字类型节点
 * 
 * @author 6tail
 * 
 */
public class NodeNumber extends AbstractNode{
  private static final long serialVersionUID = 1;
  private Number n;

  public NodeNumber(Number n){
    this.n = n;
  }

  public NodeType type(){
    return NodeType.NUMBER;
  }

  @Override
  public String toString(){
    return n+"";
  }

  public Number value(){
    return n;
  }
}