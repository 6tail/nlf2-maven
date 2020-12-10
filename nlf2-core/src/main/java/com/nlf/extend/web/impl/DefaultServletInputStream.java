package com.nlf.extend.web.impl;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author 6tail
 */
public class DefaultServletInputStream extends ServletInputStream {

  protected int lastRead = 0;
  protected InputStream inputStream;

  public DefaultServletInputStream(InputStream inputStream) {
    this.inputStream = inputStream;
  }

  @Override
  public boolean isFinished() {
    return -1==lastRead;
  }

  @Override
  public boolean isReady() {
    return false;
  }

  @Override
  public void setReadListener(ReadListener readListener) {
  }

  @Override
  public int read() throws IOException {
    lastRead = inputStream.read();
    return lastRead;
  }

  @Override
  public int available() throws IOException {
    return inputStream.available();
  }

  @Override
  public int read(byte[] b) throws IOException {
    lastRead = inputStream.read(b);
    return lastRead;
  }

  @Override
  public int read(byte[] b, int off, int len) throws IOException {
    lastRead = inputStream.read(b, off, len);
    return lastRead;
  }

  @Override
  public long skip(long n) throws IOException {
    return inputStream.skip(n);
  }

  @Override
  public void close() throws IOException {
    inputStream.close();
  }
}
