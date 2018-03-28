package com.nlf.extend.wechat.semantic.bean;

/**
 * 语义识别结果
 * 
 * @author 6tail
 *
 */
public class SemanticResponse{
  /** 识别内容 */
  protected String query;
  /** 结果类型 */
  protected String type;
  /** 部分类别的结果html5展示，目前不支持 */
  protected String answer;
  /** 特殊回复说明 */
  protected String text;

  public String getQuery(){
    return query;
  }

  public void setQuery(String query){
    this.query = query;
  }

  public String getType(){
    return type;
  }

  public void setType(String type){
    this.type = type;
  }

  public String getAnswer(){
    return answer;
  }

  public void setAnswer(String answer){
    this.answer = answer;
  }

  public String getText(){
    return text;
  }

  public void setText(String text){
    this.text = text;
  }
}