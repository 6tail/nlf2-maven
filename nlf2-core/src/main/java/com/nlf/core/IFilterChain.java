package com.nlf.core;

public interface IFilterChain{
  void doFilter(IRequest request,IResponse response) throws java.io.IOException;
}