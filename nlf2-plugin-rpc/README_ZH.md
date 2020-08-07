<div align="center">
<img width="200" src="http://6tail.cn/nlf2-logo.png" alt="NLF2">
</div>

# nlf2-plugin-rpc [![License](https://img.shields.io/badge/license-MIT-4EB1BA.svg?style=flat-square)](https://github.com/6tail/nlf2/blob/master/LICENSE)

一款基于nlf2的轻量级工具，用来进行客户端和服务器端的通信，目前支持http和socket两种方式。

> 支持java 1.5及以上版本。

[English](https://github.com/6tail/nlf2-maven/tree/master/nlf2-plugin-rpc)

## 使用

建议使用[Maven仓库](https://search.maven.org/search?q=nlf2-plugin-rpc)中的nlf2-plugin-rpc v1.8.96版本。

### 稳定版本

```xml
<dependency>
  <groupId>cn.6tail</groupId>
  <artifactId>nlf2-core</artifactId>
  <version>1.8.98</version>
</dependency>
 
<dependency>
  <groupId>cn.6tail</groupId>
  <artifactId>nlf2-plugin-rpc</artifactId>
  <version>1.8.97</version>
</dependency>
```
 
### 快照版本

```xml
<repository>
  <id>sonatype</id>
  <url>https://oss.sonatype.org/content/groups/public/</url>
  <snapshots>
    <enabled>true</enabled>
    <updatePolicy>daily</updatePolicy>
    <checksumPolicy>warn</checksumPolicy>
  </snapshots>
</repository>
```

```xml
<dependency>
  <groupId>cn.6tail</groupId>
  <artifactId>nlf2-core</artifactId>
  <version>1.8.9-SNAPSHOT</version>
</dependency>
 
<dependency>
  <groupId>cn.6tail</groupId>
  <artifactId>nlf2-plugin-rpc</artifactId>
  <version>1.8.9-SNAPSHOT</version>
</dependency>
```

### 下载jar

如果使用jar，建议下载最新的SNAPSHOT版本，bug将得到及时的修复。

[下载地址](https://github.com/6tail/nlf2-maven/releases)

## 示例

```java
package test;
 
import com.nlf.extend.rpc.RpcFactory;
 
/**
 * 启动一个轻量级的服务端
 */
public class Startup{
 
  /**
   * 在8080端口启动HTTP服务端
   * 
   * <p>访问 http://localhost:8080</p>
   * 
   * @param args args
   */
  public static void main(String[] args){
    RpcFactory.getServer("http").bind(8080);
  }
 
}
```

```java
package test;
 
import com.nlf.View;
import com.nlf.view.JsonView;
 
/**
 * 我是一个没有束缚的控制器
 */
public class Say{
  
  /**
   * 自动映射地址： http://localhost:8080/test.Say/hello
   * 
   * <p>输出结果 {"data":"Hello world!","success":true}</p>
   */
  public JsonView hello(){
    return View.json("Hello world!");
  }
  
}
```

```java
package test;
 
import com.nlf.extend.rpc.RpcFactory;
import com.nlf.extend.rpc.client.IRpcResponse;
 
/**
 * 客户端调用
 */
public class Client{
   
  public static void main(String[] args) {
    IRpcResponse response = RpcFactory.getClient("http").call("localhost", 8080, "/test.Say/hello", null, null);
    if(response.isSuccess()){
      System.out.println(response.getData());
    }else{
      System.out.println(response.getMessage());
    }
  }
   
}
```

## 设置

如果要对静态资源的访问进行配置，只需在任意.properties文件中进行类似如下设置即可 (v1.8.9不支持)：

```
#虚拟路径，以/开头，默认值=/
nlf.rpc.server.context=/myapp

#缓冲区大小, 默认值=20480 (字节)
nlf.rpc.server.http.resource.buffer_size=1024
 
#静态资源文件根目录, 默认值=空字符串 (当前目录)
nlf.rpc.server.http.resource.root=/usr/html
 
#编码, 默认值=UTF-8
nlf.rpc.server.http.resource.charset=UTF-8
 
#目录下的首页文件, 默认值=index.html
nlf.rpc.server.http.resource.home_page=index.htm
 
#是否允许目录文件列表展示, 默认值=true
nlf.rpc.server.http.resource.dir_allowed=false
 
#是否开启gzip, 默认值=true
nlf.rpc.server.http.resource.gzip.enable=false
 
#大于或等于该大小的文件开启gzip压缩, 默认值=10240 (字节)
nlf.rpc.server.http.resource.gzip.min_size=1024
 
#支持gzip压缩的文件后缀, 默认值=.htm,.html,.css,.js,.bmp,.gif,.jpg,.jpeg,.png,.xml,.svg,.ttf (以,分隔)
nlf.rpc.server.http.resource.gzip.file_ext=.html,.js,.css
```

## 联系

<a target="_blank" href="https://jq.qq.com/?_wv=1027&k=5F9Pbf0"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="NLF" title="NLF"></a>

