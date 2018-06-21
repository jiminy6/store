package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUiTreeNode;
import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.content.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	@Autowired
	private ContentCategoryService contentCategoryService;
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUiTreeNode> getContentCategoryList(@RequestParam(value="id",defaultValue="0")Long parentId){
		List<EasyUiTreeNode> list = contentCategoryService.getContentCatList(parentId);
		return list;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult addContentCategory(long parentId,String name){
		TaotaoResult result = contentCategoryService.addContentCategory(parentId, name);
		return  result;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public TaotaoResult updateContentCategory(long id,String name){
		TaotaoResult result=contentCategoryService.updateContentCategory(id,name);
		return result;
	}
	
}

