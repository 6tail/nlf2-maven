package com.nlf.core.impl;

import com.nlf.App;
import com.nlf.bytecode.ByteCodeReader;
import com.nlf.core.AbstractScanner;
import com.nlf.core.IScanner;
import com.nlf.resource.ResourceFileFilter;
import com.nlf.resource.i18n.I18nResource;
import com.nlf.resource.i18n.comparator.I18nComparator;
import com.nlf.resource.klass.ClassResource;
import com.nlf.resource.klass.comparator.ClassComparator;
import com.nlf.resource.klass.filter.JarFileFilter;
import com.nlf.util.IOUtil;
import com.nlf.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;
import java.util.Map.Entry;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static com.nlf.App.*;

/**
 * 默认扫描器
 *
 * @author 6tail
 *
 */
public class DefaultScanner extends AbstractScanner{
  public static final String CHARSET = "utf-8";
  public static final String SUF_JAR = ".jar";
  public static final String SUF_CLS = ".class";
  public static final String SUF_PPT = ".properties";
  /** jar文件过滤器 */
  protected JarFileFilter jarFilter = new JarFileFilter();
  /** resource文件过滤器 */
  protected ResourceFileFilter resourceFilter = new ResourceFileFilter();
  /** 类比较器 */
  protected ClassComparator classComparator = new ClassComparator();
  /** i18n比较器 */
  protected I18nComparator i18nComparator = new I18nComparator();
  /** 字节码读取器 */
  protected ByteCodeReader byteCodeReader = new ByteCodeReader();

  /** jar路径 */
  protected Set<String> jars = new HashSet<String>();
  /** 字节码路径 */
  protected Set<String> classes = new HashSet<String>();

  public DefaultScanner(){
    ignoreJarByManifestAttribute("Bundle-Name","*swagger*","*google*");
    ignoreJarByManifestAttribute("Bundle-Vendor","*apache software*","*eclipse*","*google*","*oracle*","*springsource*","%bundleprovider","%bundle-vendor*","*mybatis*","*slf4j*","*fasterxml*","*terracotta*","*jboss*","*fasterml*");
    ignoreJarByManifestAttribute("Created-By","*alibaba*","*apache software*","*apple*","*ibm*","*oracle*","*signtool*","*sun microsystems*","*jetbrains*");
    ignoreJarByManifestAttribute("Implementation-Vendor","*alibaba*","*apache*","*metastuff*","*mysql*","*oracle*","*sun microsystems*","*hibernate*","*aspectj*","*junit*","*coobird*","*codehaus*");
    allowJarByManifestAttribute("Built-By","6tail");
  }

  /**
   * 筛选路径
   * @param paths 路径
   * @return 筛选后的路径
   */
  protected Set<String> filterPath(String... paths){
    Set<String> l = new HashSet<String>();
    for(String p:paths){
      if(null==p){
        continue;
      }
      p = p.trim();
      if(p.length()<1){
        continue;
      }
      File f = new File(p);
      if(!f.exists()){
        continue;
      }
      if(f.isDirectory()){
        String path = f.getAbsolutePath();
        if(path.endsWith(File.separator+".")){
          path = path.substring(0,path.lastIndexOf(File.separator));
        }
        l.add(path);
      }else if(f.getName().endsWith(SUF_JAR)){
        l.add(f.getAbsolutePath());
      }
    }
    return l;
  }

  /**
   * 寻找classpath们
   *
   * @return classpath们
   */
  protected Set<String> findFromClassPath(){
    return filterPath(System.getProperty("java.class.path").split(File.pathSeparator));
  }

  /**
   * 寻找NLF框架调用者所在路径
   *
   * @return NLF框架调用者所在路径
   * @throws ClassNotFoundException ClassNotFoundException
   * @throws UnsupportedEncodingException UnsupportedEncodingException
   */
  protected String findCallerPath() throws ClassNotFoundException, UnsupportedEncodingException{
    if(null==App.caller){
      if(null==caller){
        StackTraceElement[] sts = Thread.currentThread().getStackTrace();
        //调用框架的类
        String callerClassName = null;
        Class<?> callerClass = null;

        for(int i=sts.length-1;i>-1;i--){
          StackTraceElement t = sts[i];
          String className = t.getClassName();
          if(className.startsWith(App.PACKAGE)){
            callerClass = Class.forName(callerClassName);
            if(null!=callerClass.getClassLoader()){
              break;
            }
          }
          callerClassName = className;
        }
        if(null!=callerClass) {
          String callerPath = callerClass.getProtectionDomain().getCodeSource().getLocation().getPath();
          App.caller = new File(URLDecoder.decode(callerPath, CHARSET)).getAbsolutePath();
        }
      }else{
        App.caller = caller;
      }
    }
    return App.caller;
  }

  protected String findFramePath() throws UnsupportedEncodingException {
    if(null==App.frame) {
      Class<?> i = IScanner.class;
      String framePath = i.getProtectionDomain().getCodeSource().getLocation().getPath();
      App.frame = new File(URLDecoder.decode(framePath,CHARSET)).getAbsolutePath();
    }
    return App.frame;
  }

  /**
   * 寻找框架调用者(如果是jar)引用的Class-Path
   *
   * @return classpath们
   * @throws IOException IO异常
   */
  protected Set<String> findFromCallerClassPath() throws IOException{
    Set<String> classPaths = new HashSet<String>();
    String callerPath = App.caller;
    if(callerPath.endsWith(SUF_JAR)){
      JarFile jar = null;
      try{
        jar = new JarFile(callerPath);
        Manifest mf = jar.getManifest();
        if(null!=mf){
          java.util.jar.Attributes attrs = mf.getMainAttributes();
          String classPath = attrs.getValue("Class-Path");
          if(null!=classPath){
            String[] cps = classPath.split(" ");
            classPaths.addAll(filterPath(cps));
          }
        }
      }finally{
        IOUtil.closeQuietly(jar);
      }
    }
    return classPaths;
  }

  protected Set<String> convertToAbsolutePaths(Set<String> paths){
    Set<String> l = new HashSet<String>();
    for(String p:paths){
      File f = new File(App.root,p);
      if(f.exists()){
        l.add(f.getAbsolutePath());
      }
    }
    return l;
  }

  public IScanner start(){
    try{
      findCallerPath();
      findAppRoot();
      findFramePath();
      System.out.println("App.caller         = "+App.caller);
      System.out.println("App.root           = "+App.root);
      System.out.println("App.frame          = "+App.frame);
      Set<String> paths = new HashSet<String>();
      paths.add(App.caller);
      paths.add(App.root);
      paths.add(App.frame);
      paths.addAll(findFromClassPath());
      paths.addAll(findFromCallerClassPath());
      paths.addAll(addedAbsolutePaths);
      paths.addAll(convertToAbsolutePaths(addedRelativePaths));

      for(String p:paths){
        if(p.endsWith(SUF_JAR)){
          jars.add(p);
        }else{
          classes.add(p);
        }
      }
      scan();
      buildInterface();
      buildImpls();
      buildI18n();
    }catch(Exception e){
      throw new com.nlf.exception.NlfException(e);
    }
    return this;
  }

  protected void buildI18n(){
    if(I18N.size()>1){
      List<String> l = new ArrayList<String>(I18N.size());
      l.addAll(I18N);
      Collections.sort(l,i18nComparator);
      I18N.clear();
      I18N.addAll(l);
    }
  }

  protected void buildImpls(){
    INTERFACE_IMPLEMENTS.clear();
    for(ClassResource c:CLASS.values()){
      for(String it:c.getInterfaces()){
        if(!CLASS.containsKey(it)){
          continue;
        }
        List<String> l = INTERFACE_IMPLEMENTS.get(it);
        if(null==l){
          l = new ArrayList<String>();
          INTERFACE_IMPLEMENTS.put(it,l);
        }
        if(!c.isAbstractClass()){
          l.add(c.getClassName());
        }
      }
    }
    for(Entry<String,List<String>> entry:INTERFACE_IMPLEMENTS.entrySet()){
      List<String> l = entry.getValue();
      if(l.size()>1){
        Collections.sort(l,classComparator);
      }
    }
  }

  protected void buildInterface(){
    for(ClassResource ci:CLASS.values()){
      ci.setInterfaces(byteCodeReader.readInterfaces(ci));
    }
  }

  protected boolean isModuleInfo(String className) {
    return "module-info".equals(className) || (className.contains(".") && ".module-info".equals(className.substring(className.lastIndexOf("."))));
  }

  protected void scanClasses(File file,String root){
    if(file.isDirectory()){
      File[] fs = file.listFiles(resourceFilter);
      if(null!=fs) {
        for (File f : fs) {
          scanClasses(f, root);
        }
      }
      return;
    }
    String fileName = file.getAbsolutePath().replace(root,"");
    if(fileName.startsWith(File.separator)){
      fileName = fileName.substring(File.separator.length());
    }
    if(fileName.endsWith(SUF_CLS)){
      String name = fileName.substring(0,fileName.lastIndexOf(".")).replace(File.separator,".");
      if(isModuleInfo(name)){
        return;
      }
      ClassResource cr = new ClassResource();
      cr.setClassName(name);
      cr.setRoot(root);
      cr.setInJar(false);
      cr.setFileName(file.getName());
      CLASS.put(name,cr);
    }else if(fileName.endsWith(SUF_PPT)){
      String name = fileName.substring(0,fileName.lastIndexOf(".")).replace(File.separator,".");
      for(Locale locale:Locale.getAvailableLocales()){
        String tag = "_"+locale.getLanguage();
        if(name.endsWith(tag)){
          name = name.substring(0,name.lastIndexOf(tag));
          break;
        }
      }
      I18nResource ir = new I18nResource();
      ir.setRoot(root);
      ir.setInJar(false);
      ir.setName(name);
      ir.setFileName(fileName);
      I18N_RESOURCE.add(ir);
      I18N.add(name);
    }
  }

  protected boolean matchAttributes(Attributes attrs, Map<String,Set<String>> targets){
    for(Entry<String,Set<String>> entry:targets.entrySet()){
      for(String value:entry.getValue()){
        String attrValue = attrs.getValue(entry.getKey());
        if(null==attrValue){
          continue;
        }
        if(StringUtil.matches(attrValue.toLowerCase(),value)){
          return true;
        }
      }
    }
    return false;
  }

  protected boolean matchPath(String path, Set<String> targets){
    for(String t:targets){
      if(StringUtil.matches(path,t)){
        return true;
      }
    }
    return false;
  }

  protected void scanJar(File jarFile) throws IOException{
    String root = jarFile.getAbsolutePath();
    if(matchPath(root,ignoredPaths)&&!matchPath(root,allowPaths)){
      return;
    }
    JarFile jar = null;
    try{
      jar = new JarFile(jarFile);
      Manifest mf = jar.getManifest();
      if(null!=mf){
        Attributes attrs = mf.getMainAttributes();
        if(matchAttributes(attrs,ignoredManifestAttributes)&&!matchAttributes(attrs,allowManifestAttributes)){
          return;
        }
        for(Attributes entry:mf.getEntries().values()){
          if(matchAttributes(entry,ignoredManifestAttributes)&&!matchAttributes(entry,allowManifestAttributes)){
            return;
          }
        }
      }
    }finally{
      IOUtil.closeQuietly(jar);
    }
    System.out.println("[v] "+root);
    ZipFile zip = null;
    try{
      zip = new ZipFile(jarFile);
      Enumeration<?> entries = zip.entries();
      while(entries.hasMoreElements()){
        ZipEntry entry = (ZipEntry)entries.nextElement();
        String fileName = entry.getName();
        if(fileName.endsWith(SUF_CLS)){
          String name = fileName.substring(0,fileName.lastIndexOf(".")).replace("/",".");
          if(isModuleInfo(name)){
            continue;
          }
          ClassResource ci = new ClassResource();
          ci.setClassName(name);
          ci.setRoot(root);
          ci.setInJar(true);
          ci.setFileName(entry.getName());
          CLASS.put(name,ci);
        }else if(fileName.endsWith(SUF_PPT)){
          String name = fileName.substring(0,fileName.lastIndexOf(".")).replace("/",".");
          for(Locale locale:Locale.getAvailableLocales()){
            String tag = "_"+locale;
            if(name.endsWith(tag)){
              name = name.substring(0,name.lastIndexOf(tag));
              break;
            }
          }
          I18nResource ir = new I18nResource();
          ir.setRoot(root);
          ir.setInJar(true);
          ir.setName(name);
          ir.setFileName(fileName);
          I18N_RESOURCE.add(ir);
          I18N.add(name);
        }
      }
    }finally{
      IOUtil.closeQuietly(zip);
    }
  }

  protected void scan() throws IOException{
    I18N_RESOURCE.clear();
    for(String p:classes){
      System.out.println("[v] "+p);
      App.DIRECTORIES.add(p);
      scanClasses(new File(p),p);
    }
    for(String p:jars){
      scanJar(new File(p));
    }
  }

  /**
   * 寻找应用根目录
   *
   * @return 应用根目录
   */
  protected String findAppRoot(){
    if(null==App.root){
      String callerPath = App.caller;
      App.root = callerPath.endsWith(SUF_JAR)?new File(callerPath).getParentFile().getAbsolutePath():callerPath;
    }
    return App.root;
  }
}
