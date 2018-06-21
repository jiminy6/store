package cn.e3mall.search.message;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.e3mall.common.pojo.SearchItem;
import cn.e3mall.search.mapper.ItemMapper;
@Component("myListener")
public class ItemAddMessageListener implements MessageListener{
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;

	/* (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 * 从消息中取出商品的id
	 * 根据id，在数据库中查找出商品的信息
	 * 创建文本对象，在文本中添加域的信息
	 * 将文档写入索引库
	 */
	@Override
	public void onMessage(Message message) {
		try {
			TextMessage textmessage = (TextMessage) message;
			String text = textmessage.getText();
			long itemid = Long.parseLong(text);
			SearchItem searchItem = itemMapper.getSearchItemById(itemid);
			SolrInputDocument document = new SolrInputDocument();
			document.addField("id", searchItem.getId());
			document.addField("item_title", searchItem.getTitle());
			document.addField("item_sell_point", searchItem.getSell_point());
			document.addField("item_price", searchItem.getPrice());
			document.addField("item_image", searchItem.getImage());
			document.addField("item_category_name", searchItem.getCategory_name());
			solrServer.add(document);
			solrServer.commit();
			System.out.println("solr添加成功");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
