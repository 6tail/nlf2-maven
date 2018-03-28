package com.nlf.core;

import com.nlf.Bean;
import com.nlf.util.StringUtil;

/**
 * 抽象请求
 * 
 * @author 6tail
 *
 */
public abstract class AbstractRequest implements IRequest{
  /** 页码，从第1页开始计 */
  protected int pageNumber = 1;
  /** 每页记录数 */
  protected int pageSize = 20;
  /** 请求的参数 */
  protected Bean param = new Bean();
  /** 客户端 */
  protected Client client;
  /** 会话 */
  protected ISession session;
  /** 验证器 */
  protected static IValidator validator = com.nlf.App.getProxy().newInstance(IValidator.class.getName());

  public Bean getParam(){
    return param;
  }

  public void setParam(Bean param){
    this.param = param;
  }

  public Client getClient(){
    if(null==client){
      client = new Client();
    }
    return client;
  }

  public ISession getSession(){
    return session;
  }

  public void setSession(ISession session){
    this.session = session;
  }

  public void setClient(Client client){
    this.client = client;
  }

  public int getPageNumber(){
    return pageNumber;
  }

  public void setPageNumber(int pageNumber){
    this.pageNumber = pageNumber;
  }

  public int getPageSize(){
    return pageSize;
  }

  public void setPageSize(int pageSize){
    this.pageSize = pageSize;
  }

  public String get(String key){
    Object value = param.get(key);
    if(null==value) return "";
    if(value instanceof String[]){
      return StringUtil.join((String[])value,",");
    }
    return value+"";
  }

  public String[] getArray(String key){
    Object value = param.get(key);
    if(null==value) return new String[]{};
    if(value instanceof String[]){
      return (String[])value;
    }else{
      return StringUtil.array(value+"",",");
    }
  }

  public String get(String key,String rules){
    return get(key,rules,key);
  }

  public String[] getArray(String key,String rules){
    return getArray(key,rules,key);
  }

  public String get(String key,String rules,String name){
    String value = get(key);
    validator.validate(name,value,rules);
    return value;
  }

  public String[] getArray(String key,String rules,String name){
    String[] array = getArray(key);
    for(String value:array){
      validator.validate(name,value,rules);
    }
    return array;
  }
}