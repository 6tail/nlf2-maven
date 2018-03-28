package com.nlf.extend.dao.sql.dbType.oracle11g;

import com.nlf.extend.dao.sql.dbType.common.ASqlTemplate;

/**
 * SQL模板的oracle11g实现
 * @author 6tail
 *
 */
public class Oracle11gTemplate extends ASqlTemplate{
  public boolean support(String dbType){
    return "oracle11g".equalsIgnoreCase(dbType);
  }
}