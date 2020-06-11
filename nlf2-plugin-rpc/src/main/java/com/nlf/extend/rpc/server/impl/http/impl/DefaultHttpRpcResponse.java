package com.nlf.extend.rpc.server.impl.http.impl;

import com.nlf.App;
import com.nlf.core.Statics;
import com.nlf.extend.rpc.server.impl.http.AbstractHttpRpcResponse;
import com.nlf.extend.rpc.server.impl.http.HttpRpcServer;
import com.nlf.extend.web.view.PageView;
import com.nlf.extend.web.view.RedirectView;
import com.nlf.extend.web.view.StreamView;
import com.nlf.util.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 默认HTTP RPC响应
 *
 * @author 6tail
 */
public class DefaultHttpRpcResponse extends AbstractHttpRpcResponse {
  @Override
  public void send(Object o) throws IOException {
    if (null == o) {
      return;
    }
    if (o instanceof RedirectView) {
      sendRedirect((RedirectView) o);
    } else if (o instanceof PageView) {
      sendPage((PageView) o);
    } else if (o instanceof StreamView) {
      sendStream((StreamView) o);
    } else {
      super.send(o);
    }
  }

  public void sendRedirect(RedirectView view) throws IOException {
    exchange.getResponseHeaders().add("refresh", "0,url=" + view.getUri());
    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
    OutputStream os = null;
    try {
      os = exchange.getResponseBody();
      os.flush();
    } finally {
      IOUtil.closeQuietly(os);
    }
  }

  public void sendPage(PageView view) throws IOException {
    sendResource(view.getUri(), true);
  }

  public void sendString(String s) throws IOException {
    sendString(s, ContentTypes.PLAIN_TEXT);
  }

  public void sendString(String s, String contentType) throws IOException {
    if (App.getPropertyBoolean(KEY_CORS_ENABLE, true)) {
      exchange.getResponseHeaders().add(ACCESS_CONTROL_ALLOW_CREDENTIALS, App.getPropertyBoolean(KEY_CORS_ALLOW_CREDENTIALS, true) + "");
      exchange.getResponseHeaders().add(ACCESS_CONTROL_ALLOW_ORIGIN, App.getPropertyString(KEY_CORS_ALLOW_ORIGIN, "*"));
      exchange.getResponseHeaders().add(ACCESS_CONTROL_ALLOW_METHODS, App.getPropertyString(KEY_CORS_ALLOW_METHODS, "*"));
      exchange.getResponseHeaders().add(ACCESS_CONTROL_ALLOW_HEADERS, App.getPropertyString(KEY_CORS_ALLOW_HEADERS, "*"));
      exchange.getResponseHeaders().add(ACCESS_CONTROL_MAX_AGE, App.getPropertyInt(KEY_CORS_MAX_AGE, 18000) + "");
    }
    responseString(HttpURLConnection.HTTP_OK, contentType, s);
  }

  public void sendStream(StreamView streamView) throws IOException {
    InputStream inputStream = streamView.getInputStream();
    if (null != streamView.getName()) {
      exchange.getResponseHeaders().add(CONTENT_DISPOSITION, "attachment;filename=" + URLEncoder.encode(streamView.getName(), Statics.ENCODE));
    }
    String contentType = streamView.getContentType();
    if (null == contentType || contentType.length() < 1) {
      contentType = ContentTypes.DEFAULT;
    }
    if (streamView.getSize() > -1) {
      exchange.getResponseHeaders().add(CONTENT_LENGTH, streamView.getSize() + "");
    }
    sendStream(inputStream, contentType);
  }

  protected void sendStream(InputStream inputStream, String contentType) throws IOException {
    exchange.getResponseHeaders().add(CONTENT_TYPE, contentType);
    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
    OutputStream os = null;
    try {
      os = exchange.getResponseBody();
      int n = 0;
      byte[] b = new byte[IOUtil.BUFFER_SIZE];
      while ((n = inputStream.read(b)) != -1) {
        os.write(b, 0, n);
      }
      os.flush();
    } finally {
      IOUtil.closeQuietly(os);
      IOUtil.closeQuietly(inputStream);
    }
  }

  public void sendStream(InputStream inputStream) throws IOException {
    sendStream(inputStream, ContentTypes.DEFAULT);
  }

  public void sendResource(String path, boolean dynamic) throws IOException {
    if(path.equals(HttpRpcServer.contextPath)||path.startsWith(HttpRpcServer.contextPath+Strings.SLASH_LEFT)){
      path = path.substring(HttpRpcServer.contextPath.length());
    }
    while (path.startsWith(Strings.SLASH_LEFT)) {
      path = path.substring(1);
    }
    File file;
    if (ROOT.length() > 0) {
      file = new File(ROOT, path);
    } else {
      file = new File(path);
    }
    file = new File(file.getAbsolutePath());

    if (!file.exists()) {
      responseError(HttpURLConnection.HTTP_NOT_FOUND);
    } else if (!file.canRead()) {
      responseError(HttpURLConnection.HTTP_FORBIDDEN);
    } else if (file.isDirectory()) {
      if (DIR_ALLOWED) {
        responseDirectory(file);
      } else {
        File homePageFile = new File(file, HOME_PAGE);
        if (!homePageFile.exists() || !homePageFile.canRead()) {
          responseError(HttpURLConnection.HTTP_FORBIDDEN);
        } else {
          responseFile(homePageFile, dynamic);
        }
      }
    } else {
      responseFile(file, dynamic);
    }
  }

  protected void responseDirectory(File directory) throws IOException {
    String path = exchange.getRequestURI().getRawPath();
    if (!path.endsWith(Strings.SLASH_LEFT)) {
      path += Strings.SLASH_LEFT;
    }
    StringBuilder s = new StringBuilder()
            .append("<!DOCTYPE html><html><head><meta charset=\"")
            .append(CHARSET)
            .append("\" /><title>")
            .append(path)
            .append("</title></head><body><h3>")
            .append(path)
            .append("</h3><ul><li><a href=\"../\">..</a></li>");
    File[] files = directory.listFiles();
    if (null != files) {
      for (File file : files) {
        if (!file.canRead()) {
          continue;
        }
        String fileName = file.getName();
        s.append("<li><a href=\"")
                .append(path)
                .append(fileName)
                .append("\">")
                .append(fileName)
                .append("</a></li>");
      }
    }
    s.append("</ul></body></html>");
    responseOk(s.toString());
  }

  protected void responseOk(String html) throws IOException {
    responseHtml(HttpURLConnection.HTTP_OK, html);
  }

  protected void responseError(int code) throws IOException {
    String msg = "";
    switch (code) {
      case HttpURLConnection.HTTP_FORBIDDEN:
        msg = String.format(MSG_HTML, CHARSET, MSG_403, MSG_403);
        break;
      case HttpURLConnection.HTTP_NOT_FOUND:
        msg = String.format(MSG_HTML, CHARSET, MSG_404, MSG_404);
        break;
      default:
    }
    responseHtml(code, msg);
  }

  protected void responseHtml(int code, String html) throws IOException {
    responseString(code, "text/html", html);
  }

  protected void responseString(int code, String contentType, String s) throws IOException {
    byte[] content = s.getBytes(CHARSET);
    exchange.getResponseHeaders().add(CONTENT_TYPE, String.format("%s; charset=%s", contentType, CHARSET));
    exchange.sendResponseHeaders(code, content.length);
    responseBytes(content);
  }

  protected void responseBytes(byte[] data) throws IOException {
    OutputStream os = null;
    try {
      os = exchange.getResponseBody();
      os.write(data);
      os.flush();
    } finally {
      IOUtil.closeQuietly(os);
    }
  }

  protected void responseFile(File file, boolean dynamic) throws IOException {
    if (dynamic) {
      responseDynamicFile(file);
    } else {
      responseStaticFile(file);
    }
  }

  protected void responseDynamicFile(File file) throws IOException {
    String html = FileUtil.readAsText(file);
    responseOk(html);
  }

  protected void responseStaticFile(File file) throws IOException {
    long length = file.length();
    long lastModified = file.lastModified();
    String etag = String.format("%s-%s", Long.toHexString(lastModified), Long.toHexString(length));
    String ifNoneMatch = exchange.getRequestHeaders().getFirst(IF_NONE_MATCH);
    String modifiedSince = DATE_FORMAT.format(new Date(lastModified));
    String ifModifiedSince = exchange.getRequestHeaders().getFirst(IF_MODIFIED_SINCE);
    if (null != ifNoneMatch) {
      if (etag.equals(ifNoneMatch)) {
        responseHtml(HttpURLConnection.HTTP_NOT_MODIFIED, MSG_304);
        return;
      }
    } else if (null != ifModifiedSince) {
      if (modifiedSince.equals(ifModifiedSince)) {
        responseHtml(HttpURLConnection.HTTP_NOT_MODIFIED, MSG_304);
        return;
      }
    }
    String fileName = file.getName();
    String ext = "";
    if (fileName.contains(Strings.DOT)) {
      ext = fileName.substring(fileName.lastIndexOf(Strings.DOT));
    }
    exchange.getResponseHeaders().add(CONTENT_TYPE, ContentTypes.getContentType(ext));
    exchange.getResponseHeaders().add(LAST_MODIFIED, modifiedSince);
    exchange.getResponseHeaders().add(ETAG, etag);

    //支持的编码方式
    List<String> acceptEncodings = new ArrayList<String>();
    String acceptEncoding = exchange.getRequestHeaders().getFirst(ACCEPT_ENCODING);
    if (null != acceptEncoding) {
      acceptEncodings = StringUtil.list(acceptEncoding, Strings.COMMA);
    }
    //支持的文件扩展名
    List<String> exts = StringUtil.list(GZIP_FILE_EXT, Strings.COMMA);
    if (acceptEncodings.contains(ENCODING_GZIP) && GZIP_ENABLE && length >= GZIP_MIN_SIZE && exts.contains(ext)) {
      responseGzip(file);
    } else {
      responseStream(file);
    }
  }

  protected void responseStream(File file) throws IOException {
    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, file.length());
    InputStream in = null;
    OutputStream os = null;
    try {
      in = new FileInputStream(file);
      os = exchange.getResponseBody();
      byte[] buffer = new byte[BUFFER_SIZE];
      int n = 0;
      while (-1 != (n = in.read(buffer))) {
        os.write(buffer, 0, n);
      }
      os.flush();
    } finally {
      IOUtil.closeQuietly(in);
      IOUtil.closeQuietly(os);
    }
  }

  protected void responseGzip(File file) throws IOException {
    byte[] data = gzip(file);
    exchange.getResponseHeaders().add(CONTENT_ENCODING, ENCODING_GZIP);
    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, data.length);
    responseBytes(data);
  }
}
