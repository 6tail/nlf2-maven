package test;

import com.nlf.App;
import com.nlf.core.IScanner;
import org.junit.Assert;
import org.junit.Test;

public class AppTest {

  @Test
  public void testGetImplements(){
    Assert.assertEquals(App.getImplements(IScanner.class.getName()), App.getImplements(IScanner.class));
  }
}