package test;

import com.nlf.core.ScannerFactory;
import com.nlf.extend.dao.noSql.NoSqlDaoFactory;
import com.nlf.extend.dao.sql.SqlDaoFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DruidTest {
  @Before
  public void before() throws Exception {
    ScannerFactory.setScanner(new MyScanner());
  }

  @After
  public void after() throws Exception {
  }

  @Test
  public void test() throws Exception {
    int count = SqlDaoFactory.getDao().getSelecter().table("user").count();
    Assert.assertTrue(count>0);
  }
}
