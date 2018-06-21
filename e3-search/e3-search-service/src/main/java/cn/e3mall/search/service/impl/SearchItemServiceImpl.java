package cn.e3mall.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.search.mapper.ItemMapper;
import cn.e3mall.search.service.SearchItemService;
@Service("searchItemService")
public class SearchItemServiceImpl implements SearchItemService{
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;
	@Override
	/**
	 * 查询商品列表
	 * 遍历商品列表
	 * 创建文档对象
	 * 向文档对象中添加域
	 * 把文档对象写入索引库
	 * 提交
	 * 返回提交成功
	 */
	public TaotaoResult importAllItems() {
		
			try {
				List<SearchItem> itemList = itemMapper.getItemList();
				for (SearchItem searchItem : itemList) {
					SolrInputDocument solrInputDocument = new SolrInputDocument();
					solrInputDocument.addField("id",searchItem.getId());
					solrInputDocument.addField("item_title",searchItem.getTitle());
					solrInputDocument.addField("item_sell_point",searchItem.getSell_point());
					solrInputDocument.addField("item_price", searchItem.getPrice());
					solrInputDocument.addField("item_category_name",searchItem.getCategory_name());
					solrInputDocument.addField("item_image",searchItem.getImage());
				solrServer.add(solrInputDocument);
				}
				solrServer.commit();
				return TaotaoResult.ok();
			} catch (Exception e) {
				e.printStackTrace();
				return TaotaoResult.build(500,"导入数据失败");
			}
		
	}

}
