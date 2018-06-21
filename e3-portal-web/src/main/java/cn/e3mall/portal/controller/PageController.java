package cn.e3mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.TbContent;


@Controller
public class PageController {
	@Value("${CONTENT_LUNBO_ID}")
	private long CONTENT_LUNBO_ID;
	
	@Autowired
	private ContentService contentService;
	@RequestMapping("/index")
	public String showIndex(Model model){
		List<TbContent> contentListByCid = contentService.findContentListByCid(CONTENT_LUNBO_ID);
		//将结果传递到页面。在参数中引入Model，然后执行model.setAttribute("","list")将这个信息传递到页面上
		System.out.println("====================================================================");
        System.out.println(contentListByCid);
		model.addAttribute("ad1List",contentListByCid);
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
//	@RequestMapping("/{page}")
//	public String showPage(String page){
//		return page;
//	}
	
	
	
	
	
}
