package cn.e3mall.search.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.pojo.SearchItem;
import cn.e3mall.search.pojo.SearchResult;

@Repository
public class SearchDaoImpl implements SearchDao {

	@Autowired
	private SolrServer solrServer;
	
	@Override
	public SearchResult search(SolrQuery query) throws Exception {
		//根据查询条件查询索引库
		QueryResponse response=solrServer.query(query);
		//取查询的总页数
		SolrDocumentList results = response.getResults();
		//取高亮显示
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		long found = results.getNumFound();
		//创建一个返回对象
		SearchResult result=new SearchResult();
		result.setRecourdCount((int) found);
		//生成searchItem的集合
		List<SearchItem> list=new ArrayList<>();
		for (SolrDocument solrDocument : results) {
			//创建searchItem对象
			SearchItem item=new SearchItem();
			//向searchItem中添加内容
			String title="";
			List<String> list2 = highlighting.get(solrDocument.get("id")).get("title");
			if(list2!=null && list2.size()>0){
				title=list2.get(0);
			}else{
				title=(String) solrDocument.get("item_title");
			}
			item.setTitle(title);
			item.setId((String) solrDocument.get("id"));
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			item.setImage((String) solrDocument.get("item_image"));
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			list.add(item);
		}
		result.setItemList(list);
		return result;
	}

}
