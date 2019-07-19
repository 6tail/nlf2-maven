package com.nlf.util;

import java.util.regex.Pattern;

/**
 * Base64编解码器
 * 
 * @author 6tail
 * 
 */
public class Base64Util{
  private static final int MIN_UNIT_SIZE = 4;
  private static final int MAP1_SIZE = 64;
  private static final int MAP2_SIZE = 128;
  private static final char[] MAP1 = new char[MAP1_SIZE];
  private static final byte[] MAP2 = new byte[MAP2_SIZE];
  private Base64Util(){}

  static{
    int i = 0;
    for(char c = Chars.A_UPPER;c<=Chars.Z_UPPER;c++){
      MAP1[i++] = c;
    }
    for(char c = Chars.A_LOWER;c<=Chars.Z_LOWER;c++){
      MAP1[i++] = c;
    }
    for(char c = Chars.ZERO;c<=Chars.NINE;c++){
      MAP1[i++] = c;
    }
    MAP1[i++] = Chars.ADD;
    MAP1[i] = Chars.SLASH_LEFT;

    for(i = 0;i<MAP2_SIZE;i++){
      MAP2[i] = -1;
    }
    for(i = 0;i<MAP1_SIZE;i++){
      MAP2[MAP1[i]] = (byte)i;
    }
  }

  public static boolean isBase64(String s){
    return Pattern.matches("^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{4}|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)$", s);
  }

  public static String encode(byte[] in){
    int iOff = 0;
    int iLen = in.length;
    int oDataLen = (iLen*4+2)/3;
    int oLen = ((iLen+2)/3)*4;
    char[] out = new char[oLen];
    int ip = iOff;
    int iEnd = iOff+iLen;
    int op = 0;
    while(ip<iEnd){
      int i0 = in[ip++]&0xff;
      int i1 = ip<iEnd?in[ip++]&0xff:0;
      int i2 = ip<iEnd?in[ip++]&0xff:0;
      int o0 = i0>>>2;
      int o1 = ((i0&3)<<4)|(i1>>>4);
      int o2 = ((i1&0xf)<<2)|(i2>>>6);
      int o3 = i2&0x3F;
      out[op++] = MAP1[o0];
      out[op++] = MAP1[o1];
      out[op] = op<oDataLen?MAP1[o2]:Chars.EQ;
      op++;
      out[op] = op<oDataLen?MAP1[o3]:Chars.EQ;
      op++;
    }
    return new String(out);
  }

  public static byte[] decode(String s){
    char[] in = s.toCharArray();
    int iOff = 0;
    int iLen = in.length;
    if(iLen%MIN_UNIT_SIZE!=0){
      throw new IllegalArgumentException("Length of Base64 encoded input string is not a multiple of 4.");
    }
    while(iLen>0&&in[iOff+iLen-1]==Chars.EQ){
      iLen--;
    }
    int oLen = (iLen*3)/4;
    byte[] out = new byte[oLen];
    int ip = iOff;
    int iEnd = iOff+iLen;
    int op = 0;
    while(ip<iEnd){
      int i0 = in[ip++];
      int i1 = in[ip++];
      int i2 = ip<iEnd?in[ip++]:Chars.A_UPPER;
      int i3 = ip<iEnd?in[ip++]:Chars.A_UPPER;
      if(i0>127||i1>127||i2>127||i3>127){
        throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
      }
      int b0 = MAP2[i0];
      int b1 = MAP2[i1];
      int b2 = MAP2[i2];
      int b3 = MAP2[i3];
      if(b0<0||b1<0||b2<0||b3<0){
        throw new IllegalArgumentException("Illegal character in Base64 encoded data.");
      }
      int o0 = (b0<<2)|(b1>>>4);
      int o1 = ((b1&0xf)<<4)|(b2>>>2);
      int o2 = ((b2&3)<<6)|b3;
      out[op++] = (byte)o0;
      if(op<oLen) {
        out[op++] = (byte) o1;
      }
      if(op<oLen) {
        out[op++] = (byte) o2;
      }
    }
    return out;
  }
}