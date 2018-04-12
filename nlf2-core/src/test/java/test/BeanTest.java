package test;

import com.nlf.Bean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class BeanTest {

  Bean o;

  @Before
  public void before(){
    o = new Bean();
    o.set("users",new Bean("age",20));
    o.set("number",new BigDecimal(20));
  }

  @Test
  public void testList(){
    Assert.assertEquals(20,o.getList("users",Bean.class).get(0).getInt("age",1));
  }

  @Test
  public void testObject(){
    Assert.assertEquals(20,o.get("number",BigDecimal.class,new BigDecimal(1)).intValue());
  }
}
