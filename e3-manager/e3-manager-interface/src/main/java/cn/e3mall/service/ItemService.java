package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUiDataGridResult;
import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

public interface ItemService {
	TbItem getItemById(Long id);
	EasyUiDataGridResult getItemList(int page,int rows);
	TaotaoResult addItem(TbItem item,String desc);
	TbItemDesc getItemDescById(Long id);
}
