<div align="center">
<img width="200" src="http://6tail.cn/nlf2-logo.png" alt="NLF2">
</div>

# NLF2 [![License](https://img.shields.io/badge/license-MIT-4EB1BA.svg?style=flat-square)](https://github.com/6tail/nlf2/blob/master/LICENSE)

NLF2 is a lightweight java framework.

> Support since java 1.5

[简体中文](https://github.com/6tail/nlf2-maven/blob/master/README_ZH.md)

## Usage

nlf2-core v1.8.6 and v1.8.7 on [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cnlf2) works not well, so I suggest you to use snapshot versions:

### Release

```xml
<dependency>
  <groupId>cn.6tail</groupId>
  <artifactId>nlf2-core</artifactId>
  <version>1.8.8</version>
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
  <version>1.8.8-SNAPSHOT</version>
</dependency>
```

### Download

If you will use jars in your projects, I suggest you to download latest snapshot versions with less bugs.

[Download](https://oss.sonatype.org/content/groups/public/cn/6tail/)

## Example

```java
package test;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.core.IRequest;
import com.nlf.extend.web.WebView;
import com.nlf.extend.dao.sql.SqlDaoFactory;

/**
 * Controller without extends or implements
 */
public class User{
  /**
   * Auto mapped URL: ${ctx}/test.User/info?id=xxx
   */
  public Object info(){
    IRequest r = App.getRequest();
    String id = r.get("id","not_empty");
    Bean user = SqlDaoFactory.getDao().getSelecter().table("user").where("id",id).one();
    return WebView.page("/WEB-INF/jsp/user/info.jsp").setAttribute("user",user);
  }
}
```

## Documentation

Please visit [http://6tail.cn/nlfdemo](http://6tail.cn/nlfdemo "http://6tail.cn/nlfdemo")

## Contact

<a target="_blank" href="https://jq.qq.com/?_wv=1027&k=5F9Pbf0"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="NLF" title="NLF"></a>

