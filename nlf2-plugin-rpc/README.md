<div align="center">
<img width="200" src="http://6tail.cn/nlf2-logo.png" alt="NLF2">
</div>

# nlf2-plugin-rpc [![License](https://img.shields.io/badge/license-MIT-4EB1BA.svg?style=flat-square)](https://github.com/6tail/nlf2/blob/master/LICENSE)

A lightweight tool for client/server communication with nlf2, support http or socket.

> Support since java 1.5

[简体中文](https://github.com/6tail/nlf2-maven/blob/master/nlf2-plugin-rpc/README_ZH.md)

## Usage

I suggest you to use nlf2-plugin-rpc v1.8.96 on [Maven Central](https://search.maven.org/search?q=nlf2-plugin-rpc).

### Release

```xml
<dependency>
  <groupId>cn.6tail</groupId>
  <artifactId>nlf2-core</artifactId>
  <version>1.8.96</version>
</dependency>
 
<dependency>
  <groupId>cn.6tail</groupId>
  <artifactId>nlf2-plugin-rpc</artifactId>
  <version>1.8.96</version>
</dependency>
```
 
### Snapshot

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

### Download

If you will use jars in your projects, I suggest you to download latest snapshot versions with less bugs.

[Download](https://github.com/6tail/nlf2-maven/releases)

## Example

```java
package test;
 
import com.nlf.extend.rpc.RpcFactory;
 
/**
 * Startup a lightweight server
 */
public class Startup{
 
  /**
   * Startup a lightweight http server at port 8080
   * 
   * <p>Visit http://localhost:8080</p>
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
 * Controller without extends or implements
 */
public class Say{
  
  /**
   * Auto mapped URL: http://localhost:8080/test.Say/hello
   * 
   * <p>Will output {"data":"Hello world!","success":true}</p>
   */
  public JsonView hello(){
    return View.json("Hello world!");
  }
  
}
```

## Setting

Just put a .properties file into anywhere with settings as below to support static resources access (v1.8.9 not supported):

```
#context path, starts with /, default=/
nlf.rpc.server.context=/myapp

#buffer size, default=20480 (bytes)
nlf.rpc.server.http.resource.buffer_size=1024
 
#resource files root dir, default=empty string (current dir)
nlf.rpc.server.http.resource.root=/usr/html
 
#charset, default=UTF-8
nlf.rpc.server.http.resource.charset=UTF-8
 
#home page in dir, default=index.html
nlf.rpc.server.http.resource.home_page=index.htm
 
#allow list dir files, default=true
nlf.rpc.server.http.resource.dir_allowed=false
 
#gzip, default=true
nlf.rpc.server.http.resource.gzip.enable=false
 
#min gzip file size, default=10240
nlf.rpc.server.http.resource.gzip.min_size=1024
 
#gzip file ext, default=.htm,.html,.css,.js,.bmp,.gif,.jpg,.jpeg,.png,.xml,.svg,.ttf (split by ,)
nlf.rpc.server.http.resource.gzip.file_ext=.html,.js,.css
```

## Contact

<a target="_blank" href="https://jq.qq.com/?_wv=1027&k=5F9Pbf0"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="NLF" title="NLF"></a>

