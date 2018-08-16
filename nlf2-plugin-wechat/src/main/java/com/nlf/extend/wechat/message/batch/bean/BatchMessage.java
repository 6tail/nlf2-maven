package com.nlf.extend.wechat.message.batch.bean;

import com.nlf.extend.wechat.message.batch.type.BatchMessageType;

/**
 * 群发消息
 */
public class BatchMessage {

  private BatchMessageFilter filter = new BatchMessageFilter();
  private BatchMessageType msgtype;

  public BatchMessageFilter getFilter() {
    return filter;
  }

  public void setFilter(BatchMessageFilter filter) {
    this.filter = filter;
  }

  public BatchMessageType getMsgtype() {
    return msgtype;
  }

  public void setMsgtype(BatchMessageType msgtype) {
    this.msgtype = msgtype;
  }
}