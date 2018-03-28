package com.nlf.bytecode.constant;

/**
 * 常量-类
 * 
 * @author 6tail
 *
 */
public class ClassConstant extends AbstractConstant{
  private int nameIndex;

  public int getNameIndex(){
    return nameIndex;
  }

  public void setNameIndex(int nameIndex){
    this.nameIndex = nameIndex;
  }

  public ClassConstant toClassConstant(){
    return this;
  }
}