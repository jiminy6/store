package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.EasyUiDataGridResult;
import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;

 /**
     * 说明：商品service
     * @author luowenxin
     * @version 1.0
     * @date 2017年11月27日
     */
@Service("itemService")
@Transactional
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private JmsTemplate   jmsTemplate;
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_INFO_PRE}")
	private String REDIS_INFO_PRE;
	@Value("${ITEM_CACHE_EXPIRE}")
	private Integer ITEM_CACHE_EXPIRE;
	@Override
	public TbItem getItemById(Long id) {
		//先查缓存，如果缓存中不存在，就去数据库中查找
		try {
			String json=jedisClient.get(REDIS_INFO_PRE+":"+id+":BASE");//用:来分割，在rdm下具有分类的效果
			if(StringUtils.isNotBlank(json)){
				TbItem item = JsonUtils.jsonToPojo(json,TbItem.class);
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItem selectByPrimaryKey = itemMapper.selectByPrimaryKey(id);
		try {
			jedisClient.set(REDIS_INFO_PRE+":"+id+":BASE",JsonUtils.objectToJson(selectByPrimaryKey));
			//存在就将数据存入redis中
			jedisClient.expire(REDIS_INFO_PRE+":"+id+"BASE",ITEM_CACHE_EXPIRE);//设置过期时间
		} catch (Exception e) {
			e.printStackTrace();
		}
		return selectByPrimaryKey;	
	}
	@Override
	public EasyUiDataGridResult getItemList(int page,int rows) {
		PageHelper.startPage(page,rows);//设置分页信息
		TbItemExample example = new TbItemExample();
	    List<TbItem> list = itemMapper.selectByExample(example);//执行查询
	    EasyUiDataGridResult result = new EasyUiDataGridResult();
	    result.setRows(list);
	    //取分页结果
	    PageInfo<TbItem> pageInfo = new PageInfo<>(list);
	    long total = pageInfo.getTotal();//设置总记录数	
	    result.setTotal(total);
	    return result;
	}
	@Override
	/**
	 *  生成商品的id
	 *  设置商品的状态:1正常,2:下架,3:删除
	 *  填充tbitem的内容
	 *  填充tbitemDesc
	 *  
	 */
	public TaotaoResult addItem(TbItem tbitem, String desc) {
		final long itemId = IDUtils.genItemId();
		tbitem.setStatus((byte) 1);
		Date date = new Date();
		tbitem.setCreated(date);
		tbitem.setUpdated(date);
		tbitem.setId(itemId);
		itemMapper.insert(tbitem);//插入tbitem
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(date);
		tbItemDesc.setItemId(itemId);
		itemDescMapper.insert(tbItemDesc);
		//采用activeMq接收所产生的订单号码
		jmsTemplate.send("e3-topic", new MessageCreator() {
			//添加商品的时候将商品的id作为消息(topic序列)，交给topic的消费者处理(e3-search将根据商品的id，将这个商品信息存入索引中)
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(itemId+"");
				System.out.println("产生了一个消息:"+itemId);
				return textMessage;
			}
		});;
		return TaotaoResult.ok();
	}
	@Override
	public TbItemDesc getItemDescById(Long id) {
		try {
			String json = jedisClient.get(REDIS_INFO_PRE+":"+id+":DESC");
			if(StringUtils.isNotBlank(json)){
				TbItemDesc desc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return desc;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemDesc desc = itemDescMapper.selectByPrimaryKey(id);
		try {
			jedisClient.set(REDIS_INFO_PRE+":"+id+":DESC", JsonUtils.objectToJson(desc));
			jedisClient.expire(REDIS_INFO_PRE+":"+id+":DESC",ITEM_CACHE_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return desc;
	}
}
