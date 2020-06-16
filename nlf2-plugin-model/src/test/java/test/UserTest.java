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
   * 删除
   */
  @Test
  public void delete(){
    User user = new User();
    user.setId(5);

    user.delete();
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
    List<User> users = new User().selecter().column("id,name,lover_name").where("id",1).query();
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
    user.setId(21);
    user.setName("李四");
    user.setLoverName("王二");
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
    user.setId(28);
    user.setName("李四2");
    user.save();

    //当再次修改为相同的姓名时，实际并不会执行update语句
    user.setName("李四2");
    user.save();
  }

  /**
   * 全量更新数据
   */
  @Test
  public void updateWhole(){
    User user = new User();

    //数据库已存在ID为17的记录时，先不加载即可全量更新字段
    user.setId(28);

    user.setName("李四18");
    user.setTime(new Date());
    user.save();

    //刚刚已更新过，则增量更新
    user.setName("李四2");
    user.save();
  }
}
