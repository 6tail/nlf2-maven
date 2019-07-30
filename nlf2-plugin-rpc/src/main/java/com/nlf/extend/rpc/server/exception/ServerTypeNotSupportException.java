package com.nlf.extend.rpc.server.exception;

import com.nlf.App;

/**
 * 不支持的服务端类型
 *
 * @author 6tail
 */
public class ServerTypeNotSupportException extends IllegalArgumentException {
  private static final long serialVersionUID = 1;
  private static final String MESSAGE = "nlf.rpc.server.exception.type_not_support";

  public ServerTypeNotSupportException() {
    super(App.getProperty(MESSAGE));
  }

  public ServerTypeNotSupportException(String message) {
    super(message);
  }

  public ServerTypeNotSupportException(Throwable cause) {
    this(App.getProperty(MESSAGE), cause);
  }

  public ServerTypeNotSupportException(String message, Throwable cause) {
    super(message, cause);
  }
}
