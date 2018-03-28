package test;

import com.nlf.core.ScannerFactory;
import com.nlf.extend.dao.noSql.NoSqlDaoFactory;
import com.nlf.extend.dao.noSql.dbType.redis.RedisDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

public class RedisTest {
  @Before
  public void before() throws Exception {
    ScannerFactory.setScanner(new MyScanner());
  }

  @After
  public void after() throws Exception {
  }

  @Test
  public void test() throws Exception {
    NoSqlDaoFactory.getDao().set("hello","world");
    String hello = NoSqlDaoFactory.getDao().get("hello");
    Assert.assertEquals("world",hello);
  }
}
