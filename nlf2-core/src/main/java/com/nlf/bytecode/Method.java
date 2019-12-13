package com.nlf.bytecode;

import com.nlf.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 类中的方法
 *
 * @author 6tail
 *
 */
public class Method{
  /** 返回类型：void */
  public static final String RET_VOID = "V";
  /** 返回类型：int */
  public static final String RET_INT = "I";
  /** 返回类型：long */
  public static final String RET_LONG = "J";
  /** 返回类型：float */
  public static final String RET_FLOAT = "F";
  /** 返回类型：double */
  public static final String RET_DOUBLE = "D";
  /** 返回类型：String */
  public static final String RET_STRING = "Ljava/lang/String";
  /** 返回类型：Object */
  public static final String RET_OBJECT = "Ljava/lang/Object";
  public static final String NAME_INIT = "<init>";
  public static final String NAME_CLINIT = "<clinit>";

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
  private String retMaybe = RET_VOID;

  public Method(Klass klass){
    this.klass = klass;
  }

  public boolean isInit(){
    return NAME_INIT.equals(getName());
  }

  public boolean isClInit(){
    return NAME_CLINIT.equals(getName());
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

  @Override
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
