package com.nlf.serialize;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import com.nlf.Bean;
import com.nlf.serialize.node.INode;
import com.nlf.serialize.node.impl.NodeBool;
import com.nlf.serialize.node.impl.NodeList;
import com.nlf.serialize.node.impl.NodeMap;
import com.nlf.serialize.node.impl.NodeNumber;

/**
 * 抽象解析器
 *
 * @author 6tail
 */
public abstract class AbstractParser implements IParser{

  /**
   * 解析字符串为节点
   * @param s 待解析字符串
   * @return 节点
   */
  public abstract INode parseAll(String s);
  
  public <T>T parse(String s){
    return parse(parseAll(s));
  }
  
  protected Object wrapAttributes(INode n,Object value){
    if(n.getAttributes().size()>0){
      Bean o = new Bean();
      o.set("value",value);
      for(Entry<String,String> en:n.getAttributes().entrySet()){
        o.set("attr."+en.getKey(),en.getValue());
      }
      return o;
    }else{
      return value;
    }
  }

  /**
   * 解析Map类型
   * 
   * @param nm Map节点
   * @return Bean
   */
  protected Bean genMap(NodeMap nm){
    Bean bean = new Bean();
    for(String key:nm.keySet()){
      INode n = nm.get(key);
      if(null==n){
        bean.set(key,null);
        continue;
      }
      switch(n.type()){
        case STRING:
          bean.set(key,wrapAttributes(n,n.toString()));
          break;
        case NUMBER:
          bean.set(key,wrapAttributes(n,((NodeNumber)n).value()));
          break;
        case BOOL:
          bean.set(key,wrapAttributes(n,((NodeBool)n).value()));
          break;
        case MAP:
          bean.set(key,wrapAttributes(n,genMap((NodeMap)n)));
          break;
        case LIST:
          bean.set(key,wrapAttributes(n,genList((NodeList)n)));
          break;
        default:
      }
    }
    return bean;
  }

  /**
   * 解析List类型
   * 
   * @param nl List节点
   * @return Object列表
   */
  protected List<Object> genList(NodeList nl){
    List<Object> l = new ArrayList<Object>();
    for(int i = 0,j = nl.size();i<j;i++){
      INode n = nl.get(i);
      if(null==n){
        l.add(null);
        continue;
      }
      switch(n.type()){
        case STRING:
          l.add(wrapAttributes(n,n.toString()));
          break;
        case NUMBER:
          l.add(wrapAttributes(n,((NodeNumber)n).value()));
          break;
        case BOOL:
          l.add(wrapAttributes(n,((NodeBool)n).value()));
          break;
        case MAP:
          l.add(wrapAttributes(n,genMap((NodeMap)n)));
          break;
        case LIST:
          l.add(wrapAttributes(n,genList((NodeList)n)));
          break;
        default:
      }
    }
    return l;
  }

  @SuppressWarnings("unchecked")
  protected <T>T parse(INode n){
    if(null==n){
      return null;
    }
    switch(n.type()){
      case STRING:
        return (T)(wrapAttributes(n,n.toString()));
      case NUMBER:
        return (T)(wrapAttributes(n,((NodeNumber)n).value()));
      case BOOL:
        return (T)(wrapAttributes(n,((NodeBool)n).value()));
      case MAP:
        return (T)(wrapAttributes(n,genMap((NodeMap)n)));
      case LIST:
        return (T)(wrapAttributes(n,genList((NodeList)n)));
      default:
    }
    return null;
  }
}