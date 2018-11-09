package com.nlf.extend.rpc.server.impl.http.impl;

import com.nlf.extend.rpc.server.impl.http.AbstractHttpRpcDispatcher;


/**
 * 默认HTTP RPC调度器
 * 
 * @author 6tail
 *
 */
public class DefaultHttpRpcDispatcher extends AbstractHttpRpcDispatcher {

  /**JSON返回类型*/
  protected static String RET_JSON = "L"+com.nlf.view.JsonView.class.getName().replace(".","/");

  public Object afterThrowing(com.nlf.core.ClassMethod cm,Throwable e){
    Throwable cause = (Throwable)super.afterThrowing(cm,e);
    if(RET_JSON.equals(cm.getRet())){
      return com.nlf.View.json(cause.getMessage()).setSuccess(false);
    }
    return cause;
  }

}