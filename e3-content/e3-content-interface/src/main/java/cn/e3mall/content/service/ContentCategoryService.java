package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.EasyUiTreeNode;
import cn.e3mall.common.pojo.TaotaoResult;

public interface ContentCategoryService {

	List<EasyUiTreeNode> getContentCatList(long parentId);
	TaotaoResult addContentCategory(long parentId, String name);
	TaotaoResult updateContentCategory(long id, String name);

}
