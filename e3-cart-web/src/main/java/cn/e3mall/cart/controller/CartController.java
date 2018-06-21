package cn.e3mall.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.service.ItemService;


@Controller
public class CartController {
	@Value("${COOKIE_EXPIRE_TIME}")
	private int COOKIE_EXPIRE_TIME;
	@Autowired
    private ItemService itemService;
	@Autowired
	private CartService cartService;
	/**
	 * 
	     * 说明：返回添加购物车成功的逻辑视图
	     * 从cookie中取出购物车列表
	     * 判断商品是否在列表中，如果在就数量相加
	     * 如果不在就根据id查询商品信息，得到一个tbItem对象
	     * 将对象写入cookie中
	     * 返回登陆成功的逻辑视图
	     * @return
	     * @author luowenxin
	     * @time：2017年12月16日 上午11:20:18
	 */
	@RequestMapping("/cart/add/{itemId}")
	public String addCart(@PathVariable Long itemId,@RequestParam (defaultValue="1")Integer num,HttpServletRequest request,HttpServletResponse response){
		TbUser user = (TbUser) request.getAttribute("user");
		if(user!=null){//如果用户登陆了，将信息写入redis
			cartService.addCart(user.getId(), itemId, num);
			return "cartSuccess";
		}
		List<TbItem> list = getCartListFromCookie(request);
		boolean flag=false;//用来标记商品是否在列表中是否存在
		for (TbItem tbItem : list) {
			if(tbItem.getId()==itemId.longValue()){
				tbItem.setNum(tbItem.getNum()+num);
				flag=true;
				break;
			}
		}
			//如果不存在就根据id找出商品信息
			if(!flag){
				TbItem item = itemService.getItemById(itemId);
				String image = item.getImage();
				if(StringUtils.isNotBlank(image)){
					String[] strings = image.split(",");
					item.setImage(strings[0]);
				}
				item.setNum(num);
				list.add(item);
			}
		CookieUtils.setCookie(request, response,"cart",JsonUtils.objectToJson(list),COOKIE_EXPIRE_TIME,true);//将商品信息写入cookie中
		return "cartSuccess";
	}
	/**
	 * 
	     * 说明：从cookie中取出购物车列表
	     * @param request
	     * @return
	     * @author luowenxin
	     * @time：2017年12月16日 下午2:13:08
	 */
	public List<TbItem>getCartListFromCookie(HttpServletRequest request){
		String json = CookieUtils.getCookieValue(request,"cart",true);//第三个参数为是否转码
		
	    if(StringUtils.isBlank(json)){
	    	return new ArrayList<>();	
	    }
	    else{
	    	return JsonUtils.jsonToList(json,TbItem.class);
	    }
	}
	/**
	 * 
	     * 说明：返回展示购物车的逻辑视图
	     * @return
	     * @author luowenxin
	     * @time：2017年12月17日 上午1:31:57
	 */
	@RequestMapping("/cart/cart")
	public String showCart(HttpServletRequest request,HttpServletResponse response){
		//先判断用户是否登陆了
		//如果是登陆状态，从cookie中取出商品列表
		//如果列表不为空将cookie中商品列表和服务端的商品列表合并
		//把cookie中的列表清空
		TbUser user = (TbUser) request.getAttribute("user");
		System.out.println(user);
		List<TbItem> list = getCartListFromCookie(request);
		System.out.println(list);
		if(user!=null){
		cartService.mergerCart(user.getId(),list);
		//删除cookie
		CookieUtils.deleteCookie(request, response,"cart");
		//从服务端取出商品列表
		list=cartService.getCartList(user.getId());
		System.out.println(list);
		}
		request.setAttribute("cartList",list);
		return "cart";
	}
	/**
	 * 
	     * 说明：通过商品id,数量,和cookie中的id比较，更新购物车
	     * @param itemId 这个是商品id
	     * @param num 商品的数量
	     * @return
	     * @author luowenxin
	     * @time：2017年12月17日 上午10:12:56
	 */
	@RequestMapping("/cart/update/num/{itemId}/{num}")
	public TaotaoResult updateCart(@PathVariable Long itemId,@PathVariable Integer num,HttpServletRequest request,HttpServletResponse response){
		List<TbItem> list = getCartListFromCookie(request);
		for (TbItem tbItem : list) {
			if(tbItem.getId()==itemId.longValue()){
				tbItem.setNum(num);
			}
		}
		CookieUtils.setCookie(request, response,"cart",JsonUtils.objectToJson(list));//将数据写回cookie
		return TaotaoResult.ok();
	}
	/**
	 * 
	     * 说明：根据商品id删除cookie中的购物车商品数据
	     * @return
	     * @author luowenxin
	     * @time：2017年12月17日 上午10:28:42
	 */
	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCart(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response){
		List<TbItem> list = getCartListFromCookie(request);
		for (TbItem tbItem : list) {
			if(tbItem.getId()==itemId.longValue()){
				list.remove(tbItem);
				break;
			}
		}
		CookieUtils.setCookie(request, response,"cart",JsonUtils.objectToJson(list));
		//这里设置成redirect
		return "redirect:/cart/cart.html"; 
	}
}
