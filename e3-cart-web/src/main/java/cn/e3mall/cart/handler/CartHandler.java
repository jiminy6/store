package cn.e3mall.cart.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;

public class CartHandler implements HandlerInterceptor{
	@Autowired
	private TokenService tokenService;
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		//从cookie中取出token，如果没有取到，直接放行
		//如果取到了，token中用户过期了，直接放行
		//取到了，用户为登陆状态，将用户信息放入request中
		//true为放行，false为拦截
		String token = CookieUtils.getCookieValue(request,"token");
		//做null判断和blank判断有什么区别
		if(StringUtils.isBlank(token)){
			return true;
		}
		TaotaoResult result = tokenService.getUserByToken(token);
		//过期了
		if(result.getStatus()!=200){
			return true;
		}
		  TbUser user = (TbUser) result.getData();
		  System.out.println("这个是拦截器");
		  System.out.println(user);
		  //将user存入request中
		  request.setAttribute("user",user);
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	//执行handler之后，返回modelAndView之前
	
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	//返回modelAndView之后
	
	}

}
