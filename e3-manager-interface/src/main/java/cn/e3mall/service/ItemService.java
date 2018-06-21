package cn.e3mall.service;

import cn.e3mall.common.pojo.EasyUiDataGridResult;
import cn.e3mall.pojo.TbItem;

public interface ItemService {
	TbItem getItemById(Long id);
	EasyUiDataGridResult getItemList(int page,int rows);
}
