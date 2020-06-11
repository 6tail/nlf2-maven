package com.nlf.extend.rpc.socket.impl;

import com.nlf.core.UploadFile;
import com.nlf.extend.rpc.socket.AbstractSocketRpcFileUploader;
import com.nlf.util.IOUtil;
import com.nlf.util.Strings;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;

/**
 * Socket RPC文件上传的默认实现
 * @author 6tail
 */
public class DefaultSocketRpcFileUploader extends AbstractSocketRpcFileUploader {

  public UploadFile parseFile(String fileName,long fileSize,DataInputStream in) throws IOException {
    UploadFile file = new UploadFile();
    file.setName(fileName);
    String suffix = fileName.contains(".")?fileName.substring(fileName.lastIndexOf(".")+1):"";
    file.setSuffix(suffix);
    file.setSize(fileSize);
    if(fileSize<= IOUtil.BUFFER_SIZE){
      file.setType(UploadFile.TYPE_BYTES);
      byte[] buffer = new byte[(int)fileSize];
      in.readFully(buffer);
      file.setBytes(buffer);
    }else{
      file.setType(UploadFile.TYPE_TEMP_FILE);
      StringBuilder prefix = new StringBuilder();
      prefix.append(fileName);
      while(prefix.length()<MIN_TEMP_FILE_NAME_LENGTH){
        prefix.append(Strings.UNDERSCORE);
      }
      prefix.append(Strings.UNDERSCORE);
      if(suffix.length()>0){
        suffix = Strings.DOT+suffix;
      }
      File temp = File.createTempFile(prefix.toString(),suffix);
      long rest = fileSize;
      byte[] buffer = new byte[IOUtil.BUFFER_SIZE];
      while(rest>IOUtil.BUFFER_SIZE){
        in.readFully(buffer);
        IOUtil.writeFile(temp,buffer,true);
        rest -= IOUtil.BUFFER_SIZE;
      }
      buffer = new byte[(int)rest];
      in.readFully(buffer);
      IOUtil.writeFile(temp,buffer,true);
      file.setTempFile(temp);
    }
    files.add(file);
    return file;
  }

}
