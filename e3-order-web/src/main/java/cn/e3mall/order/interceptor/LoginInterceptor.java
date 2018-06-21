package cn.e3mall.order.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;

public class LoginInterceptor implements HandlerInterceptor{
	@Value("${SSO_URL}")
	private String SSO_URL;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CartService cartService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		/**
		 * 从cookie中取token，如果没有取到说明用户没有登陆，重定向到sso的登陆页面
		 * 取到了token，调用sso，根据token提取用户的信息
		 * 如果没有取到用户的信息，说明登陆过期了，需要登陆
		 * 将用户信息写入request中
		 * 判断cookie中是否有购物车数据，如果有就合并购物车
		 * 放行
		 */
		
		String token = CookieUtils.getCookieValue(request,"token");
		if(StringUtils.isBlank(token)){
			response.sendRedirect(SSO_URL+"/page/login?redirect="+request.getRequestURL());//用户登陆成功后跳转到当前请求的url
			return false;
		}
		TaotaoResult result = tokenService.getUserByToken(token);
		//如果取不到user，说明登陆过期了
		if(result.getStatus()!=200){
			response.sendRedirect(SSO_URL+"/page/login?redirect="+request.getRequestURL());//用户登陆成功后跳转到当前请求的url
			return false;
		}
		TbUser user = (TbUser) result.getData();
		request.setAttribute("user",user);
		//判断cookie中是否有购物车信息
		String json = CookieUtils.getCookieValue(request,"cart",true);
		if(StringUtils.isNoneBlank(json)){
			cartService.mergerCart(user.getId(),JsonUtils.jsonToList(json,TbItem.class));
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		
	}

}
