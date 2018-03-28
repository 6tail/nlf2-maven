package com.nlf.extend.dao.sql;

/**
 * 条件类型
 * 
 * @author 6tail
 *
 */
public enum ConditionType{
  /** 带1个参数 */
  one_param,
  /** 带多个参数 */
  multi_params,
  /** 纯SQL */
  pure_sql
}