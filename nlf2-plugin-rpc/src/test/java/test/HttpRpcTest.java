package test;

import com.nlf.extend.rpc.RpcFactory;
import com.nlf.extend.rpc.client.IRpcResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpRpcTest {

  @Test
  public void test() throws IOException {
    int port = 8080;
    RpcFactory.getServer("http").bind(port);

    Map<String,String> args = new HashMap<String,String>();
    args.put("name","张三");

    //form和body同时提交
    IRpcResponse response = RpcFactory.getClient("http").call("localhost",port,"/test/action.Say/hello",args,"{name:'李四',age:12}");
    if(response.isSuccess()){
      System.out.println(response.getData());
    }else{
      throw new RuntimeException(response.getMessage());
    }

    //body提交
    response = RpcFactory.getClient("http").call("localhost",port,"/test/action.Say/hello",null,"{name:'李四',age:12}");
    if(response.isSuccess()){
      System.out.println(response.getData());
    }else{
      throw new RuntimeException(response.getMessage());
    }

    //form提交
    response = RpcFactory.getClient("http").call("localhost",port,"/test/action.Say/hello",args,null);
    if(response.isSuccess()){
      System.out.println(response.getData());
    }else{
      throw new RuntimeException(response.getMessage());
    }
  }
}
