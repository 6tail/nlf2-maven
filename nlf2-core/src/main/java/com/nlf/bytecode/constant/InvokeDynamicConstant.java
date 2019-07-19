package com.nlf.bytecode.constant;

/**
 * 常量 - InvokeDynamic
 *
 * @author 6tail
 */
public class InvokeDynamicConstant extends AbstractConstant{
  private int bootstrapMethodAttributeIndex;
  private int nameAndTypeIndex;

  public InvokeDynamicConstant() {}

  public InvokeDynamicConstant(int bootstrapMethodAttributeIndex, int nameAndTypeIndex) {
    this.bootstrapMethodAttributeIndex = bootstrapMethodAttributeIndex;
    this.nameAndTypeIndex = nameAndTypeIndex;
  }

  public int getBootstrapMethodAttributeIndex() {
    return bootstrapMethodAttributeIndex;
  }

  public void setBootstrapMethodAttributeIndex(int bootstrapMethodAttributeIndex) {
    this.bootstrapMethodAttributeIndex = bootstrapMethodAttributeIndex;
  }

  public int getNameAndTypeIndex() {
    return nameAndTypeIndex;
  }

  public void setNameAndTypeIndex(int nameAndTypeIndex) {
    this.nameAndTypeIndex = nameAndTypeIndex;
  }

  @Override
  public InvokeDynamicConstant toInvokeDynamicConstant(){
    return this;
  }
}