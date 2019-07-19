package com.nlf.extend.web.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import com.nlf.App;
import com.nlf.Bean;
import com.nlf.core.Statics;
import com.nlf.extend.web.AbstractWebResponse;
import com.nlf.extend.web.IWebRequest;
import com.nlf.extend.web.view.PageView;
import com.nlf.extend.web.view.RedirectView;
import com.nlf.extend.web.view.StreamView;
import com.nlf.util.ContentTypes;
import com.nlf.util.IOUtil;

/**
 * 默认WEB响应
 * 
 * @author 6tail
 *
 */
public class DefaultWebResponse extends AbstractWebResponse{
  @Override
  public void send(Object o) throws IOException{
    if(null==o){
      return;
    }
    if(o instanceof RedirectView){
      sendRedirect((RedirectView)o);
    }else if(o instanceof PageView){
      sendPage((PageView)o);
    }else if(o instanceof StreamView){
      sendStream((StreamView)o);
    }else{
      super.send(o);
    }
  }

  public void sendString(String s) throws IOException{
    sendString(s,ContentTypes.PLAIN_TEXT);
  }

  public void sendString(String s,String contentType) throws IOException{
    servletResponse.setContentType(contentType+";charset="+Statics.ENCODE);
    servletResponse.setCharacterEncoding(Statics.ENCODE);
    servletResponse.getWriter().write(s);
    servletResponse.getWriter().flush();
  }

  public void sendRedirect(RedirectView v) throws IOException{
    servletResponse.sendRedirect(v.getUri());
  }

  public void sendPage(PageView v) throws IOException{
    IWebRequest request = App.get(Statics.REQUEST);
    HttpServletRequest req = request.getServletRequest();
    Bean attrs = v.getAttributes();
    Bean param = request.getParam();
    for(String key:param.keySet()){
      req.setAttribute(key,param.get(key));
    }
    for(String key:attrs.keySet()){
      req.setAttribute(key,attrs.get(key));
    }
    String includePath = (String)req.getAttribute(RequestDispatcher.INCLUDE_REQUEST_URI);
    try{
      if(null==includePath){
        req.getRequestDispatcher(v.getUri()).forward(req,servletResponse);
      }else{
        req.getRequestDispatcher(v.getUri()).include(req,servletResponse);
      }
    }catch(ServletException e){
      throw new RuntimeException(e);
    }
  }

  public void sendStream(StreamView streamView) throws IOException{
    InputStream inputStream = streamView.getInputStream();
    if(null!=streamView.getName()){
      servletResponse.setHeader("Content-Disposition","attachment;filename="+ URLEncoder.encode(streamView.getName(),"utf-8"));
    }
    String contentType = streamView.getContentType();
    if(null==contentType||contentType.length()<1){
      contentType = ContentTypes.DEFAULT;
    }
    if(streamView.getSize()>-1){
      servletResponse.setHeader("Content-Length",streamView.getSize()+"");
    }
    sendStream(inputStream,contentType);
  }

  protected  void sendStream(InputStream inputStream,String contentType) throws IOException{
    servletResponse.setContentType(contentType);
    OutputStream os = null;
    try{
      os = servletResponse.getOutputStream();
      int n = 0;
      byte[] b = new byte[IOUtil.BUFFER_SIZE];
      while((n = inputStream.read(b))!=-1){
        os.write(b,0,n);
      }
      os.flush();
    }finally{
      IOUtil.closeQuietly(os);
      IOUtil.closeQuietly(inputStream);
    }
  }

  public void sendStream(InputStream inputStream) throws IOException{
    sendStream(inputStream,ContentTypes.DEFAULT);
  }
}