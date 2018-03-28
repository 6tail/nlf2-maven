package com.nlf.extend.web;

import javax.servlet.FilterChain;
import com.nlf.core.IFilterChain;

public interface IWebFilterChain extends IFilterChain{
  void setFilterChain(FilterChain filterChain);
}