package com.nlf.extend.rpc.server.impl.socket.impl;

import com.nlf.extend.rpc.server.impl.socket.AbstractSocketRpcDispatcher;

/**
 * 默认Socket RPC调度器
 *
 * @author 6tail
 *
 */
public class DefaultSocketRpcDispatcher extends AbstractSocketRpcDispatcher {

  /**JSON返回类型*/
  protected static String RET_JSON = "L"+com.nlf.view.JsonView.class.getName().replace(".","/");

  @Override
  public Object afterThrowing(com.nlf.core.ClassMethod cm, Throwable e){
    Throwable cause = (Throwable)super.afterThrowing(cm,e);
    if(RET_JSON.equals(cm.getRet())){
      return com.nlf.View.json(cause.getMessage()).setSuccess(false);
    }
    return cause;
  }

}
