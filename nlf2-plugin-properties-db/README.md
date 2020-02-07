<div align="center">
<img width="200" src="http://6tail.cn/nlf2-logo.png" alt="NLF2">
</div>

# nlf2-plugin-properties-db [![License](https://img.shields.io/badge/license-MIT-4EB1BA.svg?style=flat-square)](https://github.com/6tail/nlf2/blob/master/LICENSE)

Use .properties file to replace "db" folder for db setting in nlf2.

> Support since java 1.5

[简体中文](https://github.com/6tail/nlf2-maven/blob/master/nlf2-plugin-properties-db/README_ZH.md)

## Usage

I suggest you to use nlf2-plugin-properties-db v1.8.9 on [Maven Central](https://search.maven.org/search?q=nlf2-plugin-properties-db).

```xml
<dependency>
  <groupId>cn.6tail</groupId>
  <artifactId>nlf2-core</artifactId>
  <version>1.8.9</version>
</dependency>
 
<dependency>
  <groupId>cn.6tail</groupId>
  <artifactId>nlf2-plugin-properties-db</artifactId>
  <version>1.8.9</version>
</dependency>
```

### Download

If you will use jars in your projects, download from this page:

[Download](https://github.com/6tail/nlf2-maven/releases)

## Setting

Just put a .properties file into anywhere with settings as below:

```
#to change default key prefix, default=db.alias
#nlf.dao.setting.prefix=db.alias
 
db.alias=a
 
#for multiple db settings, split by comma
#db.alias=a,b
 
db.alias.a.dbtype=mysql
db.alias.a.server=localhost
db.alias.a.port=3306
db.alias.a.dbname=mysql
db.alias.a.user=root
db.alias.a.password=
db.alias.a.type=jdbc
 
#other alias setting
#db.alias.b.dbtype=mysql
#db.alias.b.server=localhost
#db.alias.b.port=3306
#db.alias.b.dbname=mysql
#db.alias.b.user=root
#db.alias.b.password=
#db.alias.b.type=jdbc
```

## Contact

<a target="_blank" href="https://jq.qq.com/?_wv=1027&k=5F9Pbf0"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="NLF" title="NLF"></a>
