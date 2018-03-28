package com.nlf.view;

/**
 * 恒成功的json视图
 * @author 6tail
 *
 */
public class SuccessJsonView extends JsonView{
  /**
   * 调用该方法并不能改变success的值
   */
  public JsonView setSuccess(boolean success){
    return this;
  }
}