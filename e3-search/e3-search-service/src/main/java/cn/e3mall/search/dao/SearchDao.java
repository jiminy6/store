package cn.e3mall.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.pojo.SearchResult;

@Repository
public class SearchDao {
	@Autowired
	private SolrServer solrServer;
	public SearchResult search(SolrQuery query) throws Exception{
		QueryResponse response = solrServer.query(query);
		SolrDocumentList results = response.getResults();
		SearchResult result = new SearchResult();
		result.setRecourdCount(results.getNumFound());
//		System.out.println("总记录数是"+results.getNumFound());
		List<SearchItem> itemList = new ArrayList<SearchItem>();//存储商品列表的信息
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		for (SolrDocument solrDocument : results) {
			SearchItem searchItem = new SearchItem();
			searchItem.setId((String) solrDocument.get("id"));
			searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
			searchItem.setImage((String) solrDocument.get("item_image"));
			searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
			searchItem.setPrice((long) solrDocument.get("item_price"));
			String title="";
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			if(list!=null&&!list.isEmpty()){
				title=list.get(0);
			}
			else{
				title=(String) solrDocument.get("item_title");
			}
			searchItem.setTitle(title);
			itemList.add(searchItem);
		}
		result.setItemList(itemList);
		return result;
	}
}
