package com.nlf.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 字节数组
 *
 * @author 6tail
 *
 */
public class ByteArray{

  /** 字节缓存 */
  private List<Byte> l = new ArrayList<Byte>();

  public ByteArray(){}

  public ByteArray(byte[] d){
    append(d);
  }

  public ByteArray(List<Byte> d){
    append(d);
  }

  /**
   * 从头部移除
   * @param n 移除字节数量
   * @return 被移除的字节数组，按原顺序排列
   */
  public byte[] removeHead(int n){
    byte[] d = new byte[n];
    for(int i=0;i<n;i++){
      d[i] = l.remove(0);
    }
    return d;
  }

  /**
   * 从尾部移除
   * @param n 移除字节数量
   * @return 被移除的字节数组，按原顺序排列
   */
  public byte[] removeTail(int n){
    byte[] d = new byte[n];
    for(int i=0;i<n;i++){
      d[n-i-1] = l.remove(size()-1);
    }
    return d;
  }

  /**
   * 获取头部数据
   * @param n 数量
   * @return
   */
  public ByteArray getHead(int n) {
    return sub(0,n);
  }

  /**
   * 获取头部字节
   * @param n 数量
   * @return
   */
  public byte[] getHeads(int n) {
    return getHead(n).toArray();
  }

  /**
   * 获取尾部数据
   * @param n 数量
   * @return
   */
  public ByteArray getTail(int n) {
    int l = size();
    return sub(l-n,l);
  }

  /**
   * 获取尾部字节
   * @param n 数量
   * @return
   */
  public byte[] getTails(int n) {
    return getTail(n).toArray();
  }

  /**
   * 清空
   */
  public ByteArray clear(){
    l.clear();
    return this;
  }

  /**
   * 设置
   *
   * @param index 下标
   * @param b 字节
   */
  public ByteArray set(int index,byte b){
    l.set(index,b);
    return this;
  }

  /**
   * 总大小
   *
   * @return 总大小
   */
  public int size(){
    return l.size();
  }

  /**
   * 获取指定下标的字节
   *
   * @param index 下标
   * @return 字节
   */
  public byte get(int index){
    return l.get(index);
  }

  /**
   * 截取
   * @param fromIndex 开始下标（包含）
   * @param toIndex 截止下标（不包含）
   * @return
   */
  public ByteArray sub(int fromIndex,int toIndex){
    return new ByteArray(l.subList(fromIndex,toIndex));
  }

  /**
   * 在末尾添加字节
   *
   * @param b 字节
   */
  public ByteArray append(byte b){
    l.add(b);
    return this;
  }

  /**
   * 在末尾添加字节数组
   *
   * @param data 字节数组
   */
  public ByteArray append(byte[] data){
    for(byte b:data){
      append(b);
    }
    return this;
  }

  /**
   * 在末尾添加字节数组
   *
   * @param data 字节数组
   */
  public ByteArray append(ByteArray data){
    int size = data.size();
    for(int i=0;i<size;i++) {
      append(data.get(i));
    }
    return this;
  }

  /**
   * 在末尾添加字节列表
   *
   * @param data 字节列表
   */
  public ByteArray append(List<Byte> data){
    l.addAll(data);
    return this;
  }

  /**
   * 转换为数组
   *
   * @return 字节数组
   */
  public byte[] toArray(){
    int n = size();
    byte[] b = new byte[n];
    for(int i = 0;i<n;i++){
      b[i] = l.get(i).byteValue();
    }
    return b;
  }

  /**
   * byte[]出现的下标，如果不存在，返回-1
   * @param bytes byte[]
   * @return
   */
  public int indexOf(byte[] bytes){
    int m = bytes.length;
    int index = 0;
    for(int i = 0,n=size();i<n;i++){
      byte b = l.get(i);
      if(b!=bytes[index]){
        index = 0;
      }
      if(b==bytes[index]){
        index++;
        if(index>=m){
          return i-index+1;
        }
      }
    }
    return -1;
  }

  /**
   * byte出现的下标，如果不存在，返回-1
   * @param b byte
   * @return
   */
  public int indexOf(byte b){
    for(int i = 0,n=size();i<n;i++){
      if(b==l.get(i)){
        return i;
      }
    }
    return -1;
  }

  public boolean equals(ByteArray ba){
    if(null==ba){
      return false;
    }
    int len = size();
    if(len!=ba.size()){
      return false;
    }
    for(int i = 0;i<len;i++){
      if(l.get(i)!=ba.get(i)){
        return false;
      }
    }
    return true;
  }

  /**
   * 获取16进制字符串
   * @param prefix 前缀，如：0x
   * @param separator 分隔符，如：,
   * @return
   */
  public String toString(String prefix,String separator){
    String pre = null==prefix?"":prefix;
    String sp = null==separator?"":separator;
    StringBuilder s = new StringBuilder();
    for(int i=0,j=size();i<j;i++){
      if(i>0) {
        s.append(sp);
      }
      s.append(pre);
      String h = Integer.toHexString(get(i)&0xFF);
      if(h.length()<2){
        s.append("0");
      }
      s.append(h);
    }
    return s.toString();
  }

  /**
   * 追加16进制字符串
   * @param hex 16进制字符串，如：0x20 0x01
   * @param prefix 每字节数据的前缀，如：0x
   */
  public ByteArray append(String hex,String prefix){
    String s = hex;
    int pl = prefix.length();
    if(pl>0){
      int start = s.indexOf(prefix);
      while(start>-1){
        s = s.substring(start+pl);
        if(s.length()>1){
          append((byte)((Character.digit(s.charAt(0),16)<<4)+Character.digit(s.charAt(1),16)));
          s = s.substring(2);
        }
        start = s.indexOf(prefix);
      }
    }else{
      int l = s.length()/2;
      byte[] b = new byte[l];
      for (int i=0;i<l;i+=2){
        b[i/2] = (byte)((Character.digit(s.charAt(i),16)<<4)+Character.digit(s.charAt(i + 1),16));
      }
      append(b);
    }
    return this;
  }

  /**
   * 获取16进制字符串，如：0x01 0x23 0x45
   */
  public String toString(){
    return toString("0x"," ");
  }
}