package com.nlf.extend.serialize.xml.exception;

import com.nlf.App;
import com.nlf.exception.NlfException;

/**
 * 不支持的XML格式
 *
 * @author 6tail
 *
 */
public class XmlFormatException extends NlfException{
  private static final long serialVersionUID = 1;
  private static final String MESSAGE = "nlf.serialize.xml.format";

  public XmlFormatException(){
    super(App.getProperty(MESSAGE));
  }

  public XmlFormatException(String message){
    super(App.getProperty(MESSAGE,message));
  }

  public XmlFormatException(Throwable cause){
    this(App.getProperty(MESSAGE),cause);
  }

  public XmlFormatException(String message,Throwable cause){
    super(App.getProperty(MESSAGE,message),cause);
  }
}