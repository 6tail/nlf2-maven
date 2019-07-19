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
import com.nlf.util.Chars;
import com.nlf.util.DataTypes;
import com.nlf.util.Strings;

/**
 * 默认json解析器
 * 
 * @author 6tail
 *
 */
public class DefaultJsonParser extends AbstractParser{
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
    return Strings.JSON.equalsIgnoreCase(format);
  }

  @Override
  public INode parseAll(String s){
    os = s;
    if(null==s){
      return null;
    }
    s = s.trim();
    reader = new StringReader(s);
    return parseNode();
  }

  private INode parseNode(){
    skip();
    switch(c){
      // 结束
      case Chars.END:
        return null;
      // 对象开始
      case Chars.BRACE_OPEN:
        return parseMap();
      // 字符串开始
      case Chars.QUOTE_SINGLE:
      case Chars.QUOTE_DOUBLE:
        return parseString();
      // 数组开始
      case Chars.BRACKET_OPEN:
        return parseList();
      // 其他，如数字，布尔类型，null
      default:
        return parseElse();
    }
  }

  private NodeString parseString(){
    int tag = c;
    // 跳过起始的引号
    next();
    NodeString o = new NodeString(readIgnoreSlash(tag));
    // 跳过结束符号
    next();
    return o;
  }

  private INode parseElse(){
    INode o = null;
    String s = readUntil(new int[]{Chars.SPACE,Chars.COMMA,Chars.BRACE_CLOSE,Chars.BRACKET_CLOSE});
    s = s.trim();
    if(DataTypes.NULL.equals(s)){
      o = null;
    }else if(DataTypes.TRUE.equals(s)){
      o = new NodeBool(true);
    }else if(DataTypes.FALSE.equals(s)){
      o = new NodeBool(false);
    }else if(s.endsWith(DataTypes.FLOAT_SUFFIX_UPPER)||s.endsWith(DataTypes.FLOAT_SUFFIX_LOWER)){
      o = new NodeNumber(new Float(s));
    }else if(s.endsWith(DataTypes.DOUBLE_SUFFIX_UPPER)||s.endsWith(DataTypes.DOUBLE_SUFFIX_LOWER)){
      o = new NodeNumber(new Double(s));
    }else if(s.endsWith(DataTypes.LONG_SUFFIX_UPPER)||s.endsWith(DataTypes.LONG_SUFFIX_LOWER)){
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
    while(Chars.END!=c){
      if(c==Chars.SLASH_RIGHT){
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
    outer:while(Chars.END!=c){
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
    // 先跳过无意义的字符
    skip();
    switch (c){
      // 如果遇到对象截止符，对象解析完成返回
      case Chars.BRACE_CLOSE:
        return;
      // 如果是以引号开始
      case Chars.QUOTE_SINGLE:
      case Chars.QUOTE_DOUBLE:
        int tag = c;
        // 跳过起始的引号
        next();
        // 一直读，直到遇到独立的引号才结束
        key = readIgnoreSlash(tag);
        // 跳过引号
        next();
        // 跳过无意义字符
        skip();
        // 接着应该有个冒号
        if(Chars.COLON!=c){
          throw new JsonFormatException(os);
        }
        // 跳过冒号
        next();
        break;
      default:
        // 一直读，直到遇到冒号才结束
        key = readUntil(new int[]{Chars.COLON});
        key = key.trim();
        // 跳过冒号
        next();
    }
    INode el = parseNode();
    o.set(key,el);
  }

  private NodeMap parseMap(){
    NodeMap o = new NodeMap();
    // 跳过起始符号{
    next();
    // 解析对象的第一个属性，如果有的话
    parseMapItem(o);
    skip();
    // 如果还有兄弟姐妹
    while(Chars.COMMA==c){
      // 跳过间隔符号,
      next();
      parseMapItem(o);
      skip();
    }
    // 接着应该有个结束符}
    if(Chars.BRACE_CLOSE!=c){
      throw new JsonFormatException(os);
    }
    // 跳过结束符}
    next();
    return o;
  }

  private void parseListItem(NodeList l){
    skip();
    l.add(parseNode());
  }

  private NodeList parseList(){
    NodeList l = new NodeList();
    // 跳过起始符号[
    next();
    skip();
    if(Chars.BRACKET_CLOSE==c){
      next();
      return l;
    }
    parseListItem(l);
    skip();
    // 如果还有兄弟姐妹
    while(Chars.COMMA==c){
      // 跳过间隔符号,
      next();
      parseListItem(l);
      skip();
    }
    // 跳过结束符号]
    next();
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
    if(Chars.END==c){
      return;
    }
    // 忽略0到32之间的
    if(Chars.NUT<=c&&Chars.SPACE>=c){
      next();
      skip();
    }
    // 忽略DEL及回车换行
    if(Chars.DEL==c||Chars.CR==c||Chars.LF==c){
      next();
      skip();
    }
    if(Chars.SLASH_LEFT==c){
      next();
      if(Chars.END==c){
        return;
      }
      // 忽略单行注释
      if(Chars.SLASH_LEFT==c){
        do{
          next();
        }while(Chars.CR!=c&&Chars.LF!=c&&Chars.END!=c);
        skip();
      }else if(Chars.STAR==c){
        // 忽略多行注释
        while(Chars.END!=c){
          next();
          if(Chars.STAR==c){
            next();
            if(Chars.SLASH_LEFT==c){
              break;
            }
          }
        }
        skip();
      }
    }
  }
}