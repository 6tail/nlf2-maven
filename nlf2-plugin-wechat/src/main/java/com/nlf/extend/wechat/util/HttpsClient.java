package com.nlf.extend.wechat.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import com.nlf.util.IOUtil;

/**
 * HTTPS客户端
 * 
 * @author 6tail
 *
 */
public class HttpsClient{
  private HttpsClient(){}

  private static TrustManager trustManager = new X509TrustManager(){
    public void checkClientTrusted(X509Certificate[] chain,String authType){}

    public void checkServerTrusted(X509Certificate[] chain,String authType){}

    public X509Certificate[] getAcceptedIssuers(){
      return null;
    }
  };

  /**
   * GET请求
   * 
   * @param url URL
   * @return 返回结果
   * @throws IOException
   * @throws NoSuchAlgorithmException
   * @throws KeyManagementException
   */
  public static String get(String url) throws IOException,NoSuchAlgorithmException,KeyManagementException{
    SSLContext ssl = SSLContext.getInstance("TLS");
    ssl.init(null,new TrustManager[]{trustManager},null);
    HttpsURLConnection conn = (HttpsURLConnection)new URL(url).openConnection();
    conn.setDoOutput(true);
    conn.setRequestMethod("GET");
    conn.setSSLSocketFactory(ssl.getSocketFactory());
    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
    String line = null;
    StringBuilder sb = new StringBuilder();
    while((line = in.readLine())!=null){
      sb.append(line);
    }
    conn.disconnect();
    return sb.toString();
  }

  /**
   * POST请求
   * 
   * @param url URL
   * @param data 数据内容
   * @return 返回结果
   * @throws IOException
   * @throws NoSuchAlgorithmException
   * @throws KeyManagementException
   */
  public static String post(String url,String data) throws IOException,NoSuchAlgorithmException,KeyManagementException{
    SSLContext ssl = SSLContext.getInstance("TLS");
    ssl.init(null,new TrustManager[]{trustManager},null);
    HttpsURLConnection conn = (HttpsURLConnection)new URL(url).openConnection();
    conn.setDoOutput(true);
    conn.setRequestMethod("POST");
    conn.setSSLSocketFactory(ssl.getSocketFactory());
    OutputStream out = conn.getOutputStream();
    out.write(data.getBytes("utf-8"));
    out.flush();
    out.close();
    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
    String line = null;
    StringBuilder sb = new StringBuilder();
    while((line = in.readLine())!=null){
      sb.append(line);
    }
    conn.disconnect();
    return sb.toString();
  }
  
  /**
   * POST请求
   * @param certFile 证书文件
   * @param password 密码
   * @param url URL
   * @param data 数据内容
   * @return 返回结果
   * @throws IOException 
   * @throws UnrecoverableKeyException 
   * @throws NoSuchAlgorithmException 
   * @throws KeyStoreException 
   * @throws CertificateException 
   * @throws KeyManagementException 
   */
  public static String post(File certFile,String password,String url,String data) throws IOException, KeyStoreException, NoSuchAlgorithmException, UnrecoverableKeyException, CertificateException, KeyManagementException{
    HttpsURLConnection conn = (HttpsURLConnection)new URL(url).openConnection();
    conn.setDoOutput(true);
    conn.setRequestMethod("POST");
    KeyStore ks  = KeyStore.getInstance("PKCS12");
    InputStream fin = null;
    try{
      fin = new FileInputStream(certFile);
      ks.load(fin,password.toCharArray());
    }finally{
      IOUtil.closeQuietly(fin);
    }
    KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
    kmf.init(ks,password.toCharArray());
    KeyManager[] kms = kmf.getKeyManagers();
    KeyStore caks = KeyStore.getInstance("JKS");
    try{
      fin = new FileInputStream(new File(System.getProperty("java.home") + File.separatorChar + "lib" + File.separatorChar + "security"+File.separatorChar+"cacerts"));
      caks.load(fin,"changeit".toCharArray());
    }finally{
      IOUtil.closeQuietly(fin);
    }
    TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
    tmf.init(caks);
    TrustManager[] tms = tmf.getTrustManagers();
    SSLContext ssl = SSLContext.getInstance("TLSv1");
    ssl.init(kms, tms, new SecureRandom());
    conn.setSSLSocketFactory(ssl.getSocketFactory());
    OutputStream out = conn.getOutputStream();
    out.write(data.getBytes("utf-8"));
    out.flush();
    out.close();
    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
    String line = null;
    StringBuilder sb = new StringBuilder();
    while((line = in.readLine())!=null){
      sb.append(line);
    }
    conn.disconnect();
    return sb.toString();
  }
}