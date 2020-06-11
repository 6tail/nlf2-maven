package test;

import com.nlf.extend.rpc.RpcFactory;
import com.nlf.extend.rpc.client.IRpcResponse;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SocketRpcTest {

  @Test
  public void test() throws IOException {
    int port = 8080;
    RpcFactory.getServer("socket").bind(port);

    Map<String,String> args = new HashMap<String,String>();
    args.put("name","张三");
    IRpcResponse response = RpcFactory.getClient("socket").call("localhost",port,"/test/action.Say/hello",args);
    if(response.isSuccess()){
      System.out.println(response.getData());
    }else{
      throw new RuntimeException(response.getMessage());
    }
  }
}
