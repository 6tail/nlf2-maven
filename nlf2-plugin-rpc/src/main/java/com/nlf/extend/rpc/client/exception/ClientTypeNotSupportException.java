package com.nlf.extend.rpc.client.exception;

import com.nlf.App;

/**
 * 不支持的客户端类型
 *
 * @author 6tail
 */
public class ClientTypeNotSupportException extends IllegalArgumentException {
  private static final long serialVersionUID = 1;
  private static final String MESSAGE = "nlf.rpc.client.exception.type_not_support";

  public ClientTypeNotSupportException() {
    super(App.getProperty(MESSAGE));
  }

  public ClientTypeNotSupportException(String message) {
    super(message);
  }

  public ClientTypeNotSupportException(Throwable cause) {
    this(App.getProperty(MESSAGE), cause);
  }

  public ClientTypeNotSupportException(String message, Throwable cause) {
    super(message, cause);
  }
}
