package com.nlf.core.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.nlf.App;
import com.nlf.core.IValidator;
import com.nlf.exception.ValidateException;
import com.nlf.util.StringUtil;

/**
 * 默认验证器
 * 
 * @author 6tail
 *
 */
public class DefaultValidator implements IValidator{
  public static final String MSG_OR = "nlf.exception.validate.or";
  public static final String MSG_PREFIX = "nlf.exception.validate.";
  public static final Map<String,String> REG = new HashMap<String,String>(){
    private static final long serialVersionUID = 1;
    {
      put(NUMBER,"^[0-9]*$");
      put(LETTER,"^[A-Za-z]+$");
      put(INTEGER,"^-?\\d+$");
      put(LETTER_UPPER,"^[A-Z]+$");
      put(LETTER_LOWER,"^[a-z]+$");
      put(INTEGER_POSITIVE,"^[1-9]\\d*$");
      put(INTEGER_NEGTIVE,"^-[1-9]\\d*$");
      put(ID_CARD,"^\\d{15}|^\\d{17}([0-9]|X|x)$");
      put(DECIMAL_POSITIVE,"^[1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*$");
      put(DECIMAL_NEGTIVE,"^-([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*)$");
      put(DECIMAL,"^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$");
      put(EMAIL,"^\\w+([-+\\.]\\w+)*@\\w+([-\\.]\\w+)*\\.\\w+([-\\.]\\w+)*$");
      put(MOBILE,"^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
    }
  };

  protected void validateEmpty(String name,String value,String rule) throws ValidateException{
    if(null!=value&&value.length()>0){
      throw new ValidateException(App.getProperty(MSG_PREFIX+EMPTY,name));
    }
  }

  protected void validateNotEmpty(String name,String value,String rule) throws ValidateException{
    if(null==value||value.length()<1){
      throw new ValidateException(App.getProperty(MSG_PREFIX+NOT_EMPTY,name));
    }
  }

  protected void validateReg(String name,String value,String rule) throws ValidateException{
    validateNotEmpty(name,value,rule);
    Pattern p = Pattern.compile(REG.get(rule));
    Matcher m = p.matcher(value);
    if(!m.matches()){
      throw new ValidateException(App.getProperty(MSG_PREFIX+rule,name));
    }
  }

  protected void validateExpression(String name,String value,String rule,String op,String ov) throws ValidateException{
    validateNotEmpty(name,value,rule);
    if(IS.equals(op)){
      if(!value.equals(ov)){
        throw new ValidateException(App.getProperty(MSG_PREFIX+op,name,ov));
      }
    }else if(NOT.equals(op)){
      if(value.equals(ov)){
        throw new ValidateException(App.getProperty(MSG_PREFIX+op,name,ov));
      }
    }else if(IN.equals(op)){
      if(!ov.contains(value)){
        throw new ValidateException(App.getProperty(MSG_PREFIX+op,name,ov));
      }
    }else if(NOT_IN.equals(op)){
      if(ov.contains(value)){
        throw new ValidateException(App.getProperty(MSG_PREFIX+op,name,ov));
      }
    }else if(CONTAINS.equals(op)){
      if(!value.contains(ov)){
        throw new ValidateException(App.getProperty(MSG_PREFIX+op,name,ov));
      }
    }else if(NOT_CONTAINS.equals(op)){
      if(value.contains(ov)){
        throw new ValidateException(App.getProperty(MSG_PREFIX+op,name,ov));
      }
    }else if(MIN_LENGTH.equals(op)){
      if(value.length()<Integer.parseInt(ov)){
        throw new ValidateException(App.getProperty(MSG_PREFIX+op,name,ov));
      }
    }else if(MAX_LENGTH.equals(op)){
      if(value.length()>Integer.parseInt(ov)){
        throw new ValidateException(App.getProperty(MSG_PREFIX+op,name,ov));
      }
    }else if(FIX_LENGTH.equals(op)){
      if(value.length()!=Integer.parseInt(ov)){
        throw new ValidateException(App.getProperty(MSG_PREFIX+op,name,ov));
      }
    }else if(REGEX.equals(op)){
      Pattern p = Pattern.compile(ov);
      Matcher m = p.matcher(value);
      if(!m.matches()){
        throw new ValidateException(App.getProperty(MSG_PREFIX+op,name,ov));
      }
    }
  }

  protected void validateSingle(String name,String value,String rule) throws ValidateException{
    if(EMPTY.equals(rule)){
      validateEmpty(name,value,rule);
    }else if(NOT_EMPTY.equals(rule)){
      validateNotEmpty(name,value,rule);
    }else if(REG.containsKey(rule)){
      validateReg(name,value,rule);
    }else if(rule.contains("[")){
      int start = rule.indexOf("[");
      String op = rule.substring(0,start);
      String ov = rule.substring(start+1,rule.lastIndexOf("]"));
      validateExpression(name,value,rule,op,ov);
    }
  }

  public void validate(String name,String value,String rules) throws ValidateException{
    if(rules.contains(TAG_OR)){
      String[] ors = rules.split(TAG_OR.replace("|","\\|"),-1);
      List<String> messages = new ArrayList<String>(ors.length);
      for(String or:ors){
        try{
          validate(name,value,or);
          return;
        }catch(ValidateException e){
          String message = e.getMessage();
          if(null!=message&&message.length()>0){
            messages.add(message);
          }
        }
      }
      throw new ValidateException(StringUtil.join(messages,App.getProperty(MSG_OR)));
    }else if(rules.contains(TAG_AND)){
      String[] ands = rules.split(TAG_AND,-1);
      for(String and:ands){
        validate(name,value,and);
      }
    }else{
      validateSingle(name,value,rules);
    }
  }
}