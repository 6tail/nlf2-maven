package com.nlf.view;

/**
 * json视图
 * 
 * @author 6tail
 *
 */
public class JsonView extends com.nlf.View{
  /** 是否成功 */
  private boolean success = true;
  /** 数据对象 */
  private Object data;

  public JsonView(){}

  public JsonView(Object o){
    data = o;
  }

  public boolean isSuccess(){
    return success;
  }

  public JsonView setSuccess(boolean success){
    this.success = success;
    return this;
  }

  public Object getData(){
    return data;
  }

  public JsonView setData(Object data){
    this.data = data;
    return this;
  }

  @Override
  public String toString(){
    return com.nlf.serialize.json.JSON.fromObject(this);
  }
}