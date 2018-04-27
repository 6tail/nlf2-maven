package com.nlf.bytecode;

import java.util.ArrayList;
import java.util.List;
import com.nlf.util.StringUtil;

/**
 * 类中的方法
 * 
 * @author 6tail
 *
 */
public class Method{
  /** 返回类型：void */
  public static final String VOID = "V";
  /** 访问修饰符 */
  private int access;
  /** 所在类 */
  private Klass klass;
  /** 方法名索引 */
  private int nameIndex;
  /** 参数及返回值描述索引 */
  private int descriptorIndex;
  /** 方法名 */
  private String name;
  /** 参数及返回值描述 */
  private String descriptor;
  /** 参数描述 */
  private List<String> args = new ArrayList<String>();
  /** 返回值描述 */
  private String ret;
  /** 可能的返回值描述 */
  private String retMaybe;

  public Method(Klass klass){
    this.klass = klass;
  }

  public boolean isInit(){
    return "<init>".equals(getName());
  }

  public boolean isClInit(){
    return "<clinit>".equals(getName());
  }

  /**
   * 获取方法名
   * 
   * @return 方法名
   */
  public String getName(){
    if(null==name){
      name = klass.getConstant(nameIndex).toUTFConstant().getContent();
    }
    return name;
  }

  /**
   * 获取参数及返回值描述
   * 
   * @return 参数及返回值描述
   */
  public String getDescripter(){
    if(null==descriptor){
      descriptor = klass.getConstant(descriptorIndex).toUTFConstant().getContent();
      String[] arrDesc = descriptor.split("\\)",-1);
      String[] arrArg = arrDesc[0].substring(1).split(";",-1);
      for(String arg:arrArg){
        if(arg.length()>0){
          args.add(arg);
        }
      }
      ret = arrDesc[1].replace(";","");
    }
    return descriptor;
  }

  public List<String> getArgs(){
    getDescripter();
    return args;
  }

  public String getRet(){
    getDescripter();
    return ret;
  }

  public String getRetMaybe(){
    return retMaybe;
  }

  public void setRetMaybe(String retMaybe){
    this.retMaybe = retMaybe;
  }

  public int getAccess(){
    return access;
  }

  public void setAccess(int access){
    this.access = access;
  }

  public Klass getKlass(){
    return klass;
  }

  public void setNameIndex(int nameIndex){
    this.nameIndex = nameIndex;
  }

  public void setDescriptorIndex(int descriptorIndex){
    this.descriptorIndex = descriptorIndex;
  }

  public String toString(){
    List<String> l = new ArrayList<String>();
    l.add(access+"");
    l.add(getRet());
    l.add(getName());
    l.add("(");
    l.add(StringUtil.join(getArgs(),","));
    l.add(")");
    return StringUtil.join(l," ");
  }
}