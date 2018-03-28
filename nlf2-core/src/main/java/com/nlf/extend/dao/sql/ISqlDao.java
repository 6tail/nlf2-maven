package com.nlf.extend.dao.sql;

import com.nlf.dao.IDao;

/**
 * SQLDao接口
 * 
 * @author 6tail
 *
 */
public interface ISqlDao extends IDao{
  /**
   * 获取SQL删除器
   * 
   * @return SQL删除器
   */
  ISqlDeleter getDeleter();

  /**
   * 获取SQL更新器
   * 
   * @return SQL更新器
   */
  ISqlUpdater getUpdater();

  /**
   * 获取SQL查询器
   * 
   * @return SQL查询器
   */
  ISqlSelecter getSelecter();

  /**
   * 获取SQL插入器
   * 
   * @return SQL插入器
   */
  ISqlInserter getInserter();

  /**
   * 获取SQL模板
   * 
   * @return SQL模板
   */
  ISqlTemplate getTemplate();
}