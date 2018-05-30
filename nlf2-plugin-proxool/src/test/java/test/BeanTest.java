package test;

import com.nlf.Bean;
import com.nlf.extend.dao.sql.SqlDaoFactory;
import org.junit.Assert;
import org.junit.Test;

public class BeanTest {
  @Test
  public void test() {
    Bean o = new Bean();
    o.set("host","%");
    o.set("id",1);
    int count = SqlDaoFactory.getDao().getSelecter().table("user").where("Host=:host",new Bean("host","%")).count();
    Assert.assertTrue(count>-1);
    count = SqlDaoFactory.getDao().getSelecter().table("user").where("Host",o.get("host")).count();
    Assert.assertTrue(count>-1);
  }
}
