package cn.e3mall.test.solr;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {

	@Test
	public void testSolrSelect()throws Exception{
		//创建一个solrServer对象
		SolrServer server=new HttpSolrServer("http://192.168.25.131:8080/solr/collection1");
		//创建一个solrQuery对象
		SolrQuery query=new SolrQuery();
		//添加查询条件
		query.setQuery("方法");
		query.set("df", "item_title");
		//开启高亮显示
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<em>");
		query.setHighlightSimplePost("</em>");
		//执行查询
		QueryResponse response = server.query(query);
		//获取查询结果
		SolrDocumentList results = response.getResults();
		//取高亮显示
		Map<String, Map<String, List<String>>> map = response.getHighlighting();
		long found = results.getNumFound();
		System.out.println(found);
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			List<String> list = map.get(solrDocument.get("id")).get("item_title");
			String title="";
			if(list!=null& list.size()>0){
				title=list.get(0);
			}else{
				title=(String) solrDocument.get("item_title");
			}
			System.out.println(title);
		}
	}
	
	@Test
	public void testSolrAdd() throws Exception{
		//创建一个solrserver对象
		SolrServer server=new HttpSolrServer("http://192.168.25.131:8080/solr/collection1");
		//创建一个solrinputdocunent对象
		SolrInputDocument document=new SolrInputDocument();
		//向对象中添加域
		document.addField("id", "15486521");
		document.addField("item_title", "调用SolrServer对象的根据id删除的方法");
		document.addField("item_keywords", "target");
		//使用solrserver把document添加进域
		server.add(document);
		//提交
		server.commit();
	}
}
