<div align="center">
<img width="200" src="http://6tail.cn/nlf2-logo.png" alt="NLF2">
</div>

# NLF2 [![License](https://img.shields.io/badge/license-MIT-4EB1BA.svg?style=flat-square)](https://github.com/6tail/nlf2/blob/master/LICENSE)

NLF2框架是一款原创的、低调的轻量级java框架，它无侵入、无依赖、零配置、无注解，看一点例子，你很快就能上手。

> 支持java 1.5及以上版本。

[English](https://github.com/6tail/nlf2-maven/blob/master/README.md)

## 使用

[Maven仓库](http://search.maven.org/#search%7Cga%7C1%7Cnlf2)中的nlf2-core v1.8.6和v1.8.7存在一些问题，建议使用快照版本：

### 稳定版本

```xml
<dependency>
  <groupId>cn.6tail</groupId>
  <artifactId>nlf2-core</artifactId>
  <version>1.8.8</version>
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
  <version>1.8.8-SNAPSHOT</version>
</dependency>
```

### 下载jar

如果使用jar，建议下载最新的SNAPSHOT版本，bug将得到及时的修复。

[下载地址](https://oss.sonatype.org/content/groups/public/cn/6tail/)

## 示例

```java
package test;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.core.IRequest;
import com.nlf.extend.web.WebView;
import com.nlf.extend.dao.sql.SqlDaoFactory;

/**
 * 我是一个没有束缚的控制器
 */
public class User{
  /**
   * 自动映射地址：${ctx}/test.User/info?id=xxx
   */
  public Object info(){
    IRequest r = App.getRequest();
    String id = r.get("id","not_empty");
    Bean user = SqlDaoFactory.getDao().getSelecter().table("user").where("id",id).one();
    return WebView.page("/WEB-INF/jsp/user/info.jsp").setAttribute("user",user);
  }
}
```

## 文档

请移步至 [http://6tail.cn/nlfdemo](http://6tail.cn/nlfdemo "http://6tail.cn/nlfdemo")

## 联系

<a target="_blank" href="https://jq.qq.com/?_wv=1027&k=5F9Pbf0"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="NLF" title="NLF"></a>

