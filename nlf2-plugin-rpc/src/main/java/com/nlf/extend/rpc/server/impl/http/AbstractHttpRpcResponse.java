package com.nlf.extend.rpc.server.impl.http;

import com.nlf.App;
import com.nlf.core.AbstractResponse;
import com.nlf.util.IOUtil;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.zip.GZIPOutputStream;


/**
 * 抽象HTTP RPC响应
 *
 * @author 6tail
 *
 */
public abstract class AbstractHttpRpcResponse extends AbstractResponse implements IHttpRpcResponse{

  protected static final int BUFFER_SIZE = App.getPropertyInt("nlf.rpc.server.http.resource.buffer_size", IOUtil.BUFFER_SIZE);
  protected static final String ROOT = App.getPropertyString("nlf.rpc.server.http.resource.root", DEFAULT_ROOT);
  protected static final String CHARSET = App.getPropertyString("nlf.rpc.server.http.resource.charset", DEFAULT_CHARSET);
  protected static final String HOME_PAGE = App.getPropertyString("nlf.rpc.server.http.resource.home_page", DEFAULT_HOME_PAGE);
  protected static final boolean DIR_ALLOWED = App.getPropertyBoolean("nlf.rpc.server.http.resource.dir_allowed", DEFAULT_DIR_ALLOWED);
  protected static final boolean GZIP_ENABLE = App.getPropertyBoolean("nlf.rpc.server.http.resource.gzip.enable", DEFAULT_GZIP_ENABLE);
  protected static final int GZIP_MIN_SIZE = App.getPropertyInt("nlf.rpc.server.http.resource.gzip.min_size", DEFAULT_GZIP_MIN_SIZE);
  protected static final String GZIP_FILE_EXT = App.getPropertyString("nlf.rpc.server.http.resource.gzip.file_ext", DEFAULT_GZIP_FILE_EXT);

  protected static final String MSG_304 = App.getPropertyString("nlf.rpc.server.msg.304", DEFAULT_MSG_304);
  protected static final String MSG_403 = App.getPropertyString("nlf.rpc.server.msg.403", DEFAULT_MSG_403);
  protected static final String MSG_404 = App.getPropertyString("nlf.rpc.server.msg.404", DEFAULT_MSG_404);
  protected static final String MSG_HTML = App.getPropertyString("nlf.rpc.server.msg.html", DEFAULT_MSG_HTML);

  protected static final DateFormat DATE_FORMAT = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);

  protected HttpExchange exchange;

  public HttpExchange getHttpExchange() {
    return exchange;
  }

  public void setHttpExchange(HttpExchange exchange) {
    this.exchange = exchange;
  }

  protected byte[] gzip(File file) throws IOException {
    InputStream in = null;
    GZIPOutputStream gs = null;
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try {
      in = new FileInputStream(file);
      gs = new GZIPOutputStream(os);
      byte[] buffer = new byte[BUFFER_SIZE];
      int n = 0;
      while (-1 != (n = in.read(buffer))) {
        gs.write(buffer, 0, n);
      }
    } finally {
      IOUtil.closeQuietly(gs);
      IOUtil.closeQuietly(in);
    }
    return os.toByteArray();
  }
}
