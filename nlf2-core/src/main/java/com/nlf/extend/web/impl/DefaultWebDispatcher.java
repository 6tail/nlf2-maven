package com.nlf.extend.web.impl;

/**
 * 默认WEB调度器
 * 
 * @author 6tail
 *
 */
public class DefaultWebDispatcher extends com.nlf.extend.web.AbstractWebDispatcher{

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