package session.redis;

import com.nlf.extend.session.web.IHttpSessionProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 基于redis的session提供器实现
 */
public class RedisSessionProvider implements IHttpSessionProvider {
  public HttpSession create(String sessionId, boolean autoCreate, HttpServletRequest request) {
    return RedisHttpSession.create(sessionId,autoCreate,request.getServletContext());
  }
}