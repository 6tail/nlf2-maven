package com.nlf.resource.i18n.comparator;

import java.util.Comparator;
import com.nlf.App;

/**
 * i18n文件比较器，按名称比较，越大的越前，但框架里面的除外（框架内的始终排在框架外的后面）。
 * 
 * @author 6tail
 * 
 */
public class I18nComparator implements Comparator<String>{

  public int compare(String o1,String o2){
    String pkg = App.class.getPackage().getName();
    if(o2.startsWith(pkg)&&!o1.startsWith(pkg)){
      return -1;
    }
    if(o1.startsWith(pkg)&&!o2.startsWith(pkg)){
      return 1;
    }
    return o2.compareTo(o1);
  }
}