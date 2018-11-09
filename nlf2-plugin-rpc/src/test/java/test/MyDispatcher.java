package test;

import com.nlf.extend.rpc.server.impl.http.impl.DefaultHttpRpcDispatcher;

/**
 * 自定义调度器
 * 
 * @author 6tail
 *
 */
public class MyDispatcher extends DefaultHttpRpcDispatcher {
  public void init(){
    super.init();
    //屏蔽指定包
    //requestMapping.remove("/com.*");
    for(String uri:requestMapping.getUriList()){
      System.out.println("mapping "+uri);
    }
  }
}