package cn.e3mall.sso.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.RegisterService;

@Controller
public class RegisterController {
	@Autowired
	private RegisterService registerService; 
	
	@RequestMapping("/page/register")
	/**
	 * 
	     * 说明：返回注册页面的逻辑视图
	     * @return
	     * @author luowenxin
	     * @time：2017年12月15日 下午1:00:04
	 */
	public String showRegister(){
		return "register";
	}
	
	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	/**
	 * 
	     * 说明：检验用户输入信息的controller
	     * @param param
	     * @param type
	     * @return
	     * @author luowenxin
	     * @time：2017年12月15日 下午5:01:20
	 */
	//requestMapping中的/{param}/{type}和参数中的param,type保持一致，用@pathVariable
	public TaotaoResult checkData(@PathVariable String param,@PathVariable int type){
		TaotaoResult result = registerService.checkData(param, type);
		return result;
	}
	//注意这后面的method=post
	@RequestMapping(value="/user/register",method=RequestMethod.POST)
	@ResponseBody
	/**
	 * 
	     * 说明: 用户注册的controller
	     * @param user
	     * @return
	     * @author luowenxin
	     * @time：2017年12月15日 下午5:12:56
	 */
	public TaotaoResult register(TbUser user){
	 TaotaoResult result = registerService.register(user);
	 return result;
	}
}
