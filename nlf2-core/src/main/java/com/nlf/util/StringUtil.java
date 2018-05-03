package com.nlf.util;

import java.util.*;

/**
 * 字符串处理工具
 * 
 * @author 6tail
 *
 */
public class StringUtil{

  /** 通配符匹配时需替换的正则关键字 */
  public static final Set<Character> REG_KEYS = new HashSet<Character>(Arrays.asList('$','(',')','+','.','[',']','\\','^','{','}','|'));
  
  /**
   * 字符串分隔为列表
   * @param s 字符串
   * @param separator 分隔符
   * @return 列表
   */
  public static List<String> list(String s,String separator){
    String[] arr = s.split(separator,-1);
    List<String> l = new ArrayList<String>(arr.length);
    Collections.addAll(l,arr);
    if(!s.startsWith(separator)){
      if(l.size()>0){
        if(l.get(0).length()<1){
          l.remove(0);
        }
      }
    }
    if(!s.endsWith(separator)){
      int offset = l.size();
      if(offset>0){
        offset--;
        if(l.get(offset).length()<1){
          l.remove(offset);
        }
      }
    }
    return l;
  }

  /**
   * 字符串分隔为字符串数组
   * @param s 字符串
   * @param separator 分隔符
   * @return 字符串数组
   */
  public static String[] array(String s,String separator){
    List<String> l = list(s,separator);
    int size = l.size();
    String[] arr = new String[size];
    for(int i=0;i<size;i++){
      arr[i] = l.get(i);
    }
    return arr;
  }

  /**
   * 将字符串数组转换为字符串
   * 
   * @param array 字符串数组
   * @param separator 分隔符
   * @return 字符串
   */
  public static String join(String[] array,String separator){
    if(null==array) return "";
    int len = array.length;
    if(len<1) return "";
    StringBuilder sb = new StringBuilder();
    for(int i=0;i<len;i++){
      if(i>0){
        sb.append(separator);
      }
      sb.append(array[i]);
    }
    return sb+"";
  }

  /**
   * 将集合转为字符串
   * 
   * @param l 集合
   * @param separator 分隔符
   * @return 字符串
   */
  public static String join(Collection<String> l,String separator){
    if(null==l) return "";
    if(l.isEmpty()) return "";
    StringBuilder sb = new StringBuilder();
    int i = 0;
    for(String s:l){
      if(i++>0){
        sb.append(separator);
      }
      sb.append(s);
    }
    return sb+"";
  }

  /**
   * 裁剪，获取中间字符串，如果找不到起始字符串或找不到结束字符串，返回空字符串
   * 
   * @param s 原始字符串
   * @param start 起始字符串
   * @param end 从起始字符串开始第一次遇到的结束字符串
   * @return 从起始字符串到结束字符串之间的字符串，不包括起始字符串和结束字符串
   */
  public static String between(String s,String start,String end){
    int idx = s.indexOf(start);
    if(idx>-1){
      String k = s.substring(idx+start.length());
      idx = k.indexOf(end);
      return idx>0?k.substring(0,idx):"";
    }
    return "";
  }

  /**
   * 裁剪，获取右侧字符串，如果找不到起始字符串，返回空字符串
   * 
   * @param s 原始字符串
   * @param start 起始字符串
   * @return 从起始字符串开始的字符串，不包括起始字符串
   */
  public static String right(String s,String start){
    int idx = s.indexOf(start);
    return idx>-1?s.substring(idx+start.length()):"";
  }

  /**
   * 裁剪，获取左侧字符串，如果找不到结束字符串，返回空字符串
   * 
   * @param s 原始字符串
   * @param end 结束字符串
   * @return 从左侧开始，直到遇到结束字符串，不包括结束字符串
   */
  public static String left(String s,String end){
    int idx = s.indexOf(end);
    return idx>0?s.substring(0,idx):"";
  }

  /**
   * 通配符匹配检测，支持*和?匹配，*匹配任意数量的字符，?匹配单个字符
   * 
   * @param s 待匹配字符串
   * @param expression 表达式
   * @return true/false
   */
  public static boolean matches(String s,String expression){
    if(null==s||null==expression) return false;
    int len = expression.length();
    StringBuilder sb = new StringBuilder();
    for(int i = 0;i<len;i++){
      char c = expression.charAt(i);
      if('?'==c){
        sb.append(".{1}");
      }else{
        if('*'==c){
          sb.append(".");
        }else if(REG_KEYS.contains(c)){
          sb.append('\\');
        }
        sb.append(c);
      }
    }
    return s.matches(sb.toString());
  }
}