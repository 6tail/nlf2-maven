package com.nlf.core;

/**
 * 扫描器
 * 
 * @author 6tail
 *
 */
public interface IScanner{
  /**
   * 忽略指定路径
   *
   * @param path 路径
   * @return 扫描器
   */
  IScanner ignore(String... path);

  /**
   * 忽略带有指定manifest属性的jar
   *
   * @param key 属性名称
   * @param value 属性值
   * @return 扫描器
   */
  IScanner ignoreJarByManifestAttribute(String key,String... value);

  /**
   * 添加绝对路径
   *
   * @param path 绝对路径
   * @return 扫描器
   */
  IScanner addAbsolutePath(String... path);

  /**
   * 添加相对路径
   *
   * @param path 相对路径
   * @return 扫描器
   */
  IScanner addRelativePath(String... path);

  /**
   * 开始扫描
   *
   * @return 扫描器
   */
  IScanner start();

  /**
   * 设置调用者路径
   *
   * @param path 路径
   * @return 扫描器
   */
  IScanner setCaller(String path);
}