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

  /** 字节长度 */
  private static final int BYTE_LENGTH = 8;

  private MathUtil(){}

  /**
   * 获取一个字节的8个bit
   * @param value 1字节数据
   * @return byte数组
   */
  public static byte[] toBit(byte value){
    byte[] b = new byte[BYTE_LENGTH];
    for(int i = 0;i<BYTE_LENGTH;i++){
      b[i] = (byte)(value>>(BYTE_LENGTH-i-1)&0x1);
    }
    return b;
  }

  /**
   * 将一个byte数组转为bit数组
   * @param value byte数据
   * @return byte数组
   */
  public static byte[] toBit(byte[] value){
    int size = value.length;
    byte[] b = new byte[BYTE_LENGTH*size];
    for(int i = 0;i<size;i++){
      for(int j = 0;j<BYTE_LENGTH;j++) {
        b[i*BYTE_LENGTH+j] = (byte)(value[i]>>(BYTE_LENGTH-j-1)&0x1);
      }
    }
    return b;
  }

  /**
   * 8bit转1byte，不足8bit的，高位补0
   * @param bits bit数组
   * @return byte
   */
  public static byte toByte(byte[] bits){
    int len = bits.length;
    if(len > BYTE_LENGTH){
      throw new IllegalArgumentException();
    }
    byte[] padded = bits;
    if(len < BYTE_LENGTH) {
      padded = new byte[BYTE_LENGTH];
      System.arraycopy(bits, 0, padded, BYTE_LENGTH - len, len);
    }
    StringBuilder s = new StringBuilder();
    for(byte bit:padded){
      s.append(bit);
    }
    int b = Integer.parseInt(s.toString(),2);
    if(bits[0]==1){
      b -= 256;
    }
    return (byte)b;
  }

  /**
   * bit数组转byte数组，不足位数的，高位补0
   * @param bits bit数组
   * @return byte数组
   */
  public static byte[] toBytes(byte[] bits){
    int len = bits.length;
    int size = (int)Math.ceil(len*1D/BYTE_LENGTH);
    int total = size*BYTE_LENGTH;
    byte[] bytes = new byte[size];
    byte[] padded = bits;
    if(total!=len) {
      padded = new byte[total];
      System.arraycopy(bits, 0, padded, total - len, len);
    }
    for(int i=0;i<size;i++){
      byte[] b = new byte[BYTE_LENGTH];
      System.arraycopy(padded,i*BYTE_LENGTH,b,0,BYTE_LENGTH);
      bytes[i] = toByte(b);
    }
    return bytes;
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
      b[i] = (byte)((value>>>(size-i-1)*BYTE_LENGTH)&0xFF);
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
      b[i] = (byte)((value>>>(size-i-1)*BYTE_LENGTH)&0xFF);
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
    for(int i=0,j=b.length;i<j;i++){
      n += (b[i]&0xFF)<<((j-i-1)*BYTE_LENGTH);
    }
    return n;
  }

  /**
   * byte数组转长整型（大端、网络字节序）
   *
   * @param b byte数组
   * @return 长整型
   */
  public static long toLong(byte[] b){
    long n = 0;
    for(int i=0,j=b.length;i<j;i++){
      n += (b[i]&0xFF)<<((j-i-1)*BYTE_LENGTH);
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
   * @param from 开始点，从0开始计算，包含
   * @param to 结束点，从0开始计算，包含
   * @return byte数组
   */
  public static byte[] sub(byte[] src,int from,int to){
    byte[] t = new byte[to-from+1];
    System.arraycopy(src,from,t,0,to-from+1);
    return t;
  }
}
