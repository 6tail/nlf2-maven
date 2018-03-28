package com.nlf.log.impl;

import com.nlf.log.ILog;
import com.nlf.log.ILogAdapter;

/**
 * 默认日志适配器
 * 
 * @author 6tail
 * 
 */
public class DefaultLogAdapter implements ILogAdapter{

  public ILog getLog(String name){
    return new DefaultLog(name);
  }

  public boolean isSupported(){
    return true;
  }
}