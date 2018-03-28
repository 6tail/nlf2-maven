package com.nlf.extend.web;

import javax.servlet.http.HttpSession;
import com.nlf.core.AbstractSession;

/**
 * 抽象WEB会话
 * 
 * @author 6tail
 *
 */
public abstract class AbstractWebSession extends AbstractSession implements IWebSession{
  /** HttpSession */
  protected HttpSession session;

  public HttpSession getSession(){
    return session;
  }

  public void setSession(HttpSession session){
    this.session = session;
  }
}