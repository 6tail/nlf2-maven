package com.nlf.extend.serialize.obj.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import com.nlf.serialize.AbstractWrapper;
import com.nlf.util.Base64Util;
import com.nlf.util.IOUtil;

/**
 * 默认obj包装器
 * 
 * @author 6tail
 *
 */
public class DefaultObjWrapper extends AbstractWrapper{
  public String wrap(Object o){
    ByteArrayOutputStream bObj = null;
    ByteArrayOutputStream bZip = null;
    ObjectOutputStream oObj = null;
    ZipOutputStream oZip = null;
    try{
      bObj = new ByteArrayOutputStream();
      bZip = new ByteArrayOutputStream();
      oObj = new ObjectOutputStream(bObj);
      oObj.writeObject(o);
      oObj.flush();
      oZip = new ZipOutputStream(bZip);
      oZip.putNextEntry(new ZipEntry("0"));
      oZip.write(bObj.toByteArray());
      oZip.closeEntry();
      return Base64Util.encode(bZip.toByteArray());
    }catch(IOException e){
      throw new RuntimeException(e);
    }finally{
      IOUtil.closeQuietly(oZip);
      IOUtil.closeQuietly(bZip);
      IOUtil.closeQuietly(oObj);
      IOUtil.closeQuietly(bObj);
    }
  }

  public boolean support(String format){
    return "obj".equalsIgnoreCase(format);
  }
}