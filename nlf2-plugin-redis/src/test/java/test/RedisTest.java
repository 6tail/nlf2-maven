package test;

import com.nlf.extend.dao.noSql.NoSqlDaoFactory;
import org.junit.Assert;
import org.junit.Test;

public class RedisTest {

  @Test
  public void test() {
    NoSqlDaoFactory.getDao().set("hello","world");
    String hello = NoSqlDaoFactory.getDao().get("hello");
    Assert.assertEquals("world",hello);
  }
}
