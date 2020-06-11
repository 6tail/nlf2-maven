package com.nlf.extend.rpc.socket;

/**
 * Socket RPC Exchange
 * @author 6tail
 */
public interface ISocketRpcExchange {

  /** 标志位 */
  String MAGIC = "nrpc";

  /** 默认404错误，当请求路径不存在时返回 */
  String DEFAULT_MSG_404 = "404 Not Found";

  /** 默认500错误，当出现服务器内部错误时返回 */
  String DEFAULT_MSG_500 = "500 Internal Server Error";

  /** 类型：结束 */
  short TYPE_END = 0;

  /** 类型：locale */
  short TYPE_LOCALE = 1;

  /** 类型：请求路径 */
  short TYPE_PATH = 2;

  /** 类型：请求参数名 */
  short TYPE_PARAM_NAME = 3;

  /** 类型：请求参数值 */
  short TYPE_PARAM_VALUE = 4;

  /** 类型：传输文件名 */
  short TYPE_FILE_NAME = 5;

  /** 类型：传输文件大小 */
  short TYPE_FILE_SIZE = 6;

  /** 类型：传输文件数据 */
  short TYPE_FILE_DATA = 7;

  /** 类型：json字符串 */
  short TYPE_JSON = 8;
}
