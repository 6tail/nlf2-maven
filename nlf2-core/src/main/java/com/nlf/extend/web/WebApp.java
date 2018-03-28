package com.nlf.extend.web;

import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import com.nlf.core.ScannerFactory;
import com.nlf.extend.web.impl.DefaultServlet;

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
  public static final Set<String> servletSuffixs = new HashSet<String>();

  public static void init(ServletContext servletContext){
    context = servletContext;
    realPath = servletContext.getRealPath("");
    contextPath = servletContext.getContextPath();
    libPath = servletContext.getRealPath("/WEB-INF/lib");
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
            servletSuffixs.add(suffix);
          }
        }
      }
    }
    System.out.println("WebApp.contextPath = "+contextPath);
    System.out.println("WebApp.realPath    = "+realPath);
    System.out.println("WebApp.libPath     = "+libPath);
    ScannerFactory.getScanner().setCaller(servletContext.getRealPath("/WEB-INF/classes")).addAbsolutePath(libPath);
    ScannerFactory.startScan();
  }
}