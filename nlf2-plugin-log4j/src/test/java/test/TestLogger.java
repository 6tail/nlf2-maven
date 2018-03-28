package test;

import com.nlf.extend.log.log4j.Log4jLog;
import com.nlf.log.Logger;
import org.junit.Assert;
import org.junit.Test;
public class TestLogger {
  @Test
  public void test() {
    Assert.assertEquals(Log4jLog.class,Logger.getLog().getClass());
  }
}
