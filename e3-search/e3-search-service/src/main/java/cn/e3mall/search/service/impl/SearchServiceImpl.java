package cn.e3mall.search.service.impl;

import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.dao.SearchDao;
import cn.e3mall.search.service.SearchService;
@Service("searchService")
public class SearchServiceImpl implements SearchService{
	@Autowired
	private SearchDao searchDao;
	@Override
	public SearchResult search(String keyword, int page, int rows) throws Exception {
		SolrQuery query = new SolrQuery();
		query.setStart((page-1)*rows);
		query.setRows(rows);
		query.setQuery(keyword);
		query.setHighlight(true);
		query.addHighlightField("item_title");//添加高亮显示的字段
		query.set("df","item_title");//设置默认搜索域
		query.setHighlightSimplePre("<em style=\"color:red\">");
		query.setHighlightSimplePost("</em>");
		SearchResult search = searchDao.search(query);
		long count = search.getRecourdCount();
		int pages=(int) (count%rows==0?(count/rows):(count/rows)+1);//计算总页数
		search.setTotalPages(pages);
		return search;
	}

}
