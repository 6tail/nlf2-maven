package com.nlf.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.zip.ZipFile;

/**
 * IO处理工具
 * 
 * @author 6tail
 *
 */
public class IOUtil{

  /** IO缓冲区大小 */
  public static int BUFFER_SIZE = 20480;

  /**
   * 输入流转字节数组
   * @param in 输入流
   * @return 字节数组
   * @throws IOException IOException
   */
  public static byte[] toBytes(InputStream in) throws IOException{
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    try{
      byte[] buffer = new byte[BUFFER_SIZE];
      int l = 0;
      while(-1!=(l = in.read(buffer))){
        out.write(buffer,0,l);
      }
      out.flush();
      return out.toByteArray();
    }finally{
      closeQuietly(in);
      closeQuietly(out);
    }
  }
  
  /**
   * 安静的关闭
   * 
   * @param closeable Closeable
   */
  public static void closeQuietly(Closeable closeable){
    if(null==closeable){
      return;
    }
    try{
      closeable.close();
    }catch(IOException e){}
  }

  public static void closeQuietly(ZipFile zip){
    if(null==zip){
      return;
    }
    try{
      zip.close();
    }catch(IOException e){}
  }

  public static void closeQuietly(Socket socket){
    if(null==socket){
      return;
    }
    try{
      socket.close();
    }catch(IOException e){}
  }
  
  public static void closeQuietly(Connection connection){
    if(null==connection){
      return;
    }
    try{
      connection.close();
    }catch(SQLException e){}
  }
  
  public static void closeQuietly(ResultSet rs){
    if(null==rs){
      return;
    }
    try{
      rs.close();
    }catch(SQLException e){}
  }
  
  public static void closeQuietly(Statement stmt){
    if(null==stmt){
      return;
    }
    try{
      stmt.close();
    }catch(SQLException e){}
  }
}