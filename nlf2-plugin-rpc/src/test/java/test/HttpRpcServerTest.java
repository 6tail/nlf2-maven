package test;

import com.nlf.extend.rpc.RpcFactory;
import org.junit.Test;

import java.io.IOException;

public class HttpRpcServerTest {

  @Test
  public void test() throws IOException, InterruptedException {
    RpcFactory.getServer("http").bind(8080);
    Thread.sleep(60000);
  }
}
