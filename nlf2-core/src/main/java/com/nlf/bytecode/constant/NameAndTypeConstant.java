package com.nlf.bytecode.constant;

/**
 * 常量-名称和类型
 * 
 * @author 6tail
 *
 */
public class NameAndTypeConstant extends AbstractConstant{
  private int nameIndex;
  private int descriptorIndex;

  public int getNameIndex(){
    return nameIndex;
  }

  public void setNameIndex(int nameIndex){
    this.nameIndex = nameIndex;
  }

  public int getDescriptorIndex(){
    return descriptorIndex;
  }

  public void setDescriptorIndex(int descriptorIndex){
    this.descriptorIndex = descriptorIndex;
  }

  public NameAndTypeConstant toNameAndTypeConstant(){
    return this;
  }
}