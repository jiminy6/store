package cn.e3mall.order.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.order.pojo.OrderInfo;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;

@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private CartService  cartService;
	/**
	 * 
	     * 说明：从服务端取出商品列表展示到页面
	     * 先从request中取得用户，再通过user获取购物车列表
	     * @return
	     * @author luowenxin
	     * @time：2017年12月20日 上午10:40:53
	 */
	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request){
		TbUser user = (TbUser) request.getAttribute("user");
		List<TbItem> cartList = cartService.getCartList(user.getId());
		request.setAttribute("cartList",cartList);
		return "order-cart";
	}
	
	@RequestMapping(value="/order/create",method=RequestMethod.POST)
	/**
	 * 
	     * 说明：提交订单
	     * 表单上的信息用orderInfo接收封装
	     * @return
	     * @author luowenxin
	     * @time：2017年12月22日 下午8:26:31
	 */
	public String create(OrderInfo orderInfo,HttpServletRequest request){
		TbUser user= (TbUser) request.getAttribute("user");
		TaotaoResult result=orderService.create(orderInfo);
		//将用户信息封装到orderInfo中
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		System.out.println(orderInfo);
		//如果成功就清除购物车的信息
		if(result.getStatus()==200){
			cartService.clear(user.getId());
		}
		//把订单号传递给页面
		request.setAttribute("orderId",result.getData());
		request.setAttribute("payment",orderInfo.getPayment());
		//返回页面的逻辑视图
		return "success";
	}
	
}
