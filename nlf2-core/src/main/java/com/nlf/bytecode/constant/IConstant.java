package com.nlf.bytecode.constant;

/**
 * 常量接口
 * 
 * @author 6tail
 *
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


  int getIndex();

  void setIndex(int index);

  int getType();

  void setType(int type);

  byte[] getData();

  void setData(byte[] data);

  ClassConstant toClassConstant();

  UTFConstant toUTFConstant();

  FieldConstant toFieldConstant();

  MethodConstant toMethodConstant();

  NameAndTypeConstant toNameAndTypeConstant();

  InvokeDynamicConstant toInvokeDynamicConstant();

  MethodHandleConstant toMethodHandleConstant();

  MethodTypeConstant toMethodTypeConstant();
}