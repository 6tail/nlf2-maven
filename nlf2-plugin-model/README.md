<div align="center">
<img width="200" src="http://6tail.cn/nlf2-logo.png" alt="NLF2">
</div>

# nlf2-plugin-model [![License](https://img.shields.io/badge/license-MIT-4EB1BA.svg?style=flat-square)](https://github.com/6tail/nlf2/blob/master/LICENSE)

封装基于Model对象的关系数据库操作，让一些简单增删改查的场景变得非常简单。

> 支持java 1.5及以上版本。

## 使用

建议使用[Maven仓库](https://search.maven.org/search?q=nlf2-plugin-model)中的nlf2-plugin-model v1.8.94版本。

### 稳定版本

```xml
<dependency>
  <groupId>cn.6tail</groupId>
  <artifactId>nlf2-core</artifactId>
  <version>1.8.94</version>
</dependency>
 
<dependency>
  <groupId>cn.6tail</groupId>
  <artifactId>nlf2-plugin-model</artifactId>
  <version>1.8.94</version>
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
  <artifactId>nlf2-plugin-model</artifactId>
  <version>1.8.9-SNAPSHOT</version>
</dependency>
```

### 下载jar

如果使用jar，建议下载最新的SNAPSHOT版本，bug将得到及时的修复。

[下载地址](https://github.com/6tail/nlf2-maven/releases)

## 示例

```java
package test;
 
import com.nlf.extend.model.Model;
 
import java.util.Date;
 
/**
 * 用户Model
 */
public class User extends Model<User> {
 
  private int id;
  private String name;
  private Date time;
 
  /**
   * 对应test表，主键为id
   */
  public User() {
    super("test","id");
  }
 
  public int getId() {
    return id;
  }
 
  public void setId(int id) {
    this.id = id;
  }
 
  public String getName() {
    return name;
  }
 
  public void setName(String name) {
    this.name = name;
  }
 
  public Date getTime() {
    return time;
  }
 
  public void setTime(Date time) {
    this.time = time;
  }
}
```

```java
package test;
 
import com.nlf.extend.model.paging.ModelPage;
import com.nlf.serialize.json.JSON;
import org.junit.Test;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
 
/**
 * 示例
 */
public class UserTest {
 
  /**
   * 通过主键加载
   */
  @Test
  public void load(){
    User user = new User();
    user.setId(1);
 
    user.load();
 
    System.out.println(JSON.fromObject(user));
  }
 
  /**
   * 分页查询
   */
  @Test
  public void page(){
    ModelPage<User> l = new User().selecter().page(1,20);
    System.out.println(l);
  }
 
  /**
   * 列表查询
   */
  @Test
  public void query(){
    List<User> users = new User().selecter().column("id,name").where("id",1).query();
    System.out.println(JSON.fromObject(users));
  }
 
  /**
   * 遍历
   */
  @Test
  public void iterator(){
    Iterator<User> users = new User().selecter().iterator();
    while(users.hasNext()){
      User user = users.next();
      System.out.println(JSON.fromObject(user));
    }
  }
 
  /**
   * 计数
   */
  @Test
  public void count(){
    int count = new User().selecter().count();
    System.out.println(count);
  }
 
  /**
   * 插入新数据
   */
  @Test
  public void insert(){
    User user = new User();
 
    //不存在该ID的记录时保存可插入数据
    user.setId(10);
    user.setName("李四");
    user.save();
 
    System.out.println(JSON.fromObject(user));
  }
 
  /**
   * 增量更新数据
   */
  @Test
  public void update(){
    User user = new User();
 
    //数据库已存在ID为17的记录，先加载出来
    user.setId(17);
    user.load();
 
    //修改姓名
    user.setName("李四18");
    user.save();
 
    //当再次修改为相同的姓名时，实际并不会执行update语句
    user.setName("李四18");
    user.save();
  }
 
  /**
   * 全量更新数据
   */
  @Test
  public void updateWhole(){
    User user = new User();
 
    //数据库已存在ID为17的记录时，先不加载即可全量更新字段
    user.setId(17);
 
    user.setName("李四18");
    user.setTime(new Date());
    user.save();
  }
}
```

## 联系

<a target="_blank" href="https://jq.qq.com/?_wv=1027&k=5F9Pbf0"><img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="NLF" title="NLF"></a>

