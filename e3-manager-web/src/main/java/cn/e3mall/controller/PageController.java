package cn.e3mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {
	@RequestMapping("/")
	public String showIndex(){
		return "index";
	}
	/**
	 * 
	     * 说明：因为请求url和页面的jsp名字一致。所以可以，请求叫什么，我就返回这个名字的jsp！很厉害呀。
	     * @param page
	     * @return
	     * @author luowenxin
	     * @time：2017年12月1日 下午1:13:23
	 */
	@RequestMapping("/{page}")
	public String showPage(String page){
		return page;
	}
	
}
