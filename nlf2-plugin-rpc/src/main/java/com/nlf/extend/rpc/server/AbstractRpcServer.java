package com.nlf.extend.rpc.server;

import com.nlf.App;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 抽象RPC服务端
 *
 * @author 6tail
 */
public abstract class AbstractRpcServer implements IRpcServer {
  protected ExecutorService getExecutor(){
    int cpu = Runtime.getRuntime().availableProcessors();
    int corePoolSize = cpu * App.getPropertyInt("nlf.rpc.server.thread.core_pool_size",2);
    int maximumPoolSize = cpu * App.getPropertyInt("nlf.rpc.server.thread.maximum_pool_size",100);
    long keepAliveMilliSeconds = App.getPropertyLong("nlf.rpc.server.thread.keep_alive_milliseconds",60000L);
    return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveMilliSeconds, TimeUnit.MILLISECONDS,new SynchronousQueue<Runnable>(),new ThreadPoolExecutor.AbortPolicy());
  }
}
