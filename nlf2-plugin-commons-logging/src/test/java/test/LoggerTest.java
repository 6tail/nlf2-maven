package test;

import com.nlf.extend.log.commons.logging.LoggingLog;
import com.nlf.log.Logger;
import org.junit.Assert;
import org.junit.Test;

public class LoggerTest {
  @Test
  public void test() {
    Assert.assertEquals(LoggingLog.class,Logger.getLog().getClass());
  }
}
