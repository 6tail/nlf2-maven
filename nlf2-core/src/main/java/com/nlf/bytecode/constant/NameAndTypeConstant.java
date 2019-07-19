package com.nlf.bytecode.constant;

/**
 * 常量 - 名称和类型
 *
 * @author 6tail
 */
public class NameAndTypeConstant extends AbstractConstant{
  private int nameIndex;
  private int descriptorIndex;

  public NameAndTypeConstant() {}

  public NameAndTypeConstant(byte[] data, int nameIndex, int descriptorIndex) {
    this.data = data;
    this.nameIndex = nameIndex;
    this.descriptorIndex = descriptorIndex;
  }

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

  @Override
  public NameAndTypeConstant toNameAndTypeConstant(){
    return this;
  }
}