package com.nlf.extend.web;

import javax.servlet.FilterChain;
import com.nlf.core.IFilterChain;

/**
 * WEB过滤链
 *
 * @author 6tail
 */
public interface IWebFilterChain extends IFilterChain{
  /**
   * 设置过滤链
   * @param filterChain 过滤链
   */
  void setFilterChain(FilterChain filterChain);
}