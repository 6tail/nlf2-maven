package com.nlf.extend.wechat.message.batch.bean;

import com.nlf.extend.wechat.message.batch.type.BatchMessageType;

public class BatchMessageText extends BatchMessage{
  private Text text;

  public BatchMessageText(){
    setMsgtype(BatchMessageType.text);
  }

  public Text getText() {
    return text;
  }

  public void setText(Text text) {
    this.text = text;
  }
}