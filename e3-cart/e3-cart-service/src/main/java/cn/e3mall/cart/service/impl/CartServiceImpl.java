package cn.e3mall.cart.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
@Service("cartService")
public class CartServiceImpl implements CartService {
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TbItemMapper itemMapper;
	@Value("${CART_PRE}")
	private String CART_PRE;
	@Override
	public TaotaoResult addCart(Long userId, Long itemId, Integer num) {
//		Boolean exists = jedisClient.exists(CART_PRE+""+userId);
		//判断redis中商品是否存在
		Boolean hexists = jedisClient.hexists(CART_PRE+":"+userId,itemId+"");
		if(hexists){
			String json = jedisClient.hget(CART_PRE+":"+userId,itemId+"");
	       TbItem tbItem = JsonUtils.jsonToPojo(json,TbItem.class);
	       tbItem.setNum(tbItem.getNum()+num);
	       //将数据重新写回redis中
	       jedisClient.hset(CART_PRE+":"+userId,itemId+"",JsonUtils.objectToJson(tbItem));
	       return TaotaoResult.ok();
		}
		//如果不存在，就根据商品id取商品的信息，并添加到redis中
		else{
			TbItem item = itemMapper.selectByPrimaryKey(itemId);
			item.setNum(num);
			if(StringUtils.isNotBlank(item.getImage())){
				item.setImage(item.getImage().split(",")[0]);
			}
			jedisClient.hset(CART_PRE+":"+userId,itemId+"",JsonUtils.objectToJson(item));
			return TaotaoResult.ok();
		}
	}
	@Override
	public TaotaoResult mergerCart(Long userId, List<TbItem> items) {
		for (TbItem tbItem : items) {
			addCart(userId,tbItem.getId(),tbItem.getNum());
		}
		return TaotaoResult.ok();
	}
	@Override
	//将string类型的List转成tbitem类型
	public List<TbItem> getCartList(Long userId) {
		List<String> hvals = jedisClient.hvals(CART_PRE+":"+userId);
		List<TbItem> itemList = new ArrayList<>();
		for (String ha : hvals) {
			TbItem item=JsonUtils.jsonToPojo(ha,TbItem.class);
			itemList.add(item);
		}
		return itemList;
	}
	@Override
	public TaotaoResult clear(Long id) {
		jedisClient.del(CART_PRE+":"+id);
		return TaotaoResult.ok();
	}
	@Override
	public TaotaoResult updateCartNum(Long userId, Long itemId, int num) {
		String json = jedisClient.hget(CART_PRE+":"+userId,itemId+"");
		TbItem item = JsonUtils.jsonToPojo(json,TbItem.class);
		item.setNum(num);
		jedisClient.hset(CART_PRE+":"+userId,itemId+"",JsonUtils.objectToJson(item));
		return TaotaoResult.ok();
	}
	@Override
	public TaotaoResult deleteCart(Long userId, Long itemId) {
		jedisClient.hdel(CART_PRE+":"+userId,itemId+"");
		return TaotaoResult.ok();
	}
}
