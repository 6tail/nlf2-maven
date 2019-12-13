package com.nlf.bytecode;

import com.nlf.bytecode.constant.*;
import com.nlf.util.IOUtil;
import com.nlf.util.MathUtil;
import com.nlf.util.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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

  protected void decodeConstants(DataInputStream in) throws IOException{
    byte[] b = new byte[2];
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
          c = new ClassConstant(b,MathUtil.toInt(b));
          break;
        case IConstant.TYPE_INVOKE_DYNAMIC:
          b = new byte[4];
          in.read(b);
          c = new InvokeDynamicConstant(MathUtil.toInt(new byte[]{b[0],b[1]}),MathUtil.toInt(new byte[]{b[2],b[3]}));
          break;
        case IConstant.TYPE_METHOD_HANDLE:
          b = new byte[3];
          in.read(b);
          c = new MethodHandleConstant(b[0],MathUtil.toInt(new byte[]{b[1],b[2]}));
          break;
        case IConstant.TYPE_METHOD_TYPE:
          b = new byte[2];
          in.read(b);
          c = new MethodTypeConstant(MathUtil.toInt(b));
          break;
        case IConstant.TYPE_STRING:
          b = new byte[2];
          in.read(b);
          break;
        case IConstant.TYPE_FIELD:
          b = new byte[4];
          in.read(b);
          c = new FieldConstant(b,MathUtil.toInt(MathUtil.sub(b,0,1)),MathUtil.toInt(MathUtil.sub(b,2,3)));
          break;
        case IConstant.TYPE_METHOD:
          b = new byte[4];
          in.read(b);
          c = new MethodConstant(b,MathUtil.toInt(MathUtil.sub(b,0,1)),MathUtil.toInt(MathUtil.sub(b,2,3)));
          break;
        case IConstant.TYPE_NAME_AND_TYPE:
          b = new byte[4];
          in.read(b);
          c = new NameAndTypeConstant(b,MathUtil.toInt(MathUtil.sub(b,0,1)),MathUtil.toInt(MathUtil.sub(b,2,3)));
          break;
        case IConstant.TYPE_INTERFACE_METHOD:
        case IConstant.TYPE_INT:
        case IConstant.TYPE_FLOAT:
          b = new byte[4];
          in.read(b);
          c.setData(b);
          break;
        case IConstant.TYPE_UTF:
          b = new byte[2];
          in.read(b);
          int length = MathUtil.toInt(b);
          b = new byte[length];
          in.read(b);
          c = new UTFConstant(b,new String(b,"utf-8"));
          break;
        case IConstant.TYPE_LONG:
        case IConstant.TYPE_DOUBLE:
          b = new byte[8];
          in.read(b);
          c.setData(b);
          break;
        default:
      }
      c.setIndex(i);
      c.setType(tag);
      constants.add(c);
      if(IConstant.TYPE_LONG==c.getType()||IConstant.TYPE_DOUBLE==c.getType()){
        constants.add(null);
        i++;
      }
    }
  }

  protected String guessRet(byte[] data){
    String ret = null;
    label:for(int k=data.length-1;k>-1;k--){
      switch (data[k]&0xff){
        case 0xac:
          ret = Method.RET_INT;
          break label;
        case 0xad:
          ret = Method.RET_LONG;
          break label;
        case 0xae:
          ret = Method.RET_FLOAT;
          break label;
        case 0xaf:
          ret = Method.RET_DOUBLE;
          break label;
        case 0xb1:
          ret = Method.RET_VOID;
          break label;
        case 0xb0:
          for(int x=k-1;x>-1;x--){
            int p = data[x]&0xff;
            try{
              IConstant c = getConstant(p);
              String retType;
              switch(c.getType()){
                case IConstant.TYPE_INT:
                  ret = Method.RET_INT;
                  break;
                case IConstant.TYPE_LONG:
                  ret = Method.RET_LONG;
                  break;
                case IConstant.TYPE_FLOAT:
                  ret = Method.RET_FLOAT;
                  break;
                case IConstant.TYPE_DOUBLE:
                  ret = Method.RET_DOUBLE;
                  break;
                case IConstant.TYPE_STRING:
                  ret = Method.RET_STRING;
                  break;
                case IConstant.TYPE_CLASS:
                  retType = "L"+getConstant(c.toClassConstant().getNameIndex()).toUTFConstant().getContent();
                  ret = retType;
                  break;
                case IConstant.TYPE_FIELD:
                  retType = getConstant(getConstant(c.toFieldConstant().getNameAndTypeIndex()).toNameAndTypeConstant().getDescriptorIndex()).toUTFConstant().getContent();
                  if(retType.contains(";")){
                    retType = retType.substring(0,retType.indexOf(";"));
                  }
                  ret = retType;
                  break;
                case IConstant.TYPE_METHOD:
                  retType = getConstant(getConstant(c.toMethodConstant().getNameAndTypeIndex()).toNameAndTypeConstant().getDescriptorIndex()).toUTFConstant().getContent();
                  if(retType.contains(")")){
                    retType = retType.substring(retType.lastIndexOf(")")+1);
                  }
                  if(retType.contains(";")){
                    retType = retType.substring(0,retType.indexOf(";"));
                  }
                  // void有可能是new的，需要获取new的class
                  if(Method.RET_VOID.equals(retType)){
                    String methodName = getConstant(getConstant(c.toMethodConstant().getNameAndTypeIndex()).toNameAndTypeConstant().getNameIndex()).toUTFConstant().getContent();
                    if(Method.NAME_INIT.equals(methodName)){
                      retType = "L"+getConstant(getConstant(c.toMethodConstant().getClassIndex()).toClassConstant().getNameIndex()).toUTFConstant().getContent();
                    }
                  }
                  ret = retType;
                  break;
                default:
                  continue;
              }
              break;
            }catch(Exception e){}
          }
          break label;
        default:
      }
    }
    return ret;
  }

  protected void decodeMethod(DataInputStream in) throws IOException{
    Method f = new Method(this);
    byte[] b = new byte[2];
    in.read(b);
    f.setAccess(MathUtil.toInt(b));
    in.read(b);
    f.setNameIndex(MathUtil.toInt(b));
    in.read(b);
    f.setDescriptorIndex(MathUtil.toInt(b));
    // attributes
    in.read(b);
    int attributeCount = MathUtil.toInt(b);
    String methodName = f.getName();
    for(int j = 0;j<attributeCount;j++){
      b = new byte[2];
      in.read(b);
      String attrName = getConstant(MathUtil.toInt(b)).toUTFConstant().getContent();
      b = new byte[4];
      in.read(b);
      int length = MathUtil.toInt(b);
      if(Method.NAME_INIT.equals(methodName) || Method.NAME_CLINIT.equals(methodName) || methodName.startsWith("$SWITCH_TABLE$") || (!"Code".equals(attrName))){
        in.skip(length);
        continue;
      }
      // data
      b = new byte[length];
      in.read(b);
      // code length
      length = MathUtil.toInt(MathUtil.sub(b,4,7));
      f.setRetMaybe(guessRet(MathUtil.sub(b,8,8+length-1)));
    }
    methods.add(f);
  }

  protected void decode() throws IOException{
    ByteArrayInputStream stream = null;
    DataInputStream in = null;
    try{
      stream = new ByteArrayInputStream(byteCodes);
      in = new DataInputStream(stream);
      //跳过magic
      in.skip(4);
      // minor version
      byte[] b = new byte[2];
      in.read(b);
      minorVersion = MathUtil.toInt(b);
      // major version
      b = new byte[2];
      in.read(b);
      majorVersion = MathUtil.toInt(b);
      // 常量池
      decodeConstants(in);
      // access flags
      b = new byte[2];
      in.read(b);
      access = MathUtil.toInt(b);
      // this class
      b = new byte[2];
      in.read(b);
      name = getConstant(getConstant(MathUtil.toInt(b)).toClassConstant().getNameIndex()).toUTFConstant().getContent();
      name = name.replace("/",".");
      // super class
      b = new byte[2];
      in.read(b);
      superClass = getConstant(getConstant(MathUtil.toInt(b)).toClassConstant().getNameIndex()).toUTFConstant().getContent();
      superClass = superClass.replace("/",".");
      // interfaces
      b = new byte[2];
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
      in.read(b);
      // field count
      int fieldCount = MathUtil.toInt(b);
      for(int i = 0;i<fieldCount;i++){
        b = new byte[2];
        // 跳过access,name index,desc index
        in.skip(6);
        //attributes
        in.read(b);
        // attribute count
        int attributeCount = MathUtil.toInt(b);
        for(int j = 0;j<attributeCount;j++){
          // 跳过name index
          in.skip(2);
          // data length
          b = new byte[4];
          in.read(b);
          //跳过data
          in.skip(MathUtil.toInt(b));
        }
      }
      // methods
      b = new byte[2];
      in.read(b);
      // method count
      int methodCount = MathUtil.toInt(b);
      for(int i = 0;i<methodCount;i++){
        decodeMethod(in);
      }
    }finally{
      IOUtil.closeQuietly(in);
      IOUtil.closeQuietly(stream);
    }
  }

  @Override
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
