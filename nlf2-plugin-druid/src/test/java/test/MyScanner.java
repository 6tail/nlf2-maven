package test;

import com.nlf.core.IScanner;
import com.nlf.core.impl.DefaultScanner;
import com.nlf.extend.dao.sql.type.druid.DruidSetting;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

public class MyScanner extends DefaultScanner{
  protected String findTargetPath() throws UnsupportedEncodingException {
    Class<?> klass = DruidSetting.class;
    URL url = klass.getClassLoader().getResource("/");
    if(null==url){
      url = klass.getProtectionDomain().getCodeSource().getLocation();
    }
    return new File(URLDecoder.decode(url.getPath(),"utf-8")).getAbsolutePath();
  }

  public IScanner start() {
    try {
      addAbsolutePath(findTargetPath());
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return super.start();
  }
}
