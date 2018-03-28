package com.nlf.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 运算辅助工具
 *
 * @author 6tail
 *
 */
public class MathUtil{

  private MathUtil(){}

  /**
   * 获取一个字节的8个bit
   * @param value 1字节数据
   * @return byte数组
   */
  public static byte[] toBit(byte value){
    int l = 8;
    byte[] b = new byte[l];
    for(int i = 0;i<l;i++){
      b[i] = (byte)(value>>(l-i-1)&0x1);
    }
    return b;
  }

  /**
   * 将一个byte数组转为bit数组
   * @param value byte数据
   * @return byte数组
   */
  public static byte[] toBit(byte[] value){
    int l = 8;
    int size = value.length;
    byte[] b = new byte[l*size];
    for(int i = 0;i<size;i++){
      for(int j = 0;j<l;j++) {
        b[i*l+j] = (byte)(value[i]>>(l-j-1)&0x1);
      }
    }
    return b;
  }

  /**
   * 整型转指定长度的byte数组（大端、网络字节序）
   *
   * @param value 整型数字
   * @param size byte长度
   * @return byte数组
   */
  public static byte[] toByte(int value,int size){
    byte[] b = new byte[size];
    for(int i = 0;i<size;i++){
      b[i] = (byte)((value>>>(b.length-i-1)*8)&0xFF);
    }
    return b;
  }

  /**
   * 长整型转指定长度的byte数组（大端、网络字节序）
   *
   * @param value 整型数字
   * @param size byte长度
   * @return byte数组
   */
  public static byte[] toByte(long value,int size){
    byte[] b = new byte[size];
    for(int i = 0;i<size;i++){
      b[i] = (byte)((value>>>(b.length-i-1)*8)&0xFF);
    }
    return b;
  }

  /**
   * byte数组转整型（大端、网络字节序）
   *
   * @param b byte数组
   * @return 整型
   */
  public static int toInt(byte[] b){
    int n = 0;
    for(int i=0;i<b.length;i++){
      n += (b[i]&0xFF)<<((b.length-i-1)*8);
    }
    return n;
  }

  /**
   * byte数组转长整型（大端、网络字节序）
   *
   * @param b byte数组
   * @return 整型
   */
  public static long toLong(byte[] b){
    long n = 0;
    for(int i=0;i<b.length;i++){
      n += (b[i]&0xFF)<<((b.length-i-1)*8);
    }
    return n;
  }

  /**
   * 两个byte数组对接
   *
   * @param a 前
   * @param b 后
   * @return 对接后的数组
   */
  public static byte[] merge(byte[] a,byte[] b){
    int la = a.length;
    int lb = b.length;
    byte[] t = new byte[la+lb];
    System.arraycopy(a,0,t,0,la);
    System.arraycopy(b,0,t,la,lb);
    return t;
  }

  public static byte[] merge(byte[]... arrays){
    byte[] t = null;
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try{
      for(byte[] b:arrays){
        os.write(b);
      }
      os.flush();
      t = os.toByteArray();
    }catch(IOException e){
      throw new RuntimeException(e);
    }finally{
      IOUtil.closeQuietly(os);
    }
    return t;
  }

  /**
   * 获取一个byte数组的一部分
   *
   * @param src 源数组
   * @param from 开始点，从0开始计算
   * @param to 结束点，从0开始计算
   * @return byte数组
   */
  public static byte[] sub(byte[] src,int from,int to){
    byte[] t = new byte[to-from+1];
    System.arraycopy(src,from,t,0,to-from+1);
    return t;
  }
}