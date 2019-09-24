package com.nlf.extend.serialize.xml.impl;

import com.nlf.exception.NlfException;
import com.nlf.extend.serialize.xml.exception.XmlFormatException;
import com.nlf.serialize.AbstractParser;
import com.nlf.serialize.node.INode;
import com.nlf.serialize.node.impl.NodeList;
import com.nlf.serialize.node.impl.NodeMap;
import com.nlf.serialize.node.impl.NodeString;
import com.nlf.util.Chars;
import com.nlf.util.StringUtil;
import com.nlf.util.Strings;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 默认xml解析器
 *
 * @author 6tail
 *
 */
public class DefaultXmlParser extends AbstractParser{
  private static final int MIN_STACK_SIZE = 2;
  /** CDATA起始符 */
  public static final String CDATA_PREFIX = "![CDATA[";
  /** CDATA结束符 */
  public static final String CDATA_SUFFIX = "]]";
  /** 注释结束符 */
  public static final String ANNO_SUFFIX = "--";
  /** 注释起始符 */
  public static final String ANNO_PREFIX = "!-";
  private static final String EQ_QUOTE_DOUBLE = "=\"";

  /** 当前字符 */
  private int c;
  /** 待解析的字符串 */
  private String os;
  /** 字符读取器 */
  private Reader reader;
  /** 节点缓存栈 */
  private List<INode> stack = new ArrayList<INode>();

  public boolean support(String format){
    return Strings.XML.equalsIgnoreCase(format);
  }

  @Override
  public INode parseAll(String s){
    os = s;
    if(null==s){
      return null;
    }
    s = s.trim();
    s = s.substring(s.indexOf(Strings.LT));
    reader = new StringReader(s);
    while(Chars.END!=c){
      parseNode();
    }
    return stack.get(0);
  }

  protected void parseNode(){
    skip();
    switch(c){
      case Chars.LT:
        parseXmlNode();
        break;
      default:
        String s = readUntil(Chars.LT);
        INode p = stack.get(stack.size()-1);
        try{
          ((NodeString)p).setValue(s.replace(Strings.LT_ENTITY_NAME,Strings.LT).replace(Strings.GT_ENTITY_NAME,Strings.GT));
        }catch(Exception e){
          throw new XmlFormatException(s);
        }
        break;
    }
  }

  private void parseEndTag(String tag){
    int stackSize = stack.size();
    if(stackSize<MIN_STACK_SIZE){
      return;
    }
    // 最后一个节点
    INode el = stack.remove(stackSize-1);
    stackSize--;
    INode p = stack.get(stackSize-1);
    switch(p.getType()){
      case LIST:
        ((NodeList)p).add(el);
        break;
      case MAP:
        NodeMap map = ((NodeMap)p);
        INode xe = map.get(tag);
        if(null!=xe){
          if(xe instanceof NodeList){
            NodeList list = (NodeList)xe;
            list.add(el);
          }else{
            NodeList list = new NodeList();
            list.setName(tag);
            list.add(xe);
            list.add(el);
            map.set(tag, list);
          }
        }else {
          map.set(tag, el);
        }
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
      case Chars.QUESTION:
        readUntil(Chars.GT);
        next();
        break;
      case Chars.EXCLAMATION:
        String s = readUntil(Chars.GT).trim();
        String us = s.toUpperCase();
        StringBuilder value = new StringBuilder();
        // 处理CDATA
        if(us.startsWith(CDATA_PREFIX)){
          while(!us.endsWith(CDATA_SUFFIX)){
            next();
            if(Chars.END==c){
              throw new XmlFormatException(os);
            }
            us = readUntil(Chars.GT);
            value.append(Strings.GT);
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
        }else if(us.startsWith(ANNO_PREFIX)){
          // 忽略注释
          while(!us.endsWith(ANNO_SUFFIX)){
            next();
            if(Chars.END==c){
              throw new XmlFormatException(os);
            }
            us = readUntil(Chars.GT);
            us = us.toUpperCase();
          }
        }
        next();
        break;
      case Chars.SLASH_LEFT:
        next();
        String tag = readUntil(Chars.GT);
        next();
        parseEndTag(tag);
        break;
      default:
        String startTag = readUntil(Chars.GT);
        next();
        boolean isClosed = false;
        startTag = startTag.trim();
        if(startTag.endsWith(Strings.SLASH_LEFT)){
          isClosed = true;
          startTag = startTag.substring(0,startTag.length()-1);
        }
        String nodeName = startTag;
        Map<String,String> attrs = new HashMap<String,String>(16);
        if(startTag.contains(Strings.SPACE)){
          nodeName = StringUtil.between(startTag,Strings.EMPTY,Strings.SPACE);
          String attr = StringUtil.right(startTag,Strings.SPACE);
          while(attr.contains(EQ_QUOTE_DOUBLE)){
            String attrName = StringUtil.between(attr,Strings.EMPTY,EQ_QUOTE_DOUBLE).trim();
            attr = StringUtil.right(attr,EQ_QUOTE_DOUBLE);
            String attrValue = StringUtil.left(attr,Strings.QUOTE_DOUBLE).trim();
            attr = StringUtil.right(attr,Strings.QUOTE_DOUBLE);
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
    while(Chars.END!=c&&endTag!=c){
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
  }

}
