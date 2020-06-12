package action;

import com.nlf.App;
import com.nlf.View;
import com.nlf.core.IRequest;

public class Say {
  public Object hello(){
    IRequest r = App.getRequest();
    String name = r.get("name");
    System.out.println("name="+name);
    System.out.println("ip="+r.getClient().getIp());
    System.out.println("locale="+r.getClient().getLocale());
    System.out.println(r.getBodyString());
    System.out.println(r.getBody());
    return View.json(name+"，你好");
  }
}
