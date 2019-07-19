package com.nlf.bytecode.constant;

/**
 * 常量 - 字符串
 *
 * @author 6tail
 */
public class UTFConstant extends AbstractConstant{
  private String content;

  public UTFConstant() {}

  public UTFConstant(byte[] data,String content) {
    this.data = data;
    this.content = content;
  }

  public String getContent(){
    return content;
  }

  public void setContent(String content){
    this.content = content;
  }

  @Override
  public UTFConstant toUTFConstant(){
    return this;
  }
}