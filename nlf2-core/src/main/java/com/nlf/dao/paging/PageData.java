package com.nlf.dao.paging;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import com.nlf.Bean;

/**
 * 分页当前页数据封装
 * 
 * @author 6tail
 * 
 */
public class PageData implements List<Bean>,java.io.Serializable{
  private static final long serialVersionUID = 1;
  /** 唯一标识 */
  private String id;
  /** 每页记录数 */
  private int pageSize;
  /** 总记录数 */
  private int recordCount;
  /** 当前第几页 */
  private int pageNumber;
  /** 该页数据 */
  private List<Bean> data = new java.util.ArrayList<Bean>();

  public PageData(){}

  public PageData(List<Bean> data,int pageSize,int pageNumber,int recordCount){
    setData(data);
    setPageSize(pageSize);
    setPageNumber(pageNumber);
    setRecordCount(recordCount);
  }

  public String getId(){
    return id;
  }

  public void setId(String id){
    this.id = id;
  }

  /**
   * 获取每页记录数
   * 
   * @return 每页记录数
   */
  public int getPageSize(){
    return pageSize;
  }

  /**
   * 设置每页记录数
   * 
   * @param pageSize 每页记录数
   */
  public void setPageSize(int pageSize){
    this.pageSize = pageSize<1?1:pageSize;
  }

  /**
   * 获取总记录数
   * 
   * @return 总记录数
   */
  public int getRecordCount(){
    return recordCount;
  }

  /**
   * 设置总记录数
   * 
   * @param recordCount 总记录数
   */
  public void setRecordCount(int recordCount){
    this.recordCount = recordCount<0?0:recordCount;
  }

  /**
   * 获取总页数
   * 
   * @return 总页数
   */
  public int getPageCount(){
    return (recordCount<1||pageSize<1)?1:(int)Math.ceil(recordCount*1D/pageSize);
  }

  /**
   * 获取前一页页码
   * 
   * @return 前一页页码
   */
  public int getPreviousPageNumber(){
    return pageNumber-1<1?1:pageNumber-1;
  }

  /**
   * 获取后一页页码
   * 
   * @return 页码
   */
  public int getNextPageNumber(){
    return pageNumber+1>getPageCount()?getPageCount():pageNumber+1;
  }

  /**
   * 获取第一页页码
   * 
   * @return 页码
   */
  public int getFirstPageNumber(){
    return 1;
  }

  /**
   * 获取最后页页码
   * 
   * @return 页码
   */
  public int getLastPageNumber(){
    return getPageCount();
  }

  /**
   * 获取前后相邻的页码
   * 
   * @param count 页码个数
   * @return 相邻的页码数组
   */
  public int[] getNearPageNumbers(int count){
    int pageCount = getPageCount();
    int start = pageNumber-count;
    int end = pageNumber+count;
    end = end<1?1:end;
    end = end>pageCount?pageNumber:end;
    start = start<1?1:start;
    int[] m = new int[end+1-start];
    for(int i = 0;i<m.length;i++){
      m[i] = start+i;
    }
    return m;
  }

  /**
   * 获取该页数据条数
   * 
   * @return 该页数据条数
   */
  public int getSize(){
    return null==data?0:data.size();
  }

  /**
   * 获取该页页码
   * 
   * @return 页码
   */
  public int getPageNumber(){
    return pageNumber;
  }

  /**
   * 设置该页页码
   * 
   * @param pageNumber 页码
   */
  public void setPageNumber(int pageNumber){
    this.pageNumber = pageNumber<1?1:pageNumber;
  }

  /**
   * 获取该页数据
   * 
   * @return 该页数据
   */
  public List<Bean> getData(){
    return data;
  }

  /**
   * 设置该页数据
   * 
   * @param data 该页数据
   */
  public void setData(List<Bean> data){
    this.data = data;
  }

  /**
   * 获取指定Bean
   * 
   * @param index 索引
   * @return Bean
   */
  public Bean get(int index){
    return data.get(index);
  }

  public boolean add(Bean o){
    return data.add(o);
  }

  public void add(int index,Bean element){
    data.add(index,element);
  }

  public boolean addAll(Collection<? extends Bean> c){
    return data.addAll(c);
  }

  public boolean addAll(int index,Collection<? extends Bean> c){
    return data.addAll(index,c);
  }

  public void clear(){
    data.clear();
  }

  public boolean contains(Object o){
    return data.contains(o);
  }

  public boolean containsAll(Collection<?> c){
    return data.containsAll(c);
  }

  public int indexOf(Object o){
    return data.indexOf(o);
  }

  public boolean isEmpty(){
    return data.isEmpty();
  }

  public java.util.Iterator<Bean> iterator(){
    return data.iterator();
  }

  public int lastIndexOf(Object o){
    return data.lastIndexOf(o);
  }

  public ListIterator<Bean> listIterator(){
    return data.listIterator();
  }

  public ListIterator<Bean> listIterator(int index){
    return data.listIterator(index);
  }

  public boolean remove(Object o){
    return data.remove(o);
  }

  public Bean remove(int index){
    return data.remove(index);
  }

  public boolean removeAll(Collection<?> c){
    return data.removeAll(c);
  }

  public boolean retainAll(Collection<?> c){
    return data.retainAll(c);
  }

  public Bean set(int index,Bean element){
    return data.set(index,element);
  }

  public int size(){
    return data.size();
  }

  public List<Bean> subList(int fromIndex,int toIndex){
    return data.subList(fromIndex,toIndex);
  }

  public Object[] toArray(){
    return data.toArray();
  }

  public <T>T[] toArray(T[] a){

    return data.toArray(a);
  }

  public String toString(){
    IPagingRender render = com.nlf.App.getProxy().newInstance(IPagingRender.class.getName());
    return render.render(this);
  }
}