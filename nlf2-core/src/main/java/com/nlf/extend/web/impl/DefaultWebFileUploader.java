package com.nlf.extend.web.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import com.nlf.App;
import com.nlf.core.IRequest;
import com.nlf.core.UploadFile;
import com.nlf.exception.ValidateException;
import com.nlf.extend.web.IWebFileUploader;
import com.nlf.extend.web.IWebRequest;
import com.nlf.util.ByteArray;
import com.nlf.util.FileUtil;
import com.nlf.util.IOUtil;
import com.nlf.util.StringUtil;

/**
 * 默认WEB文件上传器
 * 
 * @author 6tail
 *
 */
public class DefaultWebFileUploader implements IWebFileUploader{

  /**
   * 表单项
   * @author 6tail
   *
   */
  private static final class FormItem{
    /** 参数名 */
    private String key;
    /** 临时文件 */
    private File tempFile;
    /** 上传的文件 */
    private UploadFile file;

    public String getKey(){
      return key;
    }
    public void setKey(String key){
      this.key = key;
    }
    public File getTempFile(){
      return tempFile;
    }
    public void setTempFile(File tempFile){
      this.tempFile = tempFile;
    }
    public UploadFile getFile(){
      return file;
    }
    public void setFile(UploadFile file){
      this.file = file;
    }
  }
  /** 编码 */
  public static final String CHARSET = "utf-8";
  /** 参数名标识 */
  public static final String KEY_TAG = " name=\"";
  /** 文件名标识 */
  public static final String FILE_TAG = "filename=\"";
  /** 文件名标识 */
  public static final String BOUNDARY_TAG = "boundary=";
  /** 表单项 */
  protected FormItem formItem;

  /**
   * 获取boundary
   * 
   * @return boundary
   */
  protected byte[] getBoundary(String contentType){
    if(null==contentType){
      return null;
    }
    if(!contentType.contains("multipart/form-data")){
      return null;
    }
    if(!contentType.contains(BOUNDARY_TAG)){
      return null;
    }
    return ("\r\n--"+StringUtil.right(contentType,BOUNDARY_TAG)).getBytes();
  }

  protected void append(byte[] boundary,ByteArray cache) throws IOException{
    if(null==formItem) return;
    int size = cache.size();
    int lb = boundary.length;
    if(size>lb){
      int lr = size-lb;
      appendFile(cache.sub(0,lr).toArray());
      cache.removeHead(lr);
    }
  }

  protected void appendFile(byte[] data) throws IOException{
    if(null==formItem) return;
    File tempFile = formItem.getTempFile();
    if(null==tempFile){
      UploadFile file = formItem.getFile();
      String fileName = file.getName();
      String suffix = file.getSuffix();
      if(fileName.contains(".")){
        fileName = fileName.substring(0,fileName.lastIndexOf("."));
      }
      StringBuilder prefix = new StringBuilder();
      prefix.append(fileName);
      //凑够字符数
      while(prefix.length()<2){
        prefix.append("_");
      }
      prefix.append("_");
      if(suffix.length()>0){
        suffix = "."+suffix;
      }
      tempFile = File.createTempFile(prefix.toString(),suffix);
      formItem.setTempFile(tempFile);
    }
    BufferedOutputStream out = null;
    try{
      out = new BufferedOutputStream(new FileOutputStream(tempFile,true));
      out.write(data);
      out.flush();
    }finally{
      IOUtil.closeQuietly(out);
    }
  }

  protected void parseItem(byte[] boundary,ByteArray cache,List<UploadFile> files) throws IOException{
    if(null==formItem){
      int index = cache.indexOf(new byte[]{0x0D,0x0A,0x0D,0x0A});
      while(index>-1){
        String header = new String(cache.sub(0,index).toArray(),CHARSET);
        if(header.contains(FILE_TAG)){
          String fileName = StringUtil.between(header,FILE_TAG,"\"");
          if(fileName.length()>0){
            formItem = new FormItem();
            UploadFile file = new UploadFile();
            file.setName(fileName);
            file.setSuffix(fileName.contains(".")?fileName.substring(fileName.lastIndexOf(".")+1):"");
            if(header.contains(KEY_TAG)){
              String key = StringUtil.between(header,KEY_TAG,"\"");
              file.setKey(key);
            }
            if(header.contains("Content-Type:")){
              file.setContentType(StringUtil.right(header,":").trim());
            }
            formItem.setFile(file);
          }
        }else{
          if(header.contains(KEY_TAG)){
            formItem = new FormItem();
            String key = StringUtil.between(header,KEY_TAG,"\"");
            formItem.setKey(key);
          }
        }
        cache.removeHead(index+4);
        int endIndex = cache.indexOf(boundary);
        if(endIndex>-1){
          byte[] data = cache.sub(0,endIndex).toArray();
          if(null!=formItem){
            UploadFile file = formItem.getFile();
            String key = formItem.getKey();
            if(null!=file){
              file.setBytes(data);
              file.setType(UploadFile.TYPE_BYTES);
              file.setSize(data.length);
              files.add(file);
            }else if(null!=key){
              IRequest r = App.getRequest();
              r.getParam().set(key,new String(data,CHARSET));
            }
          }
          formItem = null;
          cache.removeHead(endIndex);
        }
        index = cache.indexOf(new byte[]{0x0D,0x0A,0x0D,0x0A});
      }
      append(boundary,cache);
    }else{
      int endIndex = cache.indexOf(boundary);
      if(endIndex>-1){
        appendFile(cache.sub(0,endIndex).toArray());
        File tempFile = formItem.getTempFile();
        if(null!=formItem){
          UploadFile file = formItem.getFile();
          String key = formItem.getKey();
          if(null!=file){
            file.setTempFile(tempFile);
            file.setType(UploadFile.TYPE_TEMP_FILE);
            file.setSize(tempFile.length());
            files.add(file);
          }else if(null!=key){
            IRequest r = App.getRequest();
            r.getParam().set(key,FileUtil.readAsText(tempFile));
          }
        }
        formItem = null;
        cache.removeHead(endIndex);
      }else{
        append(boundary,cache);
      }
    }
  }

  public List<UploadFile> getFiles() throws ValidateException{
    List<UploadFile> files = new ArrayList<UploadFile>();
    IWebRequest r = (IWebRequest)App.getRequest();
    HttpServletRequest req = r.getServletRequest();
    byte[] boundary = getBoundary(req.getContentType());
    if(null==boundary){
      return files;
    }
    ByteArray cache = new ByteArray();
    byte[] buffer = new byte[IOUtil.BUFFER_SIZE];
    int l;
    try{
      ServletInputStream reader = req.getInputStream();
      while(-1!=(l = reader.read(buffer))){
        byte[] tmp = new byte[l];
        System.arraycopy(buffer,0,tmp,0,l);
        cache.append(tmp);
        parseItem(boundary,cache,files);
      }
      parseItem(boundary,cache,files);
    }catch(IOException e){
      throw new RuntimeException(e);
    }
    return files;
  }
}