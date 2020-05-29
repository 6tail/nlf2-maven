package com.nlf.extend.model;

import com.nlf.Bean;
import com.nlf.core.AbstractBean;
import com.nlf.extend.dao.sql.ISqlDeleter;
import com.nlf.extend.dao.sql.ISqlSelecter;
import com.nlf.extend.dao.sql.ISqlUpdater;
import com.nlf.extend.dao.sql.SqlDaoFactory;
import com.nlf.extend.model.impl.DefaultModelSelecter;
import com.nlf.serialize.json.JSON;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Model
 * @author 6tail
 */
public class Model<M extends Model> extends AbstractBean {
  public static final String DEFAULT_PRIMARY_KEY = "id";
  protected String alias;
  protected String tableName;
  protected Set<String> primaryKeys = new LinkedHashSet<String>();
  protected String autoIncrement;
  protected Bean cache;
  protected IModelSelecter<M> selecter;

  protected Model() {
  }

  protected Model(String tableName, String primaryKey) {
    this(tableName, primaryKey, null);
  }

  protected Model(String[] primaryKeys, String autoIncrement) {
    this(null, primaryKeys, autoIncrement);
  }

  protected Model(String tableName, String primaryKey, String autoIncrement) {
    this(tableName, new String[]{primaryKey}, autoIncrement);
  }

  protected Model(String tableName) {
    this(tableName, new String[]{}, null);
  }

  protected Model(String tableName, String[] primaryKeys, String autoIncrement) {
    this.tableName = tableName;
    if (null != primaryKeys) {
      this.primaryKeys.addAll(Arrays.asList(primaryKeys));
    }
    this.autoIncrement = autoIncrement;
  }

  public String alias() {
    return alias;
  }

  public String tableName() {
    return tableName;
  }

  public Set<String> primaryKeys() {
    return primaryKeys;
  }

  public String autoIncrement() {
    return autoIncrement;
  }

  public void delete() {
    Bean param = JSON.toBean(JSON.fromObject(this));
    ISqlDeleter deleter = SqlDaoFactory.getDao().getDeleter().table(tableName);
    for (String key : primaryKeys) {
      deleter.where(key, param.get(key));
    }
    deleter.delete();
  }

  public void save() {
    Bean param = JSON.toBean(JSON.fromObject(this));
    if (param.isEmpty()) {
      return;
    }
    boolean insert = false;
    for (String key : primaryKeys) {
      if (key.equals(autoIncrement)) {
        if (param.getLong(key, 0) < 1) {
          insert = true;
          break;
        }
      } else if (param.getString(key, "").length() < 1) {
        insert = true;
        break;
      }
    }
    if (insert) {
      if (null == autoIncrement) {
        SqlDaoFactory.getDao().getInserter().table(tableName).set(param).insert();
      } else {
        param.remove(autoIncrement);
        Bean ret = SqlDaoFactory.getDao().getInserter().table(tableName).set(param).insertAndGetGenerated();
        if (!ret.isEmpty()) {
          try {
            BeanInfo info = Introspector.getBeanInfo(this.getClass(), Object.class);
            PropertyDescriptor[] props = info.getPropertyDescriptors();
            for (PropertyDescriptor p : props) {
              if (!autoIncrement.equals(p.getName())) {
                continue;
              }
              Method setterMethod = p.getWriteMethod();
              if (null == setterMethod) {
                continue;
              }
              Class<?> propType = p.getPropertyType();
              Type paramType = setterMethod.getGenericParameterTypes()[0];
              Object value = convert(ret.get("GENERATED_KEY"), propType, paramType);
              setterMethod.invoke(this, value);
              break;
            }
          } catch (Exception ignore) {
          }
        }
      }
    } else {
      if (null != cache) {
        for (String key : cache.keySet()) {
          String cacheValue = cache.getString(key, "");
          String value = param.getString(key, "");
          if (cacheValue.equals(value)) {
            param.remove(key);
          } else {
            cache.set(key, param.get(key));
          }
        }
      }

      if (!param.isEmpty()) {
        ISqlUpdater updater = SqlDaoFactory.getDao().getUpdater().table(tableName);
        for (String key : primaryKeys) {
          updater.where(key, param.get(key));
        }
        for (String key : primaryKeys) {
          param.remove(key);
        }
        updater.set(param).update();
      }
    }
  }

  @SuppressWarnings("unchecked")
  public void load() {
    Bean param = JSON.toBean(JSON.fromObject(this));
    ISqlSelecter selecter = SqlDaoFactory.getDao().getSelecter().table(tableName);
    for (String key : primaryKeys) {
      selecter.where(key, param.get(key));
    }
    Bean ret = selecter.one();
    try {
      BeanInfo info = Introspector.getBeanInfo(this.getClass(), Object.class);
      PropertyDescriptor[] props = info.getPropertyDescriptors();
      for (PropertyDescriptor p : props) {
        Method setterMethod = p.getWriteMethod();
        if (null == setterMethod) {
          continue;
        }
        String key = p.getName();
        Class<?> propType = p.getPropertyType();
        Type paramType = setterMethod.getGenericParameterTypes()[0];
        Object value = convert(ret.get(key), propType, paramType);
        setterMethod.invoke(this, value);
      }
    } catch (Exception ignore) {
    }
    cache = JSON.toBean(JSON.fromObject(this));
  }

  public IModelSelecter<M> selecter() {
    return null == selecter ? new DefaultModelSelecter<M>(this) : selecter;
  }
}
