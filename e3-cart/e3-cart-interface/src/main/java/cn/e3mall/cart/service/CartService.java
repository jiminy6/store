package cn.e3mall.cart.service;

import java.util.List;

import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;

public interface CartService {
	/**
	 * 
	     * 说明：添加购物车
	     * @param user 
	     * @param item
	     * @param num
	     * @return
	     * @author luowenxin
	     * @time：2017年12月19日 上午9:07:53
	 */
	public TaotaoResult addCart(Long userId,Long itemId,Integer num);
	/**
	 * 
	     * 说明：合并cookie中的购物车和服务端的购物车
	     * @return
	     * @author luowenxin
	     * @time：2017年12月19日 上午11:19:11
	 */
	public TaotaoResult mergerCart(Long userId,List<TbItem>items);
	/**
	 * 
	     * 说明：从服务端取出购物车列表
	     * @param id
	     * @return
	     * @author luowenxin
	     * @time：2017年12月20日 上午11:38:01
	 */
	public List<TbItem> getCartList(Long userId);
	/**
	 * 
	     * 说明：在服务端更新购物车的数量
	     * @param userId
	     * @param itemId
	     * @param num
	     * @return
	     * @author luowenxin
	     * @time：2017年12月23日 上午10:38:23
	 */
	public TaotaoResult updateCartNum(Long userId,Long itemId,int num);
	/**
	 * 
	     * 说明：在服务端删除指定用户的指定购物车商品
	     * @param userId
	     * @param itemId
	     * @return
	     * @author luowenxin
	     * @time：2017年12月23日 上午10:39:07
	 */
	public TaotaoResult deleteCart(Long userId,Long itemId);
	/**
	 * 
	     * 说明：根据用户的id清除购物车上所有的信息
	     * @param id
	     * @return
	     * @author luowenxin
	     * @time：2017年12月23日 上午9:57:17
	 */
	public TaotaoResult clear(Long id);
	
}
