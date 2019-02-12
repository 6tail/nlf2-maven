package com.nlf.extend.session.web.impl;

import com.nlf.App;
import com.nlf.extend.session.web.AbstractHttpSessionProvider;
import javax.servlet.http.Cookie;

/**
 * session id 提供器的一种实现：将session id存放到Cookie中
 */
public class CookieSessionIdProvider extends AbstractHttpSessionProvider {

  /** 默认的session id存放的Cookie名称 */
  public static final String DEFAULT_ID_NAME = "SESSIONID";

  /** session id存放的Cookie名称 */
  public static String ID_NAME = App.getPropertyString("session.id.provider.cookie.name",DEFAULT_ID_NAME);

  public String read() {
    String sessionId = null;
    for(Cookie cookie:request.getCookies()){
      String name = cookie.getName();
      if(ID_NAME.equals(name)){
        sessionId = cookie.getValue();
      }
    }
    return sessionId;
  }

  public void write(String sessionId) {
    if(null!=sessionId){
      String existSessionId = null;
      for(Cookie cookie:request.getCookies()){
        String name = cookie.getName();
        if(ID_NAME.equals(name)){
          existSessionId = cookie.getValue();
        }
      }
      if(!sessionId.equals(existSessionId)) {
        Cookie cookie = new Cookie(ID_NAME, sessionId);
        cookie.setPath("/");
        response.addCookie(cookie);
      }
    }
  }

}