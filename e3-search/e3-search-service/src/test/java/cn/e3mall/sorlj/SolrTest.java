package cn.e3mall.sorlj;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrTest {
	@Test
	public void test() throws SolrServerException, IOException{
		//先创建一个httpSolrServer对象
		HttpSolrServer solrServer = new HttpSolrServer("http://192.168.78.134:9090/solr");
		//再创建一个SolrinputDocument对象
		SolrInputDocument solrInputDocument = new SolrInputDocument();
		solrInputDocument.addField("id","test0001");
		solrInputDocument.addField("item_title","测试商品");
		solrInputDocument.addField("item_price","199");
		//把文档写入索引库
		solrServer.add(solrInputDocument);
		solrServer.commit();
	}
	@Test
	public void test2() throws SolrServerException, IOException{
		HttpSolrServer solrServer = new HttpSolrServer("http://192.168.78.134:9090/solr");
		solrServer.deleteById("test0001");
		solrServer.commit();
	}
	@Test
	public void test3() throws Exception{
		HttpSolrServer httpSolrServer = new HttpSolrServer("http://192.168.78.134:9090/solr");
		SolrQuery query = new SolrQuery();
		query.setQuery("*:*");//设置查询条件
		//使用默认的分页条件
		QueryResponse response = httpSolrServer.query(query);
		SolrDocumentList results = response.getResults();
		System.out.println("总记录数是"+results.getNumFound());
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_category_name"));
			System.out.println(solrDocument.get("item_image"));
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(solrDocument.get("item_title"));
		}
	}
	@Test
	public void test4() throws Exception{
		HttpSolrServer solrServer = new HttpSolrServer("http://192.168.78.134:9090/solr/collection1");
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery("手机");
		solrQuery.setStart(0);
		solrQuery.setRows(20);
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("item_title");//添加高亮显示的字段
		solrQuery.setHighlightSimplePre("<em>");
		solrQuery.setHighlightSimplePost("</em>");
		solrQuery.set("df","item_title");//设置默认搜索的域
		QueryResponse response = solrServer.query(solrQuery);
		SolrDocumentList results = response.getResults();
		System.out.println("总记录数是"+results.getNumFound());
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();//高亮显示的内容不在文档中，在response中
		for (SolrDocument solrDocument : results) {
			System.out.println(solrDocument.get("id"));
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");//取得高亮之后的结果
			String title="";
			if(list!=null&&list.size()>0){
				title=list.get(0);//如果list集合不为空就将list集合中的值赋值给title
			}
			else{
				title=(String) solrDocument.get("item_title");//如果集合中不存在，就继续在文档中取得
			}
			System.out.println(solrDocument.get("item_price"));
			System.out.println(solrDocument.get("item_category_name"));
			System.out.println(solrDocument.get("item_image"));
			System.out.println(solrDocument.get("item_sell_point"));
			System.out.println(title);
		}
		
	}
//	@Test
//	public void  test5() throws Exception{
//		CloudSolrServer solrServer = new CloudSolrServer("192.168.78.134:2182,192.168.78.134:2183,192.168.78.134:2184");
//		solrServer.setDefaultCollection("collection2");
//		SolrInputDocument document = new SolrInputDocument();
//		document.addField("item_title", "测试商品");
//		document.addField("item_price", "100");
//		document.addField("id", "test001");
//		solrServer.add(document);
//		solrServer.commit();
//		
//	}
}
