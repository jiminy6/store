package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.EasyUiTreeNode;
import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;

/**
 * 内容分类管理Service
 * <p>Title: ContentCategoryServiceImpl</p>
 * <p>Description: </p>
 * @version 1.0
 */
@Service("contentCategoryService")
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	
	@Override
	public List<EasyUiTreeNode> getContentCatList(long parentId) {
		// 根据parentid查询子节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> catList = contentCategoryMapper.selectByExample(example);
		//转换成EasyUITreeNode的列表
		List<EasyUiTreeNode> nodeList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : catList) {
			EasyUiTreeNode node = new EasyUiTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			//添加到列表
			nodeList.add(node);
		}
		return nodeList;
	}
	/**
	 * 增加新的节点
	 * 参数：parentId：在哪个父节点中添加子节点，name，添加子节点的名字
	 * 业务逻辑：新建一个contentCategory,设置parentId等属性
	 * 调用dao层的方法，插入这个节点
	 * 根据parentId查询出contentCategory，如果之前isparent属性false，则设置成true
	 */
	@Override
	public TaotaoResult addContentCategory(long parentId, String name) {
//		//创建一个tb_content_category表对应的pojo对象
//		TbContentCategory contentCategory = new TbContentCategory();
//		//设置pojo的属性
//		contentCategory.setParentId(parentId);
//		contentCategory.setName(name);
//		//1(正常),2(删除)
//		contentCategory.setStatus(1);
//		//默认排序就是1
//		contentCategory.setSortOrder(1);
//		//新添加的节点一定是叶子节点
//		contentCategory.setIsParent(false);
//		contentCategory.setCreated(new Date());
//		contentCategory.setUpdated(new Date());
//		//插入到数据库
//		contentCategoryMapper.insert(contentCategory);
//		//判断父节点的isparent属性。如果不是true改为true
//		//根据parentid查询父节点
//		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
//		if (!parent.getIsParent()) {
//			parent.setIsParent(true);
//			//更新到数数据库
//			contentCategoryMapper.updateByPrimaryKey(parent);
//		}
//		//返回结果，返回TaoResult，包含pojo
//		return TaotaoResult.ok(contentCategory);
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setUpdated(new Date());
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setName(name);
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setStatus(1);//"1"正常，2删除
		tbContentCategory.setIsParent(false);
		contentCategoryMapper.insert(tbContentCategory);
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);//判断父节点的isparent属性
		if(!parent.getIsParent()){
			parent.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		return TaotaoResult.ok(tbContentCategory);
	}

	@Override
	public TaotaoResult updateContentCategory(long id, String name) {
		TbContentCategory content = contentCategoryMapper.selectByPrimaryKey(id);
		content.setName(name);
		contentCategoryMapper.updateByPrimaryKey(content);
	    
		return TaotaoResult.ok(content);
	}
}
