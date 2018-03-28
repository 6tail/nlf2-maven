package com.nlf.bytecode.constant;

/**
 * 常量-属性
 * 
 * @author 6tail
 *
 */
public class FieldConstant extends AbstractConstant{
  private int classIndex;
  private int nameAndTypeIndex;

  public int getClassIndex(){
    return classIndex;
  }

  public void setClassIndex(int classIndex){
    this.classIndex = classIndex;
  }

  public int getNameAndTypeIndex(){
    return nameAndTypeIndex;
  }

  public void setNameAndTypeIndex(int nameAndTypeIndex){
    this.nameAndTypeIndex = nameAndTypeIndex;
  }

  public FieldConstant toFieldConstant(){
    return this;
  }
}