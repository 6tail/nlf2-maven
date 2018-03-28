package com.nlf.exception;

import com.nlf.App;

/**
 * 参数验证失败异常
 * 
 * @author 6tail
 *
 */
public class ValidateException extends IllegalArgumentException{
  private static final long serialVersionUID = 1;
  private static final String MESSAGE = "nlf.exception.validate";

  public ValidateException(){
    super(App.getProperty(MESSAGE));
  }

  public ValidateException(String message){
    super(message);
  }

  public ValidateException(Throwable cause){
    this(App.getProperty(MESSAGE),cause);
  }

  public ValidateException(String message,Throwable cause){
    super(message,cause);
  }
}