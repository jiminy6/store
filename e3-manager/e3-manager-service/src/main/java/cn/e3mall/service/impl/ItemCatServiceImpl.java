package cn.e3mall.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.EasyUiTreeNode;
import cn.e3mall.mapper.TbItemCatMapper;
import cn.e3mall.pojo.TbItemCat;
import cn.e3mall.pojo.TbItemCatExample;
import cn.e3mall.pojo.TbItemCatExample.Criteria;
import cn.e3mall.service.ItemCatService;

@Service("itemCatService")
public class ItemCatServiceImpl implements ItemCatService {
	@Autowired
	private TbItemCatMapper tbitemCatMapper;
	@Override
	public List<TbItemCat> getAllItemCat() {
		TbItemCatExample example = new TbItemCatExample();
		return tbitemCatMapper.selectByExample(example);
	}

	@Override
	public List<EasyUiTreeNode> getByParentNode(Long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		 Criteria criteria = example.createCriteria();//设置查询条件
		 criteria.andParentIdEqualTo(parentId);
		  List<TbItemCat> selectByExample = tbitemCatMapper.selectByExample(example);//得到所有parentId的child
		  List<EasyUiTreeNode> treeNodelists = new ArrayList<>();
		for (TbItemCat tbItemCat : selectByExample) {
			EasyUiTreeNode node = new EasyUiTreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
		    treeNodelists.add(node);
		}
		return treeNodelists;
	}

}
