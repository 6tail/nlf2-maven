package com.nlf.extend.wechat.user.bean;

import java.util.ArrayList;
import java.util.List;

public class UserList {
  private int total;
  private int count;
  private String nextOpenid;
  private List<String> openids = new ArrayList<String>();

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  public int getCount() {
    return count;
  }

  public void setCount(int count) {
    this.count = count;
  }

  public String getNextOpenid() {
    return nextOpenid;
  }

  public void setNextOpenid(String nextOpenid) {
    this.nextOpenid = nextOpenid;
  }

  public List<String> getOpenids() {
    return openids;
  }

  public void setOpenids(List<String> openids) {
    this.openids = openids;
  }

  public void addOpenid(String openid){
    openids.add(openid);
  }
}