package com.nlf.log;

/**
 * 日志抽象
 * 
 * @author 6tail
 *
 */
public abstract class AbstractLog implements ILog{
  /** 名称 */
  protected String name;

  protected AbstractLog(String name){
    this.name = name;
  }
}