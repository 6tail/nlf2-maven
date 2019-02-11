package com.nlf.bytecode;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.nlf.bytecode.constant.*;
import com.nlf.util.IOUtil;
import com.nlf.util.MathUtil;
import com.nlf.util.StringUtil;

/**
 * 字节码解码为类信息封装，只解码了框架需要的东西
 * 
 * @author 6tail
 *
 */
public class Klass{
  private byte[] byteCodes;
  /** 次版本号，一般为0 */
  private int minorVersion;
  /** 主版本号，1.2为46 */
  private int majorVersion;
  /** 访问修饰符 */
  private int access;
  /** 父类名 */
  private String superClass;
  /** 类名 */
  private String name;
  /** 常量池 */
  private List<IConstant> constants = new ArrayList<IConstant>();
  /** 实现的接口列表 */
  private Set<String> interfaces = new LinkedHashSet<String>();
  /** 方法 */
  private List<Method> methods = new ArrayList<Method>();

  public Klass(byte[] byteCodes) throws IOException{
    this.byteCodes = byteCodes;
    decode();
  }

  protected IConstant getConstant(int index){
    return constants.get(index);
  }

  public Set<String> getInterfaces(){
    return interfaces;
  }

  public String getSuperClass(){
    return superClass;
  }

  public String getName(){
    return name;
  }

  public boolean isAbstract(){
    return Modifier.isAbstract(access);
  }

  public boolean isInterface(){
    return Modifier.isInterface(access);
  }

  public int getAccess(){
    return access;
  }

  public int getMinorVersion(){
    return minorVersion;
  }

  public int getMajorVersion(){
    return majorVersion;
  }

  public List<Method> getMethods(){
    return methods;
  }

  protected void decode() throws IOException{
    ByteArrayInputStream stream = null;
    DataInputStream in = null;
    try{
      stream = new ByteArrayInputStream(byteCodes);
      in = new DataInputStream(stream);
      byte[] b = new byte[4];
      in.read(b);// magic
      b = new byte[2];
      in.read(b);// minor_version
      minorVersion = MathUtil.toInt(b);
      b = new byte[2];
      in.read(b);// major_version
      majorVersion = MathUtil.toInt(b);
      b = new byte[2];
      in.read(b);
      int count = MathUtil.toInt(b);
      constants.add(null);
      for(int i = 1;i<count;i++){
        byte tag = in.readByte();
        IConstant c = new DefaultConstant();
        switch(tag){
          case IConstant.TYPE_CLASS:
            b = new byte[2];
            in.read(b);
            ClassConstant cc = new ClassConstant();
            cc.setData(b);
            cc.setNameIndex(MathUtil.toInt(b));
            c = cc;
            break;
          case IConstant.TYPE_INVOKE_DYNAMIC:
            b = new byte[4];
            in.read(b);
            InvokeDynamicConstant idc = new InvokeDynamicConstant();
            idc.setBootstrapMethodAttributeIndex(MathUtil.toInt(new byte[]{b[0],b[1]}));
            idc.setNameAndTypeIndex(MathUtil.toInt(new byte[]{b[2],b[3]}));
            c = idc;
            break;
          case IConstant.TYPE_METHOD_HANDLE:
            b = new byte[3];
            in.read(b);
            MethodHandleConstant mhc = new MethodHandleConstant();
            mhc.setReferenceKind(b[0]);
            mhc.setReferenceIndex(MathUtil.toInt(new byte[]{b[1],b[2]}));
            c = mhc;
            break;
          case IConstant.TYPE_METHOD_TYPE:
            b = new byte[2];
            in.read(b);
            MethodTypeConstant mtc = new MethodTypeConstant();
            mtc.setDescriptorIndex(MathUtil.toInt(b));
            c = mtc;
            break;
          case IConstant.TYPE_STRING:
            b = new byte[2];
            in.read(b);
            break;
          case IConstant.TYPE_FIELD:
            b = new byte[4];
            in.read(b);
            FieldConstant fc = new FieldConstant();
            fc.setData(b);
            fc.setClassIndex(MathUtil.toInt(MathUtil.sub(b,0,1)));
            fc.setNameAndTypeIndex(MathUtil.toInt(MathUtil.sub(b,2,3)));
            c = fc;
            break;
          case IConstant.TYPE_METHOD:
            b = new byte[4];
            in.read(b);
            MethodConstant mc = new MethodConstant();
            mc.setData(b);
            mc.setClassIndex(MathUtil.toInt(MathUtil.sub(b,0,1)));
            mc.setNameAndTypeIndex(MathUtil.toInt(MathUtil.sub(b,2,3)));
            c = mc;
            break;
          case IConstant.TYPE_NAME_AND_TYPE:
            b = new byte[4];
            in.read(b);
            NameAndTypeConstant nc = new NameAndTypeConstant();
            nc.setData(b);
            nc.setNameIndex(MathUtil.toInt(MathUtil.sub(b,0,1)));
            nc.setDescriptorIndex(MathUtil.toInt(MathUtil.sub(b,2,3)));
            c = nc;
            break;
          case IConstant.TYPE_INTERFACE_METHOD:
          case IConstant.TYPE_INT:
          case IConstant.TYPE_FLOAT:
            b = new byte[4];
            in.read(b);
            c.setData(b);
            break;
          case IConstant.TYPE_UTF:
            b = new byte[2];// data length
            in.read(b);
            int length = MathUtil.toInt(b);
            b = new byte[length];// data
            in.read(b);
            UTFConstant uc = new UTFConstant();
            uc.setData(b);
            uc.setContent(new String(b,"utf-8"));
            c = uc;
            break;
          case IConstant.TYPE_LONG:
          case IConstant.TYPE_DOUBLE:
            b = new byte[8];
            in.read(b);
            c.setData(b);
            break;
        }
        c.setIndex(i);
        c.setType(tag);
        constants.add(c);
        if(IConstant.TYPE_LONG==c.getType()||IConstant.TYPE_DOUBLE==c.getType()){
          constants.add(null);
          i++;
        }
      }
      b = new byte[2];// access flags
      in.read(b);
      access = MathUtil.toInt(b);
      b = new byte[2];// this class
      in.read(b);
      name = getConstant(getConstant(MathUtil.toInt(b)).toClassConstant().getNameIndex()).toUTFConstant().getContent();
      name = name.replace("/",".");
      b = new byte[2];// super class
      in.read(b);
      superClass = getConstant(getConstant(MathUtil.toInt(b)).toClassConstant().getNameIndex()).toUTFConstant().getContent();
      superClass = superClass.replace("/",".");
      b = new byte[2];// interface count
      in.read(b);
      int interfaceCount = MathUtil.toInt(b);
      for(int i = 0;i<interfaceCount;i++){
        b = new byte[2];
        in.read(b);
        String interfaceClass = getConstant(getConstant(MathUtil.toInt(b)).toClassConstant().getNameIndex()).toUTFConstant().getContent();
        interfaceClass = interfaceClass.replace("/",".");
        interfaces.add(interfaceClass);
      }
      //fields
      in.read(b);// field count
      int fieldCount = MathUtil.toInt(b);
      for(int i = 0;i<fieldCount;i++){
        b = new byte[2];
        //没有，直接跳过
        in.skip(6);// 跳过access,name index,desc index
        //in.read(b);// access
        //in.read(b);// name index
        //in.read(b);// desc index
        //attributes
        in.read(b);// attribute count
        int attributeCount = MathUtil.toInt(b);
        for(int j = 0;j<attributeCount;j++){
          in.skip(2);// 跳过name index
          //in.read(b);// name index
          b = new byte[4];// data length
          in.read(b);
          int length = MathUtil.toInt(b);
          //没有使用，直接跳过
          in.skip(length);
          //b = new byte[length];// data
          //in.read(b);
        }
      }
      // methods
      b = new byte[2];// method count
      in.read(b);
      int methodCount = MathUtil.toInt(b);
      for(int i = 0;i<methodCount;i++){
        Method f = new Method(this);
        b = new byte[2];
        in.read(b);// access
        f.setAccess(MathUtil.toInt(b));
        in.read(b);// name index
        f.setNameIndex(MathUtil.toInt(b));
        in.read(b);// descriptor index
        f.setDescriptorIndex(MathUtil.toInt(b));
        //attributes
        in.read(b);// attribute count
        int attributeCount = MathUtil.toInt(b);
        String methodName = f.getName();
        for(int j = 0;j<attributeCount;j++){
          b = new byte[2];
          in.read(b);// name index
          String attrName = getConstant(MathUtil.toInt(b)).toUTFConstant().getContent();
          b = new byte[4];// data length
          in.read(b);
          int length = MathUtil.toInt(b);
          b = new byte[length];// data
          in.read(b);
          if("<init>".equals(methodName)) continue;
          if("<clinit>".equals(methodName)) continue;
          if(methodName.startsWith("$SWITCH_TABLE$")) continue;
          if(!"Code".equals(attrName)) continue;
          length = MathUtil.toInt(MathUtil.sub(b,4,7)); //code_length
          b = MathUtil.sub(b,8,8+length-1);
          labelK:for(int k=length-1;k>-1;k--){
            int op = b[k]&0xff;
            switch (op){
              case 0xac://ireturn
                f.setRetMaybe("I");
                break labelK;
              case 0xad://lreturn
                f.setRetMaybe("J");
                break labelK;
              case 0xae://freturn
                f.setRetMaybe("F");
                break labelK;
              case 0xaf://dreturn
                f.setRetMaybe("D");
                break labelK;
              case 0xb1://return
                f.setRetMaybe(Method.VOID);
                break labelK;
              case 0xb0://areturn
                for(int x=k-1;x>-1;x--){
                  int p = b[x]&0xff;
                  try{
                    IConstant c = getConstant(p);
                    String retType;
                    switch(c.getType()){
                      case IConstant.TYPE_CLASS:
                        retType = "L"+getConstant(c.toClassConstant().getNameIndex()).toUTFConstant().getContent();
                        f.setRetMaybe(retType);
                        break;
                      case IConstant.TYPE_FIELD:
                        retType = getConstant(getConstant(c.toFieldConstant().getNameAndTypeIndex()).toNameAndTypeConstant().getDescriptorIndex()).toUTFConstant().getContent();
                        if(retType.contains(";")){
                          retType = retType.substring(0,retType.indexOf(";"));
                        }
                        f.setRetMaybe(retType);
                        break;
                      case IConstant.TYPE_METHOD:
                        retType = getConstant(getConstant(c.toMethodConstant().getNameAndTypeIndex()).toNameAndTypeConstant().getDescriptorIndex()).toUTFConstant().getContent();
                        if(retType.contains(")")){
                          retType = retType.substring(retType.lastIndexOf(")")+1);
                        }
                        if(retType.contains(";")){
                          retType = retType.substring(0,retType.indexOf(";"));
                        }
                        f.setRetMaybe(retType);
                        break;
                      default:
                        continue;
                    }
                    break;
                  }catch(Exception e){}
                }
                break labelK;
            }
          }
        }
        methods.add(f);
      }
    }finally{
      IOUtil.closeQuietly(in);
      IOUtil.closeQuietly(stream);
    }
  }

  public String toString(){
    List<String> l = new ArrayList<String>();
    l.add(access+"");
    l.add(isAbstract()?"abstract":"");
    l.add(isInterface()?"interface":"class");
    l.add(name);
    l.add("extends");
    l.add(superClass);
    l.add("implements");
    l.add(StringUtil.join(interfaces,","));
    return StringUtil.join(l," ");
  }
}