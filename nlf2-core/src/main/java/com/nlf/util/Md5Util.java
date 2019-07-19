package com.nlf.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Md5工具
 * 
 * @author 6tail
 * 
 */
public class Md5Util{
  /**
   * 对字符串进行MD5加密
   * 
   * @param s 原文
   * @param charsetName 编码，一般utf-8
   * @return 密文，大写
   */
  public static String encode(String s,String charsetName){
    try{
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(s.getBytes(charsetName));
      byte[] b = md.digest();
      StringBuilder sb = new StringBuilder();
      for(byte d:b){
        String hex = Integer.toHexString(d&0xFF);
        if(hex.length()<2){
          sb.append("0");
        }
        sb.append(hex.toUpperCase());
      }
      return sb+"";
    }catch(NoSuchAlgorithmException e){
      throw new RuntimeException(e);
    }catch(UnsupportedEncodingException e){
      throw new RuntimeException(e);
    }
  }
}