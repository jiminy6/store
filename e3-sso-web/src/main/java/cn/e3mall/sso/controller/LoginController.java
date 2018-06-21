package cn.e3mall.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.sso.service.LoginService;

@Controller
public class LoginController {
	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;
	@Autowired
	private LoginService loginService;
	@RequestMapping("/page/login")
	/**
	 * 
	     * 说明：返回用户登陆的逻辑视图
	     * 从url中取到redirect的值，并将其放入login页面中
	     * @return
	     * @author luowenxin
	     * @time：2017年12月15日 下午5:43:56
	 */
	public String showLogin(String redirect,Model model){
		model.addAttribute("redirect",redirect);
		return "login";
	}
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	@ResponseBody
	/**
	 * 
	     * 说明：用户登陆的controller
	     * @return
	     * @author luowenxin
	     * @time：2017年12月15日 下午5:45:57
	 */
	public TaotaoResult login(String username,String password,HttpServletRequest request,HttpServletResponse response){
		//username 和 password和表单中一致时候，可以直接接收，不需要@pathVariable
		TaotaoResult result = loginService.login(username, password);
		if(result.getStatus()==200){
			//如果登陆成功，就将信息写入cookie中
			String token = (String) result.getData();
			CookieUtils.setCookie(request, response,TOKEN_KEY,token);
		}
		return result;
	}
	
}
