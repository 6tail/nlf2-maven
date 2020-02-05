package com.nlf.extend.rpc.server.impl.http.impl;

import com.nlf.App;
import com.nlf.extend.rpc.server.impl.http.AbstractHttpRpcResourceHandler;
import com.nlf.util.ContentTypes;
import com.nlf.util.IOUtil;
import com.nlf.util.Strings;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.HttpURLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 默认HTTP RPC资源处理器
 *
 * @author 6tail
 */
public class DefaultHttpRpcResourceHandler extends AbstractHttpRpcResourceHandler {

  public static final String CONTENT_TYPE = "Content-Type";
  public static final String LAST_MODIFIED = "Last-Modified";
  public static final String MSG_403 = "403 Forbidden";
  public static final String MSG_404 = "404 Not Found";

  public static final String DEFAULT_ROOT = "";
  public static final String DEFAULT_CHARSET = "UTF-8";
  public static final String DEFAULT_HOME_PAGE = "index.html";
  public static final boolean DEFAULT_DIR_ALLOWED = true;

  protected static final int BUFFER_SIZE = App.getPropertyInt("nlf.rpc.server.http.resource.buffer_size", IOUtil.BUFFER_SIZE);
  protected static final String ROOT = App.getPropertyString("nlf.rpc.server.http.resource.root", DEFAULT_ROOT);
  protected static final String CHARSET = App.getPropertyString("nlf.rpc.server.http.resource.charset", DEFAULT_CHARSET);
  protected static final String HOME_PAGE = App.getPropertyString("nlf.rpc.server.http.resource.home_page", DEFAULT_HOME_PAGE);
  protected static final boolean DIR_ALLOWED = App.getPropertyBoolean("nlf.rpc.server.http.resource.dir_allowed", DEFAULT_DIR_ALLOWED);

  protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);

  public void handle(HttpExchange exchange) throws IOException {
    String path = exchange.getRequestURI().getRawPath();
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
    OutputStream os = exchange.getResponseBody();
    if (!file.exists()) {
      responseError(exchange, HttpURLConnection.HTTP_NOT_FOUND, os);
    } else if (!file.canRead()) {
      responseError(exchange, HttpURLConnection.HTTP_FORBIDDEN, os);
    } else if (file.isDirectory()) {
      if (DIR_ALLOWED) {
        responseDirectory(exchange, file, os);
      } else {
        File homePageFile = new File(file, HOME_PAGE);
        if (!homePageFile.exists() || !homePageFile.canRead()) {
          responseError(exchange, HttpURLConnection.HTTP_FORBIDDEN, os);
        } else {
          responseFile(exchange, homePageFile, os);
        }
      }
    } else {
      responseFile(exchange, file, os);
    }
    IOUtil.closeQuietly(os);
  }

  protected void responseDirectory(HttpExchange exchange, File directory, OutputStream os) throws IOException {
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
    responseHtml(exchange, s.toString().getBytes(CHARSET), os);
  }

  protected void responseHtml(HttpExchange exchange, byte[] content, OutputStream os) throws IOException {
    exchange.getResponseHeaders().add(CONTENT_TYPE, String.format("text/html; charset=%s", CHARSET));
    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, content.length);
    os.write(content);
  }

  protected void responseError(HttpExchange exchange, int code, OutputStream os) throws IOException {
    String msg = "<!DOCTYPE html><html><head><meta charset=\"%s\" /><title>%s</title></head><body><h3>%s</h3></body></html>";
    switch (code) {
      case HttpURLConnection.HTTP_FORBIDDEN:
        msg = String.format(msg, CHARSET, MSG_403, MSG_403);
        break;
      case HttpURLConnection.HTTP_NOT_FOUND:
        msg = String.format(msg, CHARSET, MSG_404, MSG_404);
        break;
      default:
    }
    byte[] content = msg.getBytes(CHARSET);
    exchange.getResponseHeaders().add(CONTENT_TYPE, String.format("text/html; charset=%s", CHARSET));
    exchange.sendResponseHeaders(code, content.length);
    os.write(content);
  }

  protected void responseFile(HttpExchange exchange, File file, OutputStream os) throws IOException {
    String fileName = file.getName();
    String ext = "";
    if (fileName.contains(Strings.DOT)) {
      ext = fileName.substring(0, fileName.lastIndexOf(Strings.DOT));
    }
    exchange.getResponseHeaders().add(CONTENT_TYPE, ContentTypes.getContentType(ext));
    exchange.getResponseHeaders().add(LAST_MODIFIED, DATE_FORMAT.format(new Date(file.lastModified())));
    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, file.length());
    InputStream in = null;
    try {
      in = new FileInputStream(file);
      byte[] buffer = new byte[BUFFER_SIZE];
      int n = 0;
      while (-1 != (n = in.read(buffer))) {
        os.write(buffer, 0, n);
      }
    } finally {
      IOUtil.closeQuietly(in);
    }
  }
}
