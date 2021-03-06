package com.nlf.core;

import com.nlf.Bean;
import com.nlf.exception.ValidateException;

import java.io.IOException;
import java.io.InputStream;

/**
 * 输入请求接口
 *
 * @author 6tail
 *
 */
public interface IRequest extends IFileUploader{
  String LOCAL_IP_V6 = "0:0:0:0:0:0:0:1";
  String LOCAL_IP_V4 = "127.0.0.1";

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
   * 获取会话，将根据properties中nlf.session.auto_create值决定是否自动创建，默认为true
   * @return 会话
   */
  ISession getSession();

  /**
   * 获取会话
   * @param autoCreate 是否自动创建
   * @return 会话
   */
  ISession getSession(boolean autoCreate);

  /**
   * 获取请求参数
   *
   * @return 请求参数
   */
  com.nlf.Bean getParam();

  /**
   * 获取请求体字符串
   *
   * @return 请求体字符串
   */
  String getBodyString();

  /**
   * 获取请求体转换的Bean，使用默认数据格式
   *
   * @return 请求体转换为Bean
   */
  Bean getBody();

  /**
   * 获取请求体转换的Bean
   *
   * @param format 数据格式，如json/xml等
   * @return 请求体转换为Bean
   */
  Bean getBody(String format);

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

  /**
   * 获取输入流，允许多次调用
   * @return 输入流
   * @throws IOException IOException
   */
  InputStream getInputStream() throws IOException;
}
