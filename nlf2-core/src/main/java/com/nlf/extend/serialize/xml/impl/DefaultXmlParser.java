package com.nlf.extend.serialize.xml.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.nlf.exception.NlfException;
import com.nlf.extend.serialize.xml.exception.XmlFormatException;
import com.nlf.serialize.AbstractParser;
import com.nlf.serialize.node.INode;
import com.nlf.serialize.node.impl.NodeList;
import com.nlf.serialize.node.impl.NodeMap;
import com.nlf.serialize.node.impl.NodeString;
import com.nlf.util.StringUtil;

/**
 * 默认xml解析器
 * 
 * @author 6tail
 *
 */
public class DefaultXmlParser extends AbstractParser{
  
  /** CDATA起始符 */
  public static final String CDATA_PREFIX = "![CDATA[";
  /** CDATA结束符 */
  public static final String CDATA_SUFFIX = "]]";
  /** 注释起始符 */
  public static final String ANNO_PREFIX = "!--";
  /** 注释结束符 */
  public static final String ANNO_SUFFIX = "--";
  
  /** 当前字符 */
  private int c;
  /** 待解析的字符串 */
  private String os;
  /** 字符读取器 */
  private Reader reader;
  /** 节点缓存栈 */
  private List<INode> stack = new ArrayList<INode>();
  
  public boolean support(String format){
    return "xml".equalsIgnoreCase(format);
  }

  public INode parseAll(String s){
    os = s;
    if(null==s) return null;
    s = s.trim();
    s = s.substring(s.indexOf("<"));
    reader = new StringReader(s);
    while(-1!=c){
      parseNode();
    }
    return stack.get(0);
  }

  protected void parseNode(){
    skip();
    switch(c){
      case '<':// 对象开始
        parseXmlNode();
        break;
      default:
        String s = readUntil('<');
        INode p = stack.get(stack.size()-1);
        try{
          ((NodeString)p).setValue(s.replace("&lt;","<").replace("&gt;",">"));
        }catch(Exception e){
          throw new XmlFormatException(s);
        }
        break;
    }
  }
  
  private void parseEndTag(String tag){
    int stackSize = stack.size();
    if(stackSize<2){
      return;
    }
    // 最后一个节点
    INode el = stack.remove(stackSize-1);
    stackSize--;
    INode p = stack.get(stackSize-1);
    switch(p.type()){
      case LIST:
        ((NodeList)p).add(el);
        break;
      case MAP:
        NodeMap map = ((NodeMap)p);
        INode xe = map.get(tag);
        if(null!=xe){
          NodeList xl = new NodeList();
          xl.setName(p.getName());
          xl.setAttributes(p.getAttributes());
          xl.add(xe);
          xl.add(el);
          stack.set(stackSize-1,xl);
        }
        map.set(tag,el);
        break;
      case STRING:
        NodeMap m = new NodeMap();
        m.setName(p.getName());
        m.setAttributes(p.getAttributes());
        m.set(tag,el);
        stack.set(stackSize-1,m);
        break;
      default:
    }
  }

  private void parseXmlNode(){
    next();
    switch(c){
      case '?':
        readUntil('>');
        next();
        break;
      case '!':
        String s = readUntil('>').trim();
        String us = s.toUpperCase();
        StringBuilder value = new StringBuilder();
        if(us.startsWith(CDATA_PREFIX)){// 处理CDATA
          while(!us.endsWith(CDATA_SUFFIX)){
            next();
            if(-1==c){
              throw new XmlFormatException(os);
            }
            us = readUntil('>');
            value.append(">");
            value.append(us);
            us = us.toUpperCase();
          }
          s = value.toString().substring(CDATA_PREFIX.length());
          s = s.substring(0,s.length()-CDATA_SUFFIX.length());
          int stackSize = stack.size();
          if(stackSize>0){
            INode p = stack.get(stackSize-1);
            ((NodeString)p).setValue(s);
          }
        }else if(us.startsWith(ANNO_PREFIX)){// 忽略注释
          while(!us.endsWith(ANNO_SUFFIX)){
            next();
            if(-1==c){
              throw new XmlFormatException(os);
            }
            us = readUntil('>');
            us = us.toUpperCase();
          }
        }
        next();
        break;
      case '/':
        next();
        String tag = readUntil('>');
        next();
        parseEndTag(tag);
        break;
      default:
        String startTag = readUntil('>');
        next();
        boolean isClosed = false;
        startTag = startTag.trim();
        if(startTag.endsWith("/")){
          isClosed = true;
          startTag = startTag.substring(0,startTag.length()-1);
        }
        String nodeName = startTag;
        Map<String,String> attrs = new HashMap<String,String>();
        if(startTag.contains(" ")){
          nodeName = StringUtil.between(startTag,""," ");
          String attr = StringUtil.right(startTag," ");
          while(attr.contains("=\"")){
            String attrName = StringUtil.between(attr,"","=\"").trim();
            attr = StringUtil.right(attr,"=\"");
            String attrValue = StringUtil.left(attr,"\"").trim();
            attr = StringUtil.right(attr,"\"");
            attrs.put(attrName,attrValue);
          }
        }
        NodeString xs = new NodeString(null);
        xs.setName(nodeName);
        xs.setAttributes(attrs);
        stack.add(xs);
        if(isClosed){
          parseEndTag(nodeName);
        }
    }
  }
  
  /**
   * 一直读取，直到遇到指定字符，不包括指定字符
   * 
   * @param endTag 指定字符
   * @return 读取到的字符串
   */
  private String readUntil(int endTag){
    StringBuilder s = new StringBuilder();
    while(-1!=c&&endTag!=c){
      s.append((char)c);
      next();
    }
    return s.toString();
  }
  
  /**
   * 读取下一个字符
   */
  protected void next(){
    try{
      c = reader.read();
    }catch(IOException e){
      throw new NlfException(e);
    }
  }
  
  /**
   * 跳过无意义字符和注释
   */
  protected void skip(){
    if(-1==c) return;
    if(0<=c&&32>=c){ // 忽略0到32之间的
      next();
      skip();
    }
    if(127==c||'\r'==c||'\n'==c){ // 忽略DEL及回车换行
      next();
      skip();
    }
  }

}