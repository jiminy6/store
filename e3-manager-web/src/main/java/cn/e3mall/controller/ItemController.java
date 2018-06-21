package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.EasyUiDataGridResult;
import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;

@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	 
	    /**
	     * 说明：为什么这个返回的就是一个TbItem的类型的数据，它还是显示参数不正确，no converter found  真是尴尬了，特么jackson的jar包没导入！终于解决了
	     * @param itemId
	     * @return
	     * @author luowenxin
	     * @time：2017年11月28日 上午12:17:45
	     */
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId){
		TbItem itemById = itemService.getItemById(itemId);
		return itemById;
	}
	
//	@RequestMapping("/item/list")
//	@ResponseBody
//	public EasyUiDataGridResult getItemList(Integer page,Integer rows){
//		EasyUiDataGridResult result=itemService.getItemList(page, rows);
//		return result;
//	}
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUiDataGridResult getItemList(Integer page, Integer rows) {
		//调用服务查询商品列表
		EasyUiDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult saveItem (TbItem item,String desc){
		return itemService.addItem(item, desc);
	}
}
