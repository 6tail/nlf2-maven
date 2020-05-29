package com.nlf.dao.paging;

import com.nlf.App;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * 抽象分页数据
 * @author 6tail
 */
public abstract class AbstractPageable<M> implements IPageable<M>{
  /** 唯一标识 */
  protected String id;
  /** 每页记录数 */
  protected int pageSize;
  /** 总记录数 */
  protected int recordCount;
  /** 当前第几页 */
  protected int pageNumber;
  /** 该页数据 */
  protected List<M> data = new java.util.ArrayList<M>();

  public String getId(){
    return id;
  }

  public void setId(String id){
    this.id = id;
  }

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

  public int getPageCount(){
    return (recordCount<1||pageSize<1)?1:(int)Math.ceil(recordCount*1D/pageSize);
  }

  public int getPreviousPageNumber(){
    int prevNumber = pageNumber-1;
    return prevNumber<1?1:prevNumber;
  }

  public int getNextPageNumber(){
    int nextNumber = pageNumber+1;
    int pageCount = getPageCount();
    return nextNumber>pageCount?pageCount:nextNumber;
  }

  public boolean isHasNextPage(){
    return getPageNumber()<getLastPageNumber();
  }

  public int getFirstPageNumber(){
    return 1;
  }

  public int getLastPageNumber(){
    return getPageCount();
  }

  public int getSize(){
    return null==data?0:data.size();
  }

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

  public AbstractPageable(){
    setPageNumber(1);
    setPageSize(1);
  }

  public AbstractPageable(List<M> data,int pageSize,int pageNumber,int recordCount){
    setData(data);
    setPageSize(pageSize);
    setPageNumber(pageNumber);
    setRecordCount(recordCount);
  }

  /**
   * 生成前后相邻的页码
   *
   * @param count 页码个数
   * @return 相邻的页码数组
   */
  protected int[] genNearPageNumbers(int count){
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
   * 获取前后相邻的页码
   *
   * @return 相邻的页码数组
   */
  public int[] getNearPageNumbers(){
    return genNearPageNumbers(App.getPropertyInt("nlf.paging.near",2));
  }

  public List<M> getData(){
    return data;
  }

  /**
   * 设置该页数据
   *
   * @param data 该页数据
   */
  public void setData(List<M> data){
    this.data = data;
  }

  /**
   * 获取指定Bean
   *
   * @param index 索引
   * @return Bean
   */
  public M get(int index){
    return data.get(index);
  }

  public boolean add(M o){
    return data.add(o);
  }

  public void add(int index,M element){
    data.add(index,element);
  }

  public boolean addAll(Collection<? extends M> c){
    return data.addAll(c);
  }

  public boolean addAll(int index,Collection<? extends M> c){
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

  public java.util.Iterator<M> iterator(){
    return data.iterator();
  }

  public int lastIndexOf(Object o){
    return data.lastIndexOf(o);
  }

  public ListIterator<M> listIterator(){
    return data.listIterator();
  }

  public ListIterator<M> listIterator(int index){
    return data.listIterator(index);
  }

  public boolean remove(Object o){
    return data.remove(o);
  }

  public M remove(int index){
    return data.remove(index);
  }

  public boolean removeAll(Collection<?> c){
    return data.removeAll(c);
  }

  public boolean retainAll(Collection<?> c){
    return data.retainAll(c);
  }

  public M set(int index,M element){
    return data.set(index,element);
  }

  public int size(){
    return data.size();
  }

  public List<M> subList(int fromIndex,int toIndex){
    return data.subList(fromIndex,toIndex);
  }

  public Object[] toArray(){
    return data.toArray();
  }

  public <T>T[] toArray(T[] a){
    return data.toArray(a);
  }

  @Override
  public String toString(){
    List<String> impls = App.getImplements(IPagingRender.class);
    for(String impl:impls){
      IPagingRender render = App.getProxy().newInstance(impl);
      if(render.support()){
        return render.render(this);
      }
    }
    return "";
  }

}
