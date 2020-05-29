package com.nlf.extend.web.impl;

import com.nlf.App;
import com.nlf.Bean;
import com.nlf.core.IRequest;
import com.nlf.core.Statics;
import com.nlf.dao.paging.IPageable;
import com.nlf.dao.paging.IPagingRender;
import com.nlf.extend.serialize.obj.OBJ;
import com.nlf.extend.web.IWebRequest;
import com.nlf.extend.web.WebApp;
import com.nlf.extend.web.WebStatics;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Set;
import java.util.TreeSet;

/**
 * 默认的WEB自动分页渲染实现
 *
 * @author 6tail
 *
 */
public class DefaultWebPagingRender implements IPagingRender{
  public static final String URL_PATH_SEPERATOR = "/";
  /** 每页大小 */
  protected int[] pageSizes = {1,20,50,100,200};
  /** GET最大长度，超过该长度将转为POST跳转 */
  protected int maxGetLength = 2048;
  /** 默认链接地址 */
  protected String linkNone;
  /** 总记录数前缀 */
  protected String labelRecordCountPrefix;
  /** 总记录数后缀 */
  protected String labelRecordCountSuffix;
  /** 每页大小前缀 */
  protected String labelPageSizePrefix;
  /** 每页大小后缀 */
  protected String labelPageSizeSuffix;
  /** 当前页码前缀 */
  protected String labelPageNumberPrefix;
  /** 当前页码后缀 */
  protected String labelPageNumberSuffix;
  /** 上一页 */
  protected String labelPrev;
  /** 下一页 */
  protected String labelNext;
  /** JS钩子变量，通过该变量可以调用分页提供的相关js方法 */
  protected String hook;
  /** JS代码 */
  protected String javascript = "<script>;(function(W,D){var QUOTE='\"',_trim=function(s){return s.replace(/(^\\s*)|(\\s*$)/g,'')},SCRIPT='script',STYLE='style',STYLE_END=['</',STYLE,'>'].join(''),SCRIPT_END=['</',SCRIPT,'>'].join(''),SCRIPT_START=['<',SCRIPT].join(''),TYPE_START=['type','=',QUOTE].join(''),JS_TYPE=['text/','java',SCRIPT].join(''),CSS_TYPE=['text/','css'].join(''),STYLE_SHEET=[STYLE,'sheet'].join(''),H=D.head||D.getElementsByTagName('head')[0],Q=[function(){return new XMLHttpRequest()},function(){return new ActiveXObject('Msxml2.XMLHTTP')},function(){return new ActiveXObject('Microsoft.XMLHTTP')}],qi=-1;var xhr=function(param,callback){var r=(function(){if(qi>-1)return Q[qi]();for(var i=0,j=Q.length;i<j;i++){try{var q=Q[i]();qi=i;return q}catch(e){}}throw'xhr not support'})();r.open('POST','${uri}',true);r.onreadystatechange=function(){if(4==r.readyState){var t=r.status;if(200==t||0==t||t>=400){callback.call({'xhr':r,'status':t})}}};r.setRequestHeader('Content-Type','application/x-www-form-urlencoded');r.setRequestHeader('x-requested-with','XMLHttpRequest');r.send(param)};var _loadJs=function(url,callback){var o=D.createElement(SCRIPT);o.type=JS_TYPE;o.src=url;o.onload=function(){try{H.removeChild(this)}catch(e){return}if(callback)callback.call(callback,url)};o.onreadystatechange=function(){if(/loaded|complete/.test(this.readyState)){try{H.removeChild(this)}catch(e){return}if(callback)callback.call(callback,url)}};H.appendChild(o)};var _loadJsByQueue=function(queue,callback){if(queue.length<1){callback.call(callback)}else{_loadJs(queue.shift(),function(){_loadJsByQueue(queue,callback)})}};var _loadCss=function(url){var o=D.createElement('link');o.type=CSS_TYPE;o.rel=STYLE_SHEET;o.href=url;H.appendChild(o)};var _createCss=function(css){var s=_trim(css);if(s.length<1)return;var o=D.createElement(STYLE);o.type=CSS_TYPE;o.innerHTML=s;H.appendChild(o)};var _parseStyle=function(o,html){var s=html;var l=s.split(STYLE_END);for(var i=0,j=l.length;i<j;i++){s=l[i];var k=s.indexOf(['<','style'].join(''));if(k>-1){s=s.substr(k);s=s.substr(s.indexOf('>')+1);o.innerStyle.push(s)}}s=html;l=s.split(['<','link'].join(''));for(var i=0,j=l.length;i<j;i++){s=l[i];var k=s.indexOf('>');if(k>-1){s=s.substr(0,k);k=s.indexOf(STYLE_SHEET);if(k>-1){k=s.indexOf(['href','=',QUOTE].join(''));if(k>-1){s=s.substr(k+6);o.outerStyle.push(s.substr(0,s.indexOf(QUOTE)))}}}}};var _parseScript=function(o,html){var s=html;var l=s.split(SCRIPT_END);for(var i=0,j=l.length;i<j;i++){s=l[i];var k=s.indexOf(SCRIPT_START);if(k>-1){s=s.substr(k);k=s.indexOf('>');var left=s.substr(0,k);var right=s.substr(k+1);k=left.indexOf(['src','=',QUOTE].join(''));if(k>-1){s=left.substr(k+5);o.outerScript.push(s.substr(0,s.indexOf(QUOTE)))}else{k=left.indexOf(TYPE_START);if(k<0||left.indexOf(JS_TYPE)>-1){o.innerScript.push(right)}}}}};var _parseHtml=function(o,html){var rs=[];var s=html;var i=s.indexOf(SCRIPT_END);while(i>-1){var j=s.indexOf(SCRIPT_START);rs.push(s.substr(0,j));s=s.substr(j);i=s.indexOf(SCRIPT_END);var k=s.indexOf('>');var left=s.substr(0,k);if(left.indexOf(TYPE_START)>-1&&left.indexOf(JS_TYPE)<0){rs.push(s.substr(0,i));rs.push(SCRIPT_END)}s=s.substr(i+9);i=s.indexOf(SCRIPT_END)}rs.push(s);o.html=rs.join('')};var _parseTag=function(html,tag){var s=html;var l=s.split('</'+tag+'>');for(var i=0,j=l.length;i<j;i++){s=l[i];var k=s.indexOf('<'+tag+'>');if(k>-1){return s.substr(k+2+tag.length)}}return'body'==tag?html:''};var _parse=function(html){var o={outerStyle:[],innerStyle:[],outerScript:[],innerScript:[],html:[]};var head=_parseTag(html,'head');if(head.length>0){_parseStyle(o,head);_parseScript(o,head)}var body=_parseTag(html,'body');if(body.length>0){_parseHtml(o,body);_parseStyle(o,body);_parseScript(o,body)}return o};var _render=function(o,dom,callback){for(var i=0,j=o.outerStyle.length;i<j;i++)_loadCss(o.outerStyle[i]);_createCss(o.innerStyle.join(''));dom.innerHTML='<b> </b>'+o.html;dom.removeChild(dom.firstChild);_loadJsByQueue(o.outerScript,function(){var script=D.createElement(SCRIPT);script.text=o.innerScript.join('');H.appendChild(script);H.removeChild(script);if(callback)callback.call(callback)})};var form=D.getElementById('${id}');var as=form.getElementsByTagName('a');var sel=form.getElementsByTagName('select');for(var i=0,j=as.length;i<j;i++){var a=as[i];a.href='javascript:void(0);';a.onclick=function(){form.pageNumber.value=this.getAttribute('data-page');_load()}}for(var i=0,j=sel.length;i<j;i++){var a=sel[i];a.onchange=function(){form.pageSize.value=this.value;_load();return false}}var _doc=function(html){var frm=D.createElement('iframe');D.body.appendChild(frm);var doc=frm.contentWindow.document||frm.contentDocument;doc.open();doc.write(html);doc.close();return{w:frm,d:doc}};var _findDom=function(html,id){var doms=[];var p=form;while(p!=D.body){doms.push(p);p=p.parentNode}doms.push(p);var vd=_doc(html);var n=0;p=vd.d.getElementById(id);if(p){while('body'!=p.tagName.toLowerCase()){n++;p=p.parentNode}}else{n++}vd.w.parentNode.removeChild(vd.w);delete vd.w;delete vd.d;return doms[n]};var _load=function(){var els=form.elements;var ps=[];for(var i=0,j=els.length;i<j;i++){var el=els[i];ps.push(el.name+'='+encodeURIComponent(encodeURIComponent(el.value)))}xhr(ps.join('&'),function(){var html=this.xhr.responseText;var d=_parse(html);var container=_findDom(d.html,'${id}');_render(d,container)})};form.onsubmit=function(){_load();return false};if(!W['${hook}']){W['${hook}']={}}W['${hook}']['${id}']={fresh:function(){_load()},goToPage:function(n){form.pageNumber.value=n;_load()},prevPage:function(){this.goToPage(${prev})},nextPage:function(){this.goToPage(${next})},firstPage:function(){this.goToPage(${first})},lastPage:function(){this.goToPage(${last})}};W['${hook}'].fresh=function(){_load()};W['${hook}'].goToPage=function(n){form.pageNumber.value=n;_load()};W['${hook}'].prevPage=function(){this.goToPage(${prev})};W['${hook}'].nextPage=function(){this.goToPage(${next})};W['${hook}'].firstPage=function(){this.goToPage(${first})};W['${hook}'].lastPage=function(){this.goToPage(${last})}})(window,document);</script>";

  public DefaultWebPagingRender(){
    linkNone = App.getProperty("nlf.web.paging.link_none");
    labelRecordCountPrefix = App.getProperty("nlf.web.paging.label.record_count_prefix");
    labelRecordCountSuffix = App.getProperty("nlf.web.paging.label.record_count_suffix");
    labelPageSizePrefix = App.getProperty("nlf.web.paging.label.page_size_prefix");
    labelPageSizeSuffix = App.getProperty("nlf.web.paging.label.page_size_suffix");
    labelPageNumberPrefix = App.getProperty("nlf.web.paging.label.page_number_prefix");
    labelPageNumberSuffix = App.getProperty("nlf.web.paging.label.page_number_suffix");
    labelPrev = App.getProperty("nlf.web.paging.label.prev");
    labelNext = App.getProperty("nlf.web.paging.label.next");
    hook = App.getProperty("nlf.web.paging.hook");
  }

  protected String renderLink(Bean pageParam,int pageNumber,String uri){
    StringBuilder s = new StringBuilder();
    for(String key:pageParam.keySet()){
      s.append("&");
      s.append(key);
      s.append("=");
      String value = pageParam.getString(key);
      if(Statics.PARAM_PAGE_NUMBER.equals(key)){
        value = pageNumber+"";
      }
      try{
        s.append(URLEncoder.encode(value,Statics.ENCODE));
      }catch(UnsupportedEncodingException e){
        return linkNone;
      }
    }
    if(s.length()>0){
      s.replace(0,1,"?");
    }
    String rs = s.insert(0,uri).toString();
    if(rs.length()>maxGetLength){
      return linkNone;
    }
    return rs;
  }

  /**
   * 渲染javascript脚本
   * @param pd 分页数据
   * @param pageParam 参数
   * @param uri 请求地址
   * @return 渲染结果
   */
  protected String renderScript(IPageable pd,Bean pageParam,String uri){
    return javascript.replace("${uri}",uri)
        .replace("${id}",pd.getId())
        .replace("${hook}",hook)
        .replace("${first}",pd.getFirstPageNumber()+"")
        .replace("${last}",pd.getLastPageNumber()+"")
        .replace("${prev}",pd.getPreviousPageNumber()+"")
        .replace("${next}",pd.getNextPageNumber()+"");
  }

  protected String buildField(String key,Object value){
    return "<textarea name=\""+key+"\">"+value+"</textarea>";
  }

  /**
   * 渲染每页记录数选择
   * @param pd 分页数据
   * @param pageParam 参数
   * @param uri 请求地址
   * @return 渲染结果
   */
  protected String renderPageSize(IPageable pd,Bean pageParam,String uri){
    int pageSize = pd.getPageSize();
    Set<Integer> pages = new TreeSet<Integer>();
    pages.add(pageSize);
    for(int size:pageSizes){
      pages.add(size);
    }
    StringBuilder s = new StringBuilder();
    s.append("<select name=\"");
    s.append(Statics.PARAM_PAGE_SIZE);
    s.append("\">");
    for(int size:pages){
      s.append("<option value=\"");
      s.append(size);
      s.append("\"");
      if(size==pageSize){
        s.append(" selected");
      }
      s.append(">");
      s.append(labelPageSizePrefix);
      s.append(size);
      s.append(labelPageSizeSuffix);
      s.append("</option>");
    }
    s.append("</select>");
    return s.toString();
  }

  /**
   * 渲染当前页码
   * @param pd 分页数据
   * @param pageParam 参数
   * @param uri 请求地址
   * @return 渲染结果
   */
  protected String renderPageNumber(IPageable pd,Bean pageParam,String uri){
    return String.format("<i>%s</i><input name=\"%s\" type=\"text\" value=\"%d\"></input><i>%s</i>",labelPageNumberPrefix,Statics.PARAM_PAGE_NUMBER,pd.getPageNumber(),labelPageNumberSuffix);
  }

  /**
   * 渲染总记录数
   * @param pd 分页数据
   * @param pageParam 参数
   * @param uri 请求地址
   * @return 渲染结果
   */
  protected String renderRecordCount(IPageable pd,Bean pageParam,String uri){
    return String.format("<i>%s</i><i>%d</i><i>%s</i>",labelRecordCountPrefix,pd.getRecordCount(),labelRecordCountSuffix);
  }

  /**
   * 渲染上一页跳转链接
   * @param pd 分页数据
   * @param pageParam 参数
   * @param uri 请求地址
   * @return 渲染结果
   */
  protected String renderPageNumberPrev(IPageable pd,Bean pageParam,String uri){
    int prev = pd.getPreviousPageNumber();
    StringBuilder s = new StringBuilder();
    s.append("<li");
    if(pd.getFirstPageNumber()==pd.getPageNumber()){
      s.append(" class=\"disabled\"");
    }
    s.append("><a data-page=\"");
    s.append(prev);
    s.append("\" href=\"");
    s.append(renderLink(pageParam,prev,uri));
    s.append("\">");
    s.append(labelPrev);
    s.append("</a></li>");
    return s.toString();
  }

  /**
   * 渲染下一页跳转链接
   * @param pd 分页数据
   * @param pageParam 参数
   * @param uri 请求地址
   * @return 渲染结果
   */
  protected String renderPageNumberNext(IPageable pd,Bean pageParam,String uri){
    int next = pd.getNextPageNumber();
    StringBuilder s = new StringBuilder();
    s.append("<li");
    if(pd.getLastPageNumber()==pd.getPageNumber()){
      s.append(" class=\"disabled\"");
    }
    s.append("><a data-page=\"");
    s.append(next);
    s.append("\" href=\"");
    s.append(renderLink(pageParam,next,uri));
    s.append("\">");
    s.append(labelNext);
    s.append("</a></li>");
    return s.toString();
  }

  /**
   * 渲染分页页码
   * @param pd 分页数据
   * @param pageParam 参数
   * @param uri 请求地址
   * @return 渲染结果
   */
  protected String renderPageNumbers(IPageable pd,Bean pageParam,String uri){
    StringBuilder s = new StringBuilder();
    int first = pd.getFirstPageNumber();
    int last = pd.getLastPageNumber();
    int active = pd.getPageNumber();
    int[] pn = pd.getNearPageNumbers();
    int pnFirst = pn[0];
    int pnLast = pn[pn.length-1];
    s.append("<ul>");
    s.append(renderPageNumberPrev(pd,pageParam,uri));
    if(pnFirst>first){
      s.append("<li");
      if(first==active){
        s.append(" class=\"active\"");
      }
      s.append("><a data-page=\"");
      s.append(first);
      s.append("\" href=\"");
      s.append(renderLink(pageParam,first,uri));
      s.append("\">");
      s.append(first);
      s.append("</a></li>");
      if(pnFirst>first+1){
        s.append("<li><a data-page=\"");
        s.append(pnFirst-1);
        s.append("\" title=\"");
        s.append(pnFirst-1);
        s.append("\" href=\"");
        s.append(renderLink(pageParam,pnFirst-1,uri));
        s.append("\">...</a></li>");
      }
    }
    for(int i:pn){
      s.append("<li");
      if(i==active){
        s.append(" class=\"active\"");
      }
      s.append("><a data-page=\"");
      s.append(i);
      s.append("\" href=\"");
      s.append(renderLink(pageParam,i,uri));
      s.append("\">");
      s.append(i);
      s.append("</a></li>");
    }
    if(pnLast<last){
      if(pnLast<last-1){
        s.append("<li><a data-page=\"");
        s.append(pnLast+1);
        s.append("\" title=\"");
        s.append(pnLast+1);
        s.append("\" href=\"");
        s.append(renderLink(pageParam,pnLast+1,uri));
        s.append("\">...</a></li>");
      }
      s.append("<li");
      if(last==active){
        s.append(" class=\"active\"");
      }
      s.append("><a data-page=\"");
      s.append(last);
      s.append("\" href=\"");
      s.append(renderLink(pageParam,last,uri));
      s.append("\">");
      s.append(last);
      s.append("</a></li>");
    }
    s.append(renderPageNumberNext(pd,pageParam,uri));
    s.append("</ul>");
    return s.toString();
  }

  /**
   * 渲染所有组件
   * @param pd 分页数据
   * @param pageParam 参数
   * @param uri 请求地址
   * @return 渲染结果
   */
  protected String renderComponents(IPageable pd,Bean pageParam,String uri){
    return renderRecordCount(pd,pageParam,uri)+renderPageSize(pd,pageParam,uri)+renderPageNumbers(pd,pageParam,uri)+renderPageNumber(pd,pageParam,uri);
  }

  /**
   * 渲染
   * @param pd 分页数据
   * @return 渲染结果
   */
  public String render(IPageable pd){
    IRequest r = App.getRequest();
    String pg = (String)((IWebRequest)r).getServletRequest().getAttribute(Statics.PARAM_PAGE_PARAM);
    Bean pageParam = OBJ.toBean(pg);
    String paramUri = pageParam.get(WebStatics.PARAM_PAGE_URI);
    if(!paramUri.startsWith(URL_PATH_SEPERATOR)){
      paramUri = URL_PATH_SEPERATOR + paramUri;
    }
    String uri = WebApp.contextPath+paramUri;
    StringBuilder s = new StringBuilder();
    s.append("<form id=\"");
    s.append(pd.getId());
    s.append("\" class=\"nlf-paging\" action=\"");
    s.append(uri);
    s.append("\" target=\"_self\" method=\"post\">");
    Bean param = r.getParam();
    for(String key:param.keySet()){
      if(Statics.PARAM_PAGE_NUMBER.equals(key) || Statics.PARAM_PAGE_SIZE.equals(key)){
        continue;
      }
      Object value = param.get(key);
      if(null==value){
        continue;
      }
      if(value instanceof String[]){
        String[] values = (String[])value;
        for(String v:values){
          s.append(buildField(key,v));
        }
      }else{
        s.append(buildField(key,value));
      }
    }
    s.append(renderComponents(pd,pageParam,uri));
    s.append("</form>");
    s.append(renderScript(pd,pageParam,uri));
    return s.toString();
  }

  public boolean support() {
    IRequest r = App.getRequest();
    return null != r && r instanceof IWebRequest;
  }
}
