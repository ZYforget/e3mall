package cn.e3mall.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;
	
	@Override
	public SearchResult getSearch(String keyWords, int page, int rows) throws Exception {
		//创建solrquery对象
		SolrQuery query=new SolrQuery();
		//设置查询条件
		query.setQuery(keyWords);
		//设置分页条件
		query.setStart((page-1)*rows);
		//设置rows
		query.setRows(rows);
		//设置默认搜索域
		query.set("df", "item_title");
		//设置高亮显示
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em style='color=red'>");
		query.setHighlightSimplePost("</em>");
		//执行查询
		SearchResult result = searchDao.search(query);
		return result;
	}

}
