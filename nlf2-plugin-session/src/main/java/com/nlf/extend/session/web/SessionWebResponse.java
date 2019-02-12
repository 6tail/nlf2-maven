package com.nlf.extend.session.web;

import com.nlf.App;
import com.nlf.core.ISession;
import com.nlf.extend.web.IWebRequest;
import com.nlf.extend.web.impl.DefaultWebResponse;

import java.io.IOException;

/**
 * 自定义WebResponse，以便在请求响应时写session id
 */
public class SessionWebResponse extends DefaultWebResponse {
  public void send(Object o) throws IOException {
    IWebRequest request = (IWebRequest) App.getRequest();
    ISession session = request.getSession(false);
    if(null!=session){
      IHttpSessionIdProvider provider = App.getProxy().newInstance(IHttpSessionIdProvider.class.getName());
      provider.setHttpServletRequest(request.getServletRequest());
      provider.setHttpServletResponse(servletResponse);
      provider.write(session.getId());
    }
    super.send(o);
  }
}