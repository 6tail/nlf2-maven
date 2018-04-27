package com.nlf.core;

import com.nlf.exception.ValidateException;

/**
 * 输入请求接口
 * 
 * @author 6tail
 *
 */
public interface IRequest extends IFileUploader{
  /**
   * 获取请求地址
   * @return 请求地址
   */
  String getPath();

  /**
   * 获取客户端
   * 
   * @return 客户端
   */
  Client getClient();

  /**
   * 获取会话
   * @return 会话
   */
  ISession getSession();

  /**
   * 获取请求参数
   * 
   * @return 请求参数
   */
  com.nlf.Bean getParam();

  /**
   * 获取请求参数值
   * 
   * @param key 参数名
   * @return 参数值
   */
  String get(String key);

  /**
   * 获取请求参数值数组
   * @param key 参数名
   * @return 参数值数组
   */
  String[] getArray(String key);

  /**
   * 获取验证通过的请求参数值
   * 
   * @param key 参数名
   * @param rules 验证规则
   * @return 参数值
   * @throws ValidateException 验证异常
   */
  String get(String key,String rules) throws ValidateException;

  /**
   * 获取验证通过的请求参数值数组
   * 
   * @param key 参数名
   * @param rules 验证规则
   * @return 参数值数组
   * @throws ValidateException 验证异常
   */
  String[] getArray(String key,String rules) throws ValidateException;

  /**
   * 获取验证通过的请求参数值
   * 
   * @param key 参数名
   * @param rules 验证规则
   * @param name 名称，用于验证失败时提示
   * @return 参数值
   * @throws ValidateException 验证异常
   */
  String get(String key,String rules,String name) throws ValidateException;

  /**
   * 获取验证通过的请求参数值数组
   * 
   * @param key 参数名
   * @param rules 验证规则
   * @param name 名称，用于验证失败时提示
   * @return 参数值数组
   * @throws ValidateException 验证异常
   */
  String[] getArray(String key,String rules,String name) throws ValidateException;

  /**
   * 获取页码
   * 
   * @return 页码
   */
  int getPageNumber();

  /**
   * 获取每页记录数
   * 
   * @return 每页记录数
   */
  int getPageSize();
}