package cn.e3mall.test.solrcloud;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrCloudTest {

	@Test
	public void addSolrCloud() throws Exception{
		CloudSolrServer server=new CloudSolrServer("192.168.25.131:2181,192.168.25.131:2182,192.168.25.131:2183");
		server.setDefaultCollection("collection2");
		
		SolrInputDocument document=new SolrInputDocument();
		document.addField("id", "55655");
		document.addField("item_title", "HUAWEI");
		document.addField("item_price", 5599);
		
		server.add(document);
		
		server.commit();
	}
	
	@Test
	public void getSolrCloud() throws Exception{
		CloudSolrServer server=new CloudSolrServer("192.168.25.131:2181,192.168.25.131:2182,192.168.25.131:2183");
		server.setDefaultCollection("collection2");
		
		SolrQuery query=new SolrQuery();
		query.setQuery("HUAWEI");
		query.set("df", "item_title");
		
		QueryResponse response = server.query(query);
		SolrDocumentList results = response.getResults();
		long found = results.getNumFound();
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_title"));
			System.out.println(solrDocument.get("item_price"));
		}
	}
}
