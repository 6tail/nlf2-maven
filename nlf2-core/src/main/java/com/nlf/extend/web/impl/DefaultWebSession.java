package com.nlf.extend.web.impl;

import java.util.Enumeration;
import com.nlf.extend.web.AbstractWebSession;

/**
 * 默认WEB会话
 * 
 * @author 6tail
 *
 */
public class DefaultWebSession extends AbstractWebSession{
  @SuppressWarnings("unchecked")
  public <T>T getAttribute(String name){
    return (T)session.getAttribute(name);
  }

  public Enumeration<String> getAttributeNames(){
    return session.getAttributeNames();
  }

  public long getCreationTime(){
    return session.getCreationTime();
  }

  public String getId(){
    return session.getId();
  }

  public long getLastAccessedTime(){
    return session.getLastAccessedTime();
  }

  public int getMaxInactiveInterval(){
    return session.getMaxInactiveInterval();
  }

  public void invalidate(){
    session.invalidate();
  }

  public boolean isNew(){
    return session.isNew();
  }

  public void removeAttribute(String name){
    session.removeAttribute(name);
  }

  public void setAttribute(String name,Object value){
    session.setAttribute(name,value);
  }

  public void setMaxInactiveInterval(int interval){
    session.setMaxInactiveInterval(interval);
  }
}