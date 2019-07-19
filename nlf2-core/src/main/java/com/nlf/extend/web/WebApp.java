package com.nlf.extend.web;

import java.io.File;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import com.nlf.core.ScannerFactory;
import com.nlf.extend.web.impl.DefaultServlet;
import com.nlf.resource.klass.filter.JarFileFilter;

/**
 * WEB应用
 * 
 * @author 6tail
 *
 */
public class WebApp{
  protected WebApp(){}

  /** 应用虚拟路径 */
  public static String contextPath = "";
  /** 应用真实路径 */
  public static String realPath = "";
  /** 应用LIB路径 */
  public static String libPath = "";
  public static ServletContext context;
  /** servlet后缀 */
  public static final Set<String> SERVLET_SUFFIXS = new HashSet<String>();

  public static void init(ServletContext servletContext){
    context = servletContext;
    realPath = servletContext.getRealPath("");
    contextPath = servletContext.getContextPath();
    libPath = servletContext.getRealPath("/WEB-INF/lib");
    System.out.println("WebApp.contextPath = "+contextPath);
    System.out.println("WebApp.realPath    = "+realPath);
    System.out.println("WebApp.libPath     = "+libPath);
    String servletClass = DefaultServlet.class.getName();
    for(Entry<String,?> entry:servletContext.getServletRegistrations().entrySet()){
      String key = entry.getKey();
      ServletRegistration servlet = (ServletRegistration)entry.getValue();
      String className = servlet.getClassName();
      if(null==className){
        className = servletClass;
        servletContext.addServlet(key,className);
      }
      if(servletClass.equals(className)){
        for(String mapping:servlet.getMappings()){
          if(mapping.startsWith("*.")){
            String suffix = mapping.substring(1);
            SERVLET_SUFFIXS.add(suffix);
          }
        }
      }
    }
    //发现lib下的jar
    String[] jarPaths = new String[0];
    File libDir = new File(libPath);
    if(libDir.exists()) {
      File[] jars = libDir.listFiles(new JarFileFilter());
      if (null != jars) {
        int jarCount = jars.length;
        jarPaths = new String[jarCount];
        for (int i = 0; i < jarCount; i++) {
          jarPaths[i] = jars[i].getAbsolutePath();
        }
      }
    }
    ScannerFactory.getScanner().setCaller(servletContext.getRealPath("/WEB-INF/classes")).addAbsolutePath(libPath).addAbsolutePath(jarPaths);
    ScannerFactory.startScan();
  }
}