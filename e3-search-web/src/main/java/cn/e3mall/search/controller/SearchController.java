package cn.e3mall.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.SearchResult;
import cn.e3mall.search.service.SearchService;

@Controller
public class SearchController {
	@Value("${SEARCH_RESULT_ROWS}")
	private int rows;
	@Autowired
	private SearchService searchService;
	@RequestMapping("/search")
	public String search(String keyword,@RequestParam(defaultValue="1") Integer page,Model model) throws Exception{
		keyword=new String(keyword.getBytes("iso-8859-1"),"utf-8");
		SearchResult search = searchService.search(keyword, page, rows);
		model.addAttribute("query",keyword);
		model.addAttribute("totalPages",search.getTotalPages());
		model.addAttribute("itemList",search.getItemList());
		model.addAttribute("page",page);
		model.addAttribute("recourdCount",search.getRecourdCount());
		//返回逻辑视图
//		int i=1/0;
		return "search";
	}
}
