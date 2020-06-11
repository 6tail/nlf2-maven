package com.nlf.extend.rpc;

import com.nlf.App;
import com.nlf.extend.rpc.client.IRpcClient;
import com.nlf.extend.rpc.client.exception.ClientTypeNotSupportException;
import com.nlf.extend.rpc.server.IRpcServer;
import com.nlf.extend.rpc.server.exception.ServerTypeNotSupportException;

import java.util.List;

/**
 * RPC工厂
 *
 * @author 6tail
 */
public class RpcFactory {

  /**
   * 获取服务端
   * @param type 类型，如socket/http
   * @return RPC服务端
   */
  public static IRpcServer getServer(String type){
    List<String> impls = App.getImplements(IRpcServer.class);
    for(String klass:impls) {
      IRpcServer server = App.getProxy().newInstance(klass);
      if(server.support(type)){
        return server;
      }
    }
    throw new ServerTypeNotSupportException();
  }

  /**
   * 获取客户端
   * @param type 类型，如socket/http
   * @return RPC客户端
   */
  public static IRpcClient getClient(String type){
    List<String> impls = App.getImplements(IRpcClient.class);
    for(String klass:impls) {
      IRpcClient client = App.getProxy().newInstance(klass);
      if(client.support(type)){
        return client;
      }
    }
    throw new ClientTypeNotSupportException();
  }
}
