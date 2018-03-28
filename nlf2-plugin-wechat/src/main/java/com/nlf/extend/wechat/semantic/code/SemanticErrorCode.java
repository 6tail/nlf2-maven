package com.nlf.extend.wechat.semantic.code;

import java.util.HashMap;
import java.util.Map;

/**
 * 语义识别错误码信息
 * 
 * @author 6tail
 *
 */
public class SemanticErrorCode{
  private static final Map<Integer,String> ERROR_MSG = new HashMap<Integer,String>(){
    private static final long serialVersionUID = 1L;
    {
      put(0,"请求正常，有语义结果");
      put(7000000,"请求正常，无语义结果");
      put(7000001,"缺失请求参数");
      put(7000002,"signature 参数无效");
      put(7000003,"地理位置相关配置1 无效");
      put(7000004,"地理位置相关配置2 无效");
      put(7000005,"请求地理位置信息失败");
      put(7000006,"地理位置结果解析失败");
      put(7000007,"内部初始化失败");
      put(7000008,"非法 appid（获取密钥失败）");
      put(7000009,"请求语义服务失败");
      put(7000010,"非法 post请求");
      put(7000011,"post请求 json字段无效");
      put(7000030,"查询 query太短");
      put(7000031,"查询 query太长");
      put(7000032,"城市、经纬度信息缺失");
      put(7000033,"query请求语义处理失败");
      put(7000034,"获取天气信息失败");
      put(7000035,"获取股票信息失败");
      put(7000036,"utf8 编码转换失败");
    }
  };

  /**
   * 根据错误码获取错误信息
   * 
   * @param code 错误码
   * @return 错误信息
   */
  public static String getErrorMsg(int code){
    return ERROR_MSG.get(code);
  }
}
