package session.redis;

import com.nlf.extend.dao.noSql.INoSqlDao;
import com.nlf.extend.dao.noSql.NoSqlDaoFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * 基于redis的http session上下文
 */
@Deprecated
public class RedisHttpSessionContext implements HttpSessionContext{

  protected ServletContext servletContext;

  public RedisHttpSessionContext(ServletContext servletContext){
    this.servletContext = servletContext;
  }

  @Deprecated
  public HttpSession getSession(String id) {
    RedisHttpSession session = null;
    if(null!=id&&id.length()>0){
      INoSqlDao dao;
      if(null==RedisHttpSession.DB_ALIAS||RedisHttpSession.DB_ALIAS.length()<1){
        dao = NoSqlDaoFactory.getDao();
      }else{
        dao = NoSqlDaoFactory.getDao(RedisHttpSession.DB_ALIAS);
      }
      boolean exists = dao.exists(RedisHttpSession.KEY_PREFIX+id);
      if(exists){
        session = new RedisHttpSession(id,servletContext);
      }
    }
    return session;
  }

  @Deprecated
  public Enumeration<String> getIds() {
    INoSqlDao dao;
    if(null==RedisHttpSession.DB_ALIAS||RedisHttpSession.DB_ALIAS.length()<1){
      dao = NoSqlDaoFactory.getDao();
    }else{
      dao = NoSqlDaoFactory.getDao(RedisHttpSession.DB_ALIAS);
    }
    Set<String> keys = dao.keys(RedisHttpSession.KEY_PREFIX+"*");
    Set<String> ids = new HashSet<String>(keys.size());
    for(String key:keys){
      ids.add(key.replace(RedisHttpSession.KEY_PREFIX,""));
    }
    return Collections.enumeration(ids);
  }
}