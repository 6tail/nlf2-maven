package com.nlf.util;

/**
 * 字符串
 *
 * @author 6tail
 */
public class Strings {
  public static final String EQ = "=";
  public static final String LT = "<";
  public static final String GT = ">";
  /** 回车 */
  public static final String CR = "\r";
  /** 换行 */
  public static final String LF = "\n";
  /** 翻页 */
  public static final String FF = "\f";
  /** 点 */
  public static final String DOT = ".";
  /** TAB */
  public static final String TAB = "\t";
  public static final String EMPTY = "";
  /** 冒号 */
  public static final String COLON = ":";
  /** 逗号 */
  public static final String COMMA = ",";
  public static final String SPACE = " ";
  public static final String MINUS = "-";
  public static final String UNDERSCORE = "_";
  public static final String SLASH_LEFT = "/";
  /** 退格 */
  public static final String BACKSPACE = "\b";
  /** 左大括号 */
  public static final String BRACE_OPEN = "{";
  /** 右大括号 */
  public static final String BRACE_CLOSE = "}";
  public static final String EXCLAMATION = "!";
  public static final String SLASH_RIGHT = "\\";
  public static final String QUOTE_SINGLE = "'";
  public static final String BRACKET_OPEN = "[";
  public static final String BRACKET_CLOSE = "]";
  public static final String QUOTE_DOUBLE = "\"";
  public static final String LT_ENTITY_NAME = "&lt;";
  public static final String GT_ENTITY_NAME = "&gt;";

  public static final String XML = "xml";
  public static final String JSON = "json";

  public static String concat(String... strs) {
    StringBuilder sb = new StringBuilder();
    for (String s : strs) {
      sb.append(s);
    }
    return sb.toString();
  }

  public static String repeat(String s,int times) {
    StringBuilder sb = new StringBuilder();
    for (int i=0;i<times;i++) {
      sb.append(s);
    }
    return sb.toString();
  }
}
