package com.nlf;

import com.nlf.view.JsonView;

/**
 * 抽象视图
 * @author 6tail
 *
 */
public abstract class View{
  /** 仅代表操作成功的json */
  public static final JsonView json = new com.nlf.view.SuccessJsonView();

  /**
   * 生成json
   * @param o 对象
   * @return json
   */
  public static JsonView json(Object o){
    return new JsonView(o);
  }
}