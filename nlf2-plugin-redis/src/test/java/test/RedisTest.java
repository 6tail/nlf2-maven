package test;

import com.nlf.extend.dao.noSql.NoSqlDaoFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class RedisTest {

  @Test
  public void testSetGet() {
    NoSqlDaoFactory.getDao().set("test:foo","bar");
    String bar = NoSqlDaoFactory.getDao().get("test:foo");
    Assert.assertEquals("bar",bar);
  }

  @Test
  public void testDelete() {
    NoSqlDaoFactory.getDao().delete("test:foo");
    String bar = NoSqlDaoFactory.getDao().get("test:foo");
    Assert.assertNull(bar);
  }

  @Test
  public void testKeys() {
    NoSqlDaoFactory.getDao().set("test:key:1","1");
    NoSqlDaoFactory.getDao().set("test:key:2","2");
    Set<String> keys = NoSqlDaoFactory.getDao().keys("test:key:*");
    Assert.assertEquals(2,keys.size());
  }
}
