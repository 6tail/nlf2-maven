package com.nlf.exception;

import com.nlf.App;

/**
 * 框架异常
 * 
 * @author 6tail
 * 
 */
public class NlfException extends RuntimeException{
  private static final long serialVersionUID = 1;
  private static final String MESSAGE = "nlf.exception";

  public NlfException(){
    super(App.getProperty(MESSAGE));
  }

  public NlfException(String message){
    super(message);
  }

  public NlfException(Throwable cause){
    this(App.getProperty(MESSAGE),cause);
  }

  public NlfException(String message,Throwable cause){
    super(message,cause);
  }
}