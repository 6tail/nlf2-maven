package com.nlf.core;

import com.nlf.Bean;
import com.nlf.util.DataTypes;

import java.beans.IntrospectionException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

/**
 * 抽象Bean
 * @author 6tail
 */
public abstract class AbstractBean implements java.io.Serializable{

  private static final String TYPE_CLASS_PREFIX = "class [";

  protected Object convertString(String object,String type){
    Object value = object;
    if (DataTypes.BYTE.equals(type)) {
      value = Byte.parseByte(object);
    } else if (DataTypes.SHORT.equals(type)) {
      value = Short.parseShort(object);
    } else if (DataTypes.INT.equals(type)) {
      value = Integer.parseInt(object);
    } else if (DataTypes.LONG.equals(type)) {
      value = Long.parseLong(object);
    } else if (DataTypes.FLOAT.equals(type)) {
      value = Float.parseFloat(object);
    } else if (DataTypes.DOUBLE.equals(type)) {
      value = Double.parseDouble(object);
    }
    return value;
  }

  protected Object convertInteger(int object,String type){
    Object value = object;
    if (DataTypes.BYTE.equals(type)) {
      value = (byte) object;
    } else if (DataTypes.SHORT.equals(type)) {
      value = (short) object;
    }
    return value;
  }

  protected Object convertLong(long object,String type){
    Object value = object;
    if (DataTypes.BYTE.equals(type)) {
      value = (byte) object;
    } else if (DataTypes.SHORT.equals(type)) {
      value = (short) object;
    } else if (DataTypes.INT.equals(type)) {
      value = (int) object;
    }
    return value;
  }

  protected Object convertDouble(double object,String type){
    Object value = object;
    if (DataTypes.BYTE.equals(type)) {
      value = (byte) object;
    } else if (DataTypes.SHORT.equals(type)) {
      value = (short) object;
    } else if (DataTypes.INT.equals(type)) {
      value = (int) object;
    } else if (DataTypes.LONG.equals(type)) {
      value = (long) object;
    } else if (DataTypes.FLOAT.equals(type)) {
      value = (float) object;
    }
    return value;
  }

  protected Object convertBigDecimal(BigDecimal object, String type){
    Object value = object;
    if (DataTypes.BYTE.equals(type)) {
      value = object.byteValue();
    } else if (DataTypes.SHORT.equals(type)) {
      value = object.shortValue();
    }else if (DataTypes.INT.equals(type)) {
      value = object.intValue();
    }else if (DataTypes.LONG.equals(type)) {
      value = object.longValue();
    }else if (DataTypes.FLOAT.equals(type)) {
      value = object.floatValue();
    }else if (DataTypes.DOUBLE.equals(type)) {
      value = object.doubleValue();
    }
    return value;
  }

  @SuppressWarnings("unchecked")
  protected Object convert(Object object,Class<?> propType,Type paramType) throws IntrospectionException,IllegalAccessException,InvocationTargetException {
    Object value = object;
    String paramTypeString = paramType+"";
    if(null!=object) {
      if (value instanceof Integer) {
        value = convertInteger((Integer)value,paramTypeString);
      }else if (value instanceof Long) {
        value = convertLong((Long)value,paramTypeString);
      } else if (value instanceof BigDecimal) {
        value = convertBigDecimal((BigDecimal)value,paramTypeString);
      } else if (value instanceof Double) {
        value = convertDouble((Double)value,paramTypeString);
      } else if (value instanceof String) {
        value = convertString((String)value,paramTypeString);
      } else if (value instanceof Bean) {
        value = ((Bean) value).toObject(propType);
      } else if (value instanceof List) {
        Type elType = propType.getComponentType();
        if(null==elType){
          elType = ((ParameterizedType) paramType).getActualTypeArguments()[0];
        }
        List l = (List) value;
        int size = l.size();
        Map<Integer, Object> cache = new HashMap<Integer, Object>(size);
        for (int i = 0, j = l.size(); i < j; i++) {
          Object el = l.get(i);
          if (el instanceof Bean) {
            cache.put(i, ((Bean) el).toObject((Class) elType));
          }
        }
        for (Map.Entry<Integer, Object> entry : cache.entrySet()) {
          l.set(entry.getKey(), entry.getValue());
        }
        if (paramTypeString.startsWith(Set.class.getName())) {
          value = new HashSet(l);
        } else if (paramTypeString.startsWith(Queue.class.getName())) {
          value = new LinkedList(l);
        } else if (paramTypeString.startsWith(TYPE_CLASS_PREFIX)) {
          value = Array.newInstance((Class) elType, size);
          for (int i = 0; i < size; i++) {
            Array.set(value, i, l.get(i));
          }
        }
      }
    }
    return value;
  }
}
