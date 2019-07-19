package com.nlf.util;

import java.lang.management.ManagementFactory;
import java.net.NetworkInterface;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 唯一ID生成器，生成24位递增的唯一字符串
 *
 * @author 6tail
 *
 */
public class IDUtil implements java.io.Serializable{
  private static final long serialVersionUID = 1;
  private final int time;
  private static final int MACHINE;
  private final int inc;
  private static AtomicInteger nextInc = new AtomicInteger((new Random()).nextInt());

  /**
   * 获取唯一ID
   *
   * @return 唯一ID，24位递增的唯一字符串
   */
  public static String next(){
    return new IDUtil().toHexString();
  }

  private IDUtil(){
    time = (int)(System.currentTimeMillis()/1000);
    inc = nextInc.getAndIncrement();
  }

  private String toHexString(){
    final StringBuilder s = new StringBuilder(24);
    for(final byte b:toByteArray()){
      s.append(String.format("%02x",b&0xff));
    }
    return s+"";
  }

  private byte[] toByteArray(){
    byte[] b = new byte[12];
    ByteBuffer bb = ByteBuffer.wrap(b);
    bb.putInt(time);
    bb.putInt(MACHINE);
    bb.putInt(inc);
    return b;
  }

  static{
    int machinePiece;
    try{
      StringBuilder s = new StringBuilder();
      Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
      while(e.hasMoreElements()){
        s.append(e.nextElement().toString());
      }
      machinePiece = s.toString().hashCode()<<16;
    }catch(Throwable e){
      machinePiece = (new Random().nextInt())<<16;
    }
    final int processPiece;
    int processId = new java.util.Random().nextInt();
    try{
      processId = ManagementFactory.getRuntimeMXBean().getName().hashCode();
    }catch(Throwable t){}
    ClassLoader loader = IDUtil.class.getClassLoader();
    int loaderId = loader!=null?System.identityHashCode(loader):0;
    String s = Integer.toHexString(processId);
    s += Integer.toHexString(loaderId);
    processPiece = s.hashCode()&0xFFFF;
    MACHINE = machinePiece|processPiece;
  }
}