package com.nlf.dao.exception;

import com.nlf.App;

/**
 * 数据访问异常
 * 
 * @author 6tail
 */
public class DaoException extends com.nlf.exception.NlfException{
  private static final long serialVersionUID = 1;
  private static final String MESSAGE = "nlf.exception.dao";

  public DaoException(){
    super(App.getProperty(MESSAGE));
  }

  public DaoException(String message){
    super(message);
  }

  public DaoException(Throwable cause){
    this(App.getProperty(MESSAGE),cause);
  }

  public DaoException(String message,Throwable cause){
    super(message,cause);
  }
}