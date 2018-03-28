package com.nlf.bytecode.constant;

/**
 * 常量-字符串
 * 
 * @author 6tail
 *
 */
public class UTFConstant extends AbstractConstant{
  private String content;

  public String getContent(){
    return content;
  }

  public void setContent(String content){
    this.content = content;
  }

  public UTFConstant toUTFConstant(){
    return this;
  }
}