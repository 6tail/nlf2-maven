package session.redis;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.extend.dao.noSql.INoSqlDao;
import com.nlf.extend.dao.noSql.NoSqlDaoFactory;
import com.nlf.extend.session.SessionConfig;
import com.nlf.serialize.json.JSON;
import com.nlf.util.Base64Util;
import com.nlf.util.IDUtil;
import com.nlf.util.IOUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.io.*;
import java.util.Collections;
import java.util.Enumeration;

/**
 * 基于redis存储的http session
 */
public class RedisHttpSession implements HttpSession {

  /** 默认的redis数据库配置别名 */
  public static final String DEFAULT_DB_ALIAS = "";
  /** 默认的存放session的键前缀 */
  public static final String DEFAULT_KEY_PREFIX = "session:";

  /** redis数据库配置别名 */
  public static String DB_ALIAS = App.getPropertyString("session.redis.db.alias",DEFAULT_DB_ALIAS);
  /** 存放session的键前缀 */
  public static String KEY_PREFIX = App.getPropertyString("session.redis.key_prefix",DEFAULT_KEY_PREFIX);

  /** session id */
  private String id;
  private ServletContext servletContext;

  protected RedisHttpSession(String id, ServletContext servletContext){
    this.id = id;
    this.servletContext = servletContext;
  }

  /**
   * 将对象序列化为字符串便于存放到redis
   * @param o 对象
   * @return 字符串
   */
  protected String wrap(Object o){
    ByteArrayOutputStream bObj = null;
    ObjectOutputStream oObj = null;
    try{
      bObj = new ByteArrayOutputStream();
      oObj = new ObjectOutputStream(bObj);
      oObj.writeObject(o);
      oObj.flush();
      return Base64Util.encode(bObj.toByteArray());
    }catch(IOException e){
      throw new RuntimeException(e);
    }finally{
      IOUtil.closeQuietly(oObj);
      IOUtil.closeQuietly(bObj);
    }
  }

  /**
   * 从字符串反序列化为对象
   * @param s 字符串
   * @return 对象
   */
  protected Object parse(String s){
    Object obj;
    ObjectInputStream iObj = null;
    try{
      iObj = new ObjectInputStream(new ByteArrayInputStream(Base64Util.decode(s)));
      obj = iObj.readObject();
    }catch(ClassNotFoundException e){
      throw new RuntimeException(e);
    }catch(IOException e){
      throw new RuntimeException(e);
    }finally{
      IOUtil.closeQuietly(iObj);
    }
    return obj;
  }

  /**
   * 创建http session
   * @param id session id
   * @param autoCreate 是否自动创建
   * @param servletContext ServletContext
   * @return 基于redis存储的http session
   */
  public static RedisHttpSession create(String id, boolean autoCreate, ServletContext servletContext){
    RedisHttpSession session = null;
    Bean obj = null;
    INoSqlDao dao;
    if(null==DB_ALIAS||DB_ALIAS.length()<1){
      dao = NoSqlDaoFactory.getDao();
    }else{
      dao = NoSqlDaoFactory.getDao(DB_ALIAS);
    }
    if(null!=id&&id.length()>0){
      String rs = dao.get(KEY_PREFIX + id);
      if(null!=rs&&rs.length()>0){
        obj = JSON.toBean(rs);
      }
    }
    int maxInactiveInterval = SessionConfig.MAX_INACTIVE_INTERVAL;
    if(maxInactiveInterval<1){
      maxInactiveInterval = -1;
    }
    long timestamp = System.currentTimeMillis();
    if(null!=obj){
      session = new RedisHttpSession(id,servletContext);
      obj.set("lastAccessedTime",timestamp);
      obj.set("isNew",false);
      dao.set(KEY_PREFIX+id, JSON.fromObject(obj));
      dao.expire(KEY_PREFIX+id,maxInactiveInterval);
    }else if(autoCreate){
      String sessionId = IDUtil.next();
      session = new RedisHttpSession(sessionId,servletContext);
      obj = new Bean();
      obj.set("id",sessionId);
      obj.set("creationTime",timestamp);
      obj.set("lastAccessedTime",timestamp);
      obj.set("maxInactiveInterval",maxInactiveInterval);
      obj.set("isNew",true);
      dao.set(KEY_PREFIX+sessionId, JSON.fromObject(obj));
      dao.expire(KEY_PREFIX+sessionId,maxInactiveInterval);
    }
    return session;
  }

  /**
   * 读取当前session的存储内容
   * @return 当前session的存储内容
   */
  protected Bean read(){
    INoSqlDao dao;
    if(null==DB_ALIAS||DB_ALIAS.length()<1){
      dao = NoSqlDaoFactory.getDao();
    }else{
      dao = NoSqlDaoFactory.getDao(DB_ALIAS);
    }
    String rs = dao.get(KEY_PREFIX + id);
    if(null==rs||rs.length()<1){
      return new Bean();
    }
    return JSON.toBean(rs);
  }

  public long getCreationTime() {
    return read().getLong("creationTime",0);
  }

  public String getId() {
    return id;
  }

  public long getLastAccessedTime() {
    return read().getLong("lastAccessedTime",0);
  }

  public ServletContext getServletContext() {
    return servletContext;
  }

  public void setMaxInactiveInterval(int i) {
    Bean o = read();
    if(i<1){
      i = -1;
    }
    o.set("maxInactiveInterval",i);
    INoSqlDao dao;
    if(null==DB_ALIAS||DB_ALIAS.length()<1){
      dao = NoSqlDaoFactory.getDao();
    }else{
      dao = NoSqlDaoFactory.getDao(DB_ALIAS);
    }
    dao.set(KEY_PREFIX+id, JSON.fromObject(o));
    dao.expire(KEY_PREFIX+id,i);
  }

  public int getMaxInactiveInterval() {
    return read().getInt("maxInactiveInterval",-1);
  }

  @Deprecated
  public HttpSessionContext getSessionContext() {
    return new RedisHttpSessionContext(servletContext);
  }

  public Object getAttribute(String s) {
    Bean o = read();
    Bean attributes = o.getBean("attributes",new Bean());
    String value = attributes.getString(s);
    return null==value?null:parse(value);
  }

  public Object getValue(String s) {
    return getAttribute(s);
  }

  public Enumeration<String> getAttributeNames() {
    Bean o = read();
    Bean attributes = o.getBean("attributes",new Bean());
    return Collections.enumeration(attributes.keySet());
  }

  public String[] getValueNames() {
    Bean o = read();
    Bean attributes = o.getBean("attributes",new Bean());
    return attributes.keySet().toArray(new String[0]);
  }

  public void setAttribute(String s, Object o) {
    Bean obj = read();
    Bean attributes = obj.getBean("attributes",new Bean());
    attributes.set(s,wrap(o));
    obj.set("attributes",attributes);
    INoSqlDao dao;
    if(null==DB_ALIAS||DB_ALIAS.length()<1){
      dao = NoSqlDaoFactory.getDao();
    }else{
      dao = NoSqlDaoFactory.getDao(DB_ALIAS);
    }
    dao.set(KEY_PREFIX + id, JSON.fromObject(obj));
  }

  public void putValue(String s, Object o) {
    setAttribute(s,o);
  }

  public void removeAttribute(String s) {
    Bean o = read();
    Bean attributes = o.getBean("attributes");
    if(null!=attributes) {
      attributes.remove(s);
      INoSqlDao dao;
      if(null==DB_ALIAS||DB_ALIAS.length()<1){
        dao = NoSqlDaoFactory.getDao();
      }else{
        dao = NoSqlDaoFactory.getDao(DB_ALIAS);
      }
      dao.set(KEY_PREFIX + id, JSON.fromObject(o));
    }
  }

  public void removeValue(String s) {
    removeAttribute(s);
  }

  public void invalidate() {
    INoSqlDao dao;
    if(null==DB_ALIAS||DB_ALIAS.length()<1){
      dao = NoSqlDaoFactory.getDao();
    }else{
      dao = NoSqlDaoFactory.getDao(DB_ALIAS);
    }
    dao.delete(KEY_PREFIX + id);
  }

  public boolean isNew() {
    return read().getBoolean("isNew",true);
  }
}