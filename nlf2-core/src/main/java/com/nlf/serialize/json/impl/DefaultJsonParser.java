package com.nlf.serialize.json.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.nlf.exception.NlfException;
import com.nlf.serialize.AbstractParser;
import com.nlf.serialize.json.exception.JsonFormatException;
import com.nlf.serialize.node.INode;
import com.nlf.serialize.node.impl.NodeBool;
import com.nlf.serialize.node.impl.NodeList;
import com.nlf.serialize.node.impl.NodeMap;
import com.nlf.serialize.node.impl.NodeNumber;
import com.nlf.serialize.node.impl.NodeString;

/**
 * 默认json解析器
 * 
 * @author 6tail
 *
 */
public class DefaultJsonParser extends AbstractParser{
  /** 右斜杠 */
  public static final int RIGHT_SLASH = 92;
  /** 当前字符 */
  private int c;
  /** 位置 */
  @SuppressWarnings("unused")
  private int pos = 0;
  /** 待解析的字符串 */
  private String os;
  /** 字符读取器 */
  private Reader reader;
  
  public boolean support(String format){
    return "json".equalsIgnoreCase(format);
  }

  public INode parseAll(String s){
    os = s;
    if(null==s) return null;
    s = s.trim();
    reader = new StringReader(s);
    return parseNode();
  }

  private INode parseNode(){
    skip();
    switch(c){
      case -1:// 结束
        return null;
      case '{':// 对象开始
        return parseMap();
      case '\'':// 字符串开始
        return parseString();
      case '"':// 字符串开始
        return parseString();
      case '[':// 数组开始
        return parseList();
      default:// 其他，如数字，布尔类型，null
        return parseElse();
    }
  }

  private NodeString parseString(){
    NodeString o = null;
    if('\''==c){// 单引号开始的
      next();// 跳过起始的单引号
      o = new NodeString(readIgnoreSlash('\''));
    }else if('"'==c){ // 双引号开始的
      next();// 跳过起始的双引号
      o = new NodeString(readIgnoreSlash('"'));
    }
    next();// 跳过结束符号
    return o;
  }

  private INode parseElse(){
    INode o = null;
    String s = readUntil(new int[]{' ',',','}',']'});
    s = s.trim();
    if("null".equals(s)){
      o = null;
    }else if("true".equals(s)){
      o = new NodeBool(true);
    }else if("false".equals(s)){
      o = new NodeBool(false);
    }else if(s.endsWith("f")||s.endsWith("F")){
      o = new NodeNumber(new Float(s));
    }else if(s.endsWith("d")||s.endsWith("D")){
      o = new NodeNumber(new Double(s));
    }else if(s.endsWith("l")||s.endsWith("L")){
      o = new NodeNumber(Long.parseLong(s.substring(0,s.length()-1)));
    }else{
      try{
        o = new NodeNumber(new BigDecimal(s));
      }catch(NumberFormatException e){
        o = new NodeString(os);
      }
    }
    return o;
  }

  private String readIgnoreSlash(int endTag){
    StringBuilder s = new StringBuilder();
    List<Integer> slash = new ArrayList<Integer>();
    while(-1!=c){
      if(c==RIGHT_SLASH){
        slash.add(c);
      }else{
        if(endTag==c){
          if(slash.size()%2==0){
            break;
          }
        }
        slash.clear();
      }
      s.append((char)c);
      next();
    }
    return s.toString().replace("\\\\","\\");
  }

  private String readUntil(int[] endTags){
    StringBuilder s = new StringBuilder();
    outer:while(-1!=c){
      for(int t:endTags){
        if(t==c){
          break outer;
        }
      }
      s.append((char)c);
      next();
    }
    return s.toString();
  }

  private void parseMapItem(NodeMap o){
    String key = "";
    skip();// 先跳过无意义的字符
    switch (c){
      case '}':// 如果遇到对象截止符，对象解析完成返回
        return;
      case '\'': // 如果是以单引号开始
        next(); // 跳过起始的单引号
        key = readIgnoreSlash('\''); // 一直读，直到遇到独立的单引号才结束
        next(); // 跳过单引号
        skip(); // 跳过无意义字符
        if(':'!=c){ // 接着应该有个冒号
          throw new JsonFormatException(os);
        }
        next(); // 跳过冒号
        break;
      case '"': // 如果是以双引号开始
        next(); // 跳过起始的双引号
        key = readIgnoreSlash('"'); // 一直读，直到遇到独立的双引号才结束
        next(); // 跳过双引号
        skip(); // 跳过无意义的字符
        if(':'!=c){ // 接着应该有个冒号
          throw new JsonFormatException(os);
        }
        next(); // 跳过冒号
        break;
      default:
        key = readUntil(new int[]{':'}); // 一直读，直到遇到冒号才结束
        key = key.trim();
        next();// 跳过冒号
    }
    INode el = parseNode();
    o.set(key,el);
  }

  private NodeMap parseMap(){
    NodeMap o = new NodeMap();
    next();// 跳过起始符号{
    parseMapItem(o);// 解析对象的第一个属性，如果有的话
    skip();
    while(','==c){// 如果还有兄弟姐妹
      next();// 跳过间隔符号,
      parseMapItem(o);
      skip();
    }
    if('}'!=c){ // 接着应该有个结束符}
      throw new JsonFormatException(os);
    }
    next();// 跳过结束符}
    return o;
  }

  private void parseListItem(NodeList l){
    skip();
    l.add(parseNode());
  }

  private NodeList parseList(){
    NodeList l = new NodeList();
    next();// 跳过起始符号[
    skip();
    if(']'==c){
      next();
      return l;
    }
    parseListItem(l);
    skip();
    while(','==c){// 如果还有兄弟姐妹
      next();// 跳过间隔符号,
      parseListItem(l);
      skip();
    }
    next();// 跳过结束符号]
    return l;
  }

  /**
   * 读取下一个字符
   */
  private void next(){
    try{
      c = reader.read();
      pos++;
    }catch(IOException e){
      throw new NlfException(e);
    }
  }

  /**
   * 跳过无意义字符和注释
   */
  private void skip(){
    if(-1==c) return;
    if(0<=c&&32>=c){ // 忽略0到32之间的
      next();
      skip();
    }
    if(127==c||'\r'==c||'\n'==c){ // 忽略DEL及回车换行
      next();
      skip();
    }
    if('/'==c){
      next();
      if(-1==c) return;
      if('/'==c){ // 忽略单行注释
        do{
          next();
        }while('\r'!=c&&'\n'!=c&&-1!=c);
        skip();
      }else if('*'==c){ // 忽略多行注释
        while(-1!=c){
          next();
          if('*'==c){
            next();
            if('/'==c){
              break;
            }
          }
        }
        skip();
      }
    }
  }
}