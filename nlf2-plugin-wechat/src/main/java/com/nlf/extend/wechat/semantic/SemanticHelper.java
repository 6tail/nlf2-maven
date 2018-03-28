package com.nlf.extend.wechat.semantic;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.extend.wechat.exception.WeixinException;
import com.nlf.extend.wechat.semantic.bean.SemanticRequest;
import com.nlf.extend.wechat.semantic.bean.SemanticResponse;
import com.nlf.extend.wechat.semantic.bean.SemanticResponseFlight;
import com.nlf.extend.wechat.semantic.code.SemanticErrorCode;
import com.nlf.extend.wechat.util.HttpsClient;
import com.nlf.log.Logger;
import com.nlf.serialize.json.JSON;

/**
 * 语义识别
 * @author 6tail
 *
 */
public class SemanticHelper{

  protected SemanticHelper(){}

  /**
   * 识别
   * @param accessToken accessToken
   * @param request 语义识别请求
   * @return 语义识别结果
   * @throws WeixinException
   */
  public static SemanticResponse recognize(String accessToken,SemanticRequest request) throws WeixinException{
    try{
      String url = App.getProperty("nlf.weixin.url.semantic_search",accessToken);
      String data = JSON.fromObject(request);
      Logger.getLog().debug(App.getProperty("nlf.weixin.send",data));
      String result = HttpsClient.post(url,data);
      Logger.getLog().debug(App.getProperty("nlf.weixin.recv",result));
      Bean o = JSON.toBean(result);
      int errorCode = o.getInt("errcode",0);
      if(0!=errorCode){
        throw new WeixinException(errorCode,SemanticErrorCode.getErrorMsg(errorCode));
      }
      String type = o.getString("type");
      SemanticResponse response = new SemanticResponse();
      if("flight".equals(type)){
        SemanticResponseFlight res = new SemanticResponseFlight();
        Bean details = o.getBean("semantic").get("details");
        res.setAirline(details.getString("airline"));
        Bean startLoc = details.get("start_loc");
        res.setDep(startLoc.getString("loc_ori"));
        res.setDepCityName(startLoc.getString("city"));
        res.setDepCityNameSimple(startLoc.getString("city_simple"));
        Bean endLoc = details.get("end_loc");
        res.setArr(endLoc.getString("loc_ori"));
        res.setArrCityName(endLoc.getString("city"));
        res.setArrCityNameSimple(endLoc.getString("city_simple"));
        Bean startDate = details.get("start_date");
        res.setDay(startDate.getString("date_ori"));
        res.setDayInYmd(startDate.getString("date"));
        response = res;
      }
      response.setQuery(o.getString("query"));
      response.setType(type);
      response.setAnswer(o.getString("answer"));
      response.setText(o.getString("text"));
      return response;
    }catch(Exception e){
      throw new WeixinException(e);
    }
  }
}