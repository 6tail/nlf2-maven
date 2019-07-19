package com.nlf.bytecode.constant;

/**
 * 常量接口
 *
 * @author 6tail
 */
public interface IConstant{
  int TYPE_UTF = 1;
  int TYPE_INT = 3;
  int TYPE_FLOAT = 4;
  int TYPE_LONG = 5;
  int TYPE_DOUBLE = 6;
  int TYPE_CLASS = 7;
  int TYPE_STRING = 8;
  int TYPE_FIELD = 9;
  int TYPE_METHOD = 10;
  int TYPE_INTERFACE_METHOD = 11;
  int TYPE_NAME_AND_TYPE = 12;
  int TYPE_METHOD_HANDLE = 15;
  int TYPE_METHOD_TYPE = 16;
  int TYPE_INVOKE_DYNAMIC = 18;

  /**
   * 获取序号
   * @return 序号
   */
  int getIndex();

  /**
   * 设置序号
   * @param index 序号
   */
  void setIndex(int index);

  /**
   * 获取类型
   * @return 类型
   */
  int getType();

  /**
   * 设置类型
   * @param type 类型
   */
  void setType(int type);

  /**
   * 获取数据
   * @return 数据
   */
  byte[] getData();

  /**
   * 设置数据
   * @param data 数据
   */
  void setData(byte[] data);

  /**
   * 转换为 ClassConstant
   * @return ClassConstant
   */
  ClassConstant toClassConstant();

  /**
   * 转换为 UTFConstant
   * @return UTFConstant
   */
  UTFConstant toUTFConstant();

  /**
   * 转换为 FieldConstant
   * @return FieldConstant
   */
  FieldConstant toFieldConstant();

  /**
   * 转换为 MethodConstant
   * @return MethodConstant
   */
  MethodConstant toMethodConstant();

  /**
   * 转换为 NameAndTypeConstant
   * @return NameAndTypeConstant
   */
  NameAndTypeConstant toNameAndTypeConstant();

  /**
   * 转换为 InvokeDynamicConstant
   * @return InvokeDynamicConstant
   */
  InvokeDynamicConstant toInvokeDynamicConstant();

  /**
   * 转换为 MethodHandleConstant
   * @return MethodHandleConstant
   */
  MethodHandleConstant toMethodHandleConstant();

  /**
   * 转换为 MethodTypeConstant
   * @return MethodTypeConstant
   */
  MethodTypeConstant toMethodTypeConstant();
}