<div align="center">
<img width="200" src="http://6tail.cn/nlf2-logo.png" alt="NLF2">
</div>

# nlf2-plugin-properties-db [![License](https://img.shields.io/badge/license-MIT-4EB1BA.svg?style=flat-square)](https://github.com/6tail/nlf2/blob/master/LICENSE)

使用该插件，用.properties文件来替代nlf2默认的db文件夹进行db设置的方式。

> Support since java 1.5

[English](https://github.com/6tail/nlf2-maven/blob/master/nlf2-plugin-properties-db/README_ZH.md)

## 使用

建议使用[Maven仓库](https://search.maven.org/search?q=nlf2-plugin-properties-db)中的nlf2-plugin-properties-db v1.8.91版本。

```xml
<dependency>
  <groupId>cn.6tail</groupId>
  <artifactId>nlf2-core</artifactId>
  <version>1.8.9</version>
</dependency>
 
<dependency>
  <groupId>cn.6tail</groupId>
  <artifactId>nlf2-plugin-properties-db</artifactId>
  <version>1.8.91</version>
</dependency>
```

### 下载jar

如果需要直接使用jar，可在下面的页面中下载：

[下载地址](https://github.com/6tail/nlf2-maven/releases)

## 设置

只需在任意.properties文件中进行类似如下设置即可：

```
#用于修改掉默认的前缀，默认即db.alias
#nlf.dao.setting.prefix=db.alias
 
db.alias=a
 
#多个配置以逗号,间隔
#db.alias=a,b
 
db.alias.a.dbtype=mysql
db.alias.a.server=localhost
db.alias.a.port=3306
db.alias.a.dbname=mysql
db.alias.a.user=root
db.alias.a.password=
db.alias.a.type=jdbc
 
#其他配置
#db.alias.b.dbtype=mysql
#db.alias.b.server=localhost
#db.alias.b.port=3306
#db.alias.b.dbname=mysql
#db.alias.b.user=root
#db.alias.b.password=
#db.alias.b.type=HikariCP
#db.alias.b.properties.useUnicode=true
#db.alias.b.properties.characterEncoding=utf-8
```

## 联系

<a target="_blank" href="https://jq.qq.com/?_wv=1027&k=5F9Pbf0"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="NLF" title="NLF"></a>
