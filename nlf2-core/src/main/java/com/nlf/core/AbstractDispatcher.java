package com.nlf.core;

import java.lang.reflect.Modifier;
import java.util.Map;
import com.nlf.App;
import com.nlf.bytecode.Method;
import com.nlf.dao.connection.IConnection;

/**
 * 抽象调度器
 * @author 6tail
 *
 */
public abstract class AbstractDispatcher implements IDispatcher{
  /** url映射 */
  protected static final RequestMapping requestMapping = new RequestMapping();

  public void init(){
    for(com.nlf.resource.klass.ClassResource r:App.CLASS.values()){
      String klass = r.getClassName();
      if(klass.startsWith(App.PACKAGE)){
        continue;
      }
      for(Method m:r.getMethods()){
        String ret = m.getRet();
        if(Method.RET_VOID.equals(ret)){
          continue;
        }
        if(m.getArgs().size()>0){
          continue;
        }
        if(!Modifier.isPublic(m.getAccess())){
          continue;
        }
        if(Modifier.isStatic(m.getAccess())){
          continue;
        }
        if("Ljava/lang/Object".equals(ret)){
          ret = m.getRetMaybe();
        }
        String method = m.getName();
        ClassMethod cm = new ClassMethod();
        cm.setKlass(klass);
        cm.setMethod(method);
        cm.setRet(ret);
        requestMapping.add("/"+klass+"/"+method,cm);
      }
    }
  }

  public void service(IRequest request,IResponse response,IFilterChain filterChain) throws java.io.IOException{
    ClassMethod cm = requestMapping.get(request.getPath());
    if(null==cm){
      filterChain.doFilter(request,response);
      return;
    }
    Object r = null;
    try{
      before(cm);
      r = around(cm);
      r = after(cm,r);
    }catch(Throwable e){
      r = afterThrowing(cm,e);
    }finally{
      afterReturning(cm);
    }
    response.send(r);
  }

  /**
   * 调用前执行
   * @param cm 调用的类和方法
   */
  protected void before(ClassMethod cm){}

  /**
   * 调用后执行，仅调用成功才执行
   * @param cm 调用的类和方法
   * @param ret 返回结果
   */
  protected Object after(ClassMethod cm,Object ret){
    return ret;
  }

  /**
   * 调用过程
   * @param cm 调用的类和方法
   * @return 返回结果
   */
  protected Object around(ClassMethod cm){
    return cm.proceed();
  }

  /**
   * 调用结束时执行，即使调用出错，也始终会执行
   * @param cm 调用的类和方法
   */
  protected void afterReturning(ClassMethod cm){
    Map<String,IConnection> connections = App.get(Statics.CONNECTIONS);
    if(null!=connections){
      for(IConnection connection:connections.values()){
        connection.close();
      }
    }
  }

  /**
   * 调用出错时的处理
   * 
   * @param e 产生的异常
   * @return 返回值
   */
  protected Object afterThrowing(ClassMethod cm,Throwable e){
    com.nlf.log.Logger.getLog().error(App.getProperty("nlf.dispatcher.request_failed",cm+""),e);
    Throwable cause = e;
    while(null!=cause&&null!=cause.getCause()){
      cause = cause.getCause();
    }
    return cause;
  }
}