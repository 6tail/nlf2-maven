package com.nlf.core;

import java.util.Date;

public abstract class AbstractResponse implements IResponse{
  public void send(Object o) throws java.io.IOException{
    if(null!=o) {
      if (o instanceof RuntimeException) {
        throw (RuntimeException) o;
      } else if (o instanceof Throwable) {
        throw new RuntimeException((Throwable) o);
      } else if (o instanceof Number || o instanceof Boolean || o instanceof Character || o instanceof String) {
        sendString(o + "");
      } else if (o instanceof com.nlf.view.JsonView) {
        sendString(o + "", "application/json");
      } else if (o instanceof Date) {
        sendString(com.nlf.util.DateUtil.ymdhms((Date) o) + "");
      }
    }
  }
}