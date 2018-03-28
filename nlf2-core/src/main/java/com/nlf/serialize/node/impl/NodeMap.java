package com.nlf.serialize.node.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import com.nlf.serialize.node.AbstractNode;
import com.nlf.serialize.node.INode;
import com.nlf.serialize.node.NodeType;

/**
 * Map对象类型节点
 * 
 * @author 6tail
 * 
 */
public class NodeMap extends AbstractNode{
  private static final long serialVersionUID = 1;
  private Map<String,INode> o = new HashMap<String,INode>();

  public INode get(String key){
    return o.get(key);
  }

  public void set(String key,INode value){
    o.put(key,value);
  }

  public Set<String> keySet(){
    return o.keySet();
  }

  public NodeType type(){
    return NodeType.MAP;
  }

  public String toString(){
    return o.toString();
  }
}