package com.nlf.extend.dao.sql;

/**
 * 条件
 * 
 * @author 6tail
 *
 */
public class Condition{
  /** 类型 */
  private ConditionType type = ConditionType.one_param;
  /** 字段 */
  private String column;
  /** 操作开始 */
  private String start = "=";
  /** 占位符 */
  private String placeholder = "?";
  /** 值 */
  private Object value;
  /** 操作结束 */
  private String end = "";

  public ConditionType getType(){
    return type;
  }

  public void setType(ConditionType type){
    this.type = type;
  }

  public String getColumn(){
    return column;
  }

  public void setColumn(String column){
    this.column = column;
  }

  public String getStart(){
    return start;
  }

  public void setStart(String start){
    this.start = start;
  }

  public String getPlaceholder(){
    return placeholder;
  }

  public void setPlaceholder(String placeholder){
    this.placeholder = placeholder;
  }

  public Object getValue(){
    return value;
  }

  public void setValue(Object value){
    this.value = value;
  }

  public String getEnd(){
    return end;
  }

  public void setEnd(String end){
    this.end = end;
  }
}