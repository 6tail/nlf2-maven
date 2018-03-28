package com.nlf.serialize.node;

/**
 * 节点类型
 * 
 * @author 6tail
 * 
 */
public enum NodeType{
  /** 对象 */
  MAP,
  /** 列表 */
  LIST,
  /** 字符串 */
  STRING,
  /** 数字 */
  NUMBER,
  /** 布尔值 */
  BOOL
}