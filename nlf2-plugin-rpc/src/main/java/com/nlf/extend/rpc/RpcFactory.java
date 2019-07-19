package com.nlf.extend.rpc;

import com.nlf.App;
import com.nlf.extend.rpc.server.IRpcServer;

import java.util.List;

/**
 * RPC工厂
 *
 * @author 6tail
 */
public class RpcFactory {

  public static IRpcServer getServer(String type){
    List<String> impls = App.getImplements(IRpcServer.class);
    for(String klass:impls) {
      IRpcServer server = App.getProxy().newInstance(klass);
      if(server.support(type)){
        return server;
      }
    }
    return null;
  }
}
