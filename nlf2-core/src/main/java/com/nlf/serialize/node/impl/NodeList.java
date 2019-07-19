package com.nlf.serialize.node.impl;

import java.util.ArrayList;
import java.util.List;
import com.nlf.serialize.node.AbstractNode;
import com.nlf.serialize.node.INode;
import com.nlf.serialize.node.NodeType;

/**
 * List类型节点
 * 
 * @author 6tail
 * 
 */
public class NodeList extends AbstractNode{
  private static final long serialVersionUID = 1;
  private List<INode> l = new ArrayList<INode>();

  public int size(){
    return l.size();
  }

  public INode get(int index){
    return l.get(index);
  }

  public NodeType type(){
    return NodeType.LIST;
  }

  public void add(INode o){
    l.add(o);
  }

  @Override
  public String toString(){
    return l.toString();
  }
}