package com.nlf.extend.model;

import com.nlf.Bean;
import com.nlf.core.AbstractBean;
import com.nlf.extend.dao.sql.*;
import com.nlf.extend.model.impl.DefaultModelSelecter;
import com.nlf.serialize.json.JSON;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Model
 * @author 6tail
 */
public class Model<M extends Model> extends AbstractBean{

  /** 默认主键 */
  public static final String DEFAULT_PRIMARY_KEY = "id";
  private static final Pattern PATTERN_UPPER = Pattern.compile("[A-Z]");
  private static final Pattern PATTERN_LINE = Pattern.compile("_(\\w)");

  /** 数据源别名 */
  protected String alias;

  /** 数据表名 */
  protected String tableName;

  /** 主键 */
  protected Set<String> primaryKeys = new LinkedHashSet<String>();

  /** 自增字段 */
  protected String autoIncrement;

  protected Bean cache;

  /**
   * 初始化
   * @param tableName 数据表名
   */
  protected Model(String tableName){
    this(tableName, new String[]{}, null);
  }

  /**
   * 初始化
   * @param tableName 数据表名
   * @param primaryKey 主键（对象属性会转换为数据字段）
   */
  protected Model(String tableName, String primaryKey){
    this(tableName, primaryKey, null);
  }

  /**
   * 初始化
   * @param tableName 数据表名
   * @param primaryKey 主键（对象属性会转换为数据字段）
   * @param autoIncrement 自增字段（对象属性会转换为数据字段）
   */
  protected Model(String tableName, String primaryKey, String autoIncrement){
    this(tableName, new String[]{primaryKey}, autoIncrement);
  }

  /**
   * 初始化
   * @param tableName 数据表名
   * @param primaryKeys 联合主键（对象属性会转换为数据字段）
   * @param autoIncrement 自增字段（对象属性会转换为数据字段）
   */
  protected Model(String tableName, String[] primaryKeys, String autoIncrement){
    this(null,tableName,primaryKeys,autoIncrement);
  }

  /**
   * 初始化
   * @param alias 数据源别名
   * @param tableName 数据表名
   * @param primaryKeys 联合主键（对象属性会转换为数据字段）
   * @param autoIncrement 自增字段（对象属性会转换为数据字段）
   */
  protected Model(String alias,String tableName, String[] primaryKeys, String autoIncrement){
    this.alias = alias;
    this.tableName = tableName;
    if(null != primaryKeys){
      for(String key:primaryKeys){
        this.primaryKeys.add(encode(key));
      }
    }
    //如果未设置主键，使用默认主键
    if(this.primaryKeys.isEmpty()){
      this.primaryKeys.add(DEFAULT_PRIMARY_KEY);
    }
    this.autoIncrement = encode(autoIncrement);
  }

  /**
   * 将对象属性名转换为数据字段名（默认将驼峰转换为下划线）
   * @param property 对象属性名
   * @return 数据字段名
   */
  public String encode(String property){
    if(null==property){
      return null;
    }
    Matcher matcher = PATTERN_UPPER.matcher(property);
    StringBuffer s = new StringBuffer();
    while(matcher.find()){
      matcher.appendReplacement(s, "_" + matcher.group(0).toLowerCase());
    }
    matcher.appendTail(s);
    return s.toString();
  }

  /**
   * 将数据字段名转换为对象属性名（默认将下划线转换为驼峰）
   * @param field 数据字段名
   * @return 对象属性名
   */
  public String decode(String field){
    if(null==field){
      return null;
    }
    field = field.toLowerCase();
    Matcher matcher = PATTERN_LINE.matcher(field);
    StringBuffer s = new StringBuffer();
    while(matcher.find()){
      matcher.appendReplacement(s, matcher.group(1).toUpperCase());
    }
    matcher.appendTail(s);
    return s.toString();
  }

  public String alias(){
    return alias;
  }

  public String tableName(){
    return tableName;
  }

  public Set<String> primaryKeys(){
    return primaryKeys;
  }

  public String autoIncrement(){
    return autoIncrement;
  }

  /**
   * delete
   */
  public void delete(){
    Bean param = JSON.toBean(JSON.fromObject(this));
    ISqlDao dao = (null==alias)?SqlDaoFactory.getDao():SqlDaoFactory.getDao(alias);
    ISqlDeleter deleter = dao.getDeleter().table(tableName);
    for(String key : primaryKeys){
      deleter.where(key, param.get(decode(key)));
    }
    deleter.delete();
  }

  /**
   * auto insert or update
   */
  public void save(){
    Bean record = JSON.toBean(JSON.fromObject(this));
    if(record.isEmpty()){
      return;
    }
    Set<String> needEncode = new HashSet<String>();
    for(String key:record.keySet()){
      if(!encode(key).equals(key)){
        needEncode.add(key);
      }
    }
    for(String key:needEncode){
      record.set(encode(key), record.get(key));
      record.remove(key);
    }
    //操作类型：0-unknown，1-insert，2-增量更新，3-全量更新
    int type = 0;
    if(null!=cache){
      type = 2;
    }else{
      for(String key : primaryKeys){
        if(record.getString(key, "").length() < 1){
          //如果有主键未赋值，肯定是insert
          type = 1;
          break;
        }
      }
    }
    ISqlDao dao = (null==alias)?SqlDaoFactory.getDao():SqlDaoFactory.getDao(alias);
    //如果还不能确定，则检查数据库是否已存在该记录
    if(0==type){
      ISqlSelecter selecter = dao.getSelecter().table(tableName);
      for(String key : primaryKeys){
        selecter.where(key, record.get(key));
      }
      type = selecter.count()>0?3:1;
    }
    Bean temp;
    switch(type){
      case 1:
        //insert
        if(null==autoIncrement||record.getString(autoIncrement,"").length()>0){
          dao.getInserter().table(tableName).set(record).insert();
        }else{
          record.remove(autoIncrement);
          Bean ret = dao.getInserter().table(tableName).set(record).insertAndGetGenerated();
          if(!ret.isEmpty()){
            try{
              String property = decode(autoIncrement);
              BeanInfo info = Introspector.getBeanInfo(this.getClass(), Object.class);
              PropertyDescriptor[] props = info.getPropertyDescriptors();
              for(PropertyDescriptor p : props){
                if(!p.getName().equals(property)){
                  continue;
                }
                Method setter = p.getWriteMethod();
                if(null == setter){
                  continue;
                }
                Object value = convert(ret.get("GENERATED_KEY"), p.getPropertyType(), setter.getGenericParameterTypes()[0]);
                setter.invoke(this, value);
                break;
              }
            }catch(Exception ignore){}
          }
        }
        break;
      case 2:
        //增量更新
        temp = new Bean();
        for(String property:cache.keySet()){
          temp.set(property,cache.get(property));
        }
        for(String property:cache.keySet()){
          String field = encode(property);
          String cacheValue = cache.getString(property, "");
          String value = record.getString(field, "");
          if(cacheValue.equals(value)){
            record.remove(field);
          }else{
            temp.set(property, record.get(field));
          }
        }
        if(!record.isEmpty()){
          ISqlUpdater updater = dao.getUpdater().table(tableName);
          for(String key:primaryKeys){
            updater.where(key, cache.get(decode(key)));
          }
          updater.set(record).update();
          cache = temp;
        }
        break;
      case 3:
        //全量更新
        temp = new Bean();
        for(String field:record.keySet()){
          temp.set(decode(field),record.get(field));
        }
        ISqlUpdater updater = dao.getUpdater().table(tableName);
        for(String key:primaryKeys){
          updater.where(key, record.get(key));
          record.remove(key);
        }
        updater.set(record).update();
        cache = temp;
        break;
      default:
    }
  }

  /**
   * 通过主键加载
   */
  @SuppressWarnings("unchecked")
  public void load(){
    Bean param = JSON.toBean(JSON.fromObject(this));
    ISqlDao dao = (null==alias)?SqlDaoFactory.getDao():SqlDaoFactory.getDao(alias);
    ISqlSelecter selecter = dao.getSelecter().table(tableName);
    for(String key : primaryKeys){
      selecter.where(key, param.get(decode(key)));
    }
    Bean ret = selecter.one();
    try{
      BeanInfo info = Introspector.getBeanInfo(this.getClass(), Object.class);
      PropertyDescriptor[] props = info.getPropertyDescriptors();
      for(PropertyDescriptor p : props){
        Method setter = p.getWriteMethod();
        if(null == setter){
          continue;
        }
        Object value = convert(ret.get(encode(p.getName())), p.getPropertyType(), setter.getGenericParameterTypes()[0]);
        setter.invoke(this, value);
      }
    } catch (Exception ignore){
    }
    cache = JSON.toBean(JSON.fromObject(this));
  }

  public IModelSelecter<M> selecter(){
    return new DefaultModelSelecter<M>(this);
  }
}
