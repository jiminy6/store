package cn.e3mall.service;

import java.util.List;

import cn.e3mall.common.pojo.EasyUiTreeNode;
import cn.e3mall.pojo.TbItemCat;

public interface ItemCatService {
	List<TbItemCat> getAllItemCat();
	List<EasyUiTreeNode> getByParentNode(Long parentId);
}
