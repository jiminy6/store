package cn.e3mall.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.pojo.EasyUiDataGridResult;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;

 /**
     * 说明：商品service
     * @author luowenxin
     * @version 1.0
     * @date 2017年11月27日
     */
@Service("itemService")
//@Transactional
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Override
	public TbItem getItemById(Long id) {
		TbItem selectByPrimaryKey = itemMapper.selectByPrimaryKey(id);
		return selectByPrimaryKey;	
	}
	@Override
	public EasyUiDataGridResult getItemList(int page,int rows) {
		PageHelper.startPage(page,rows);//设置分页信息
		TbItemExample example = new TbItemExample();
	    List<TbItem> list = itemMapper.selectByExample(example);//执行查询
	    EasyUiDataGridResult result = new EasyUiDataGridResult();
	    result.setRows(list);
	    //取分页结果
	    PageInfo<TbItem> pageInfo = new PageInfo<>(list);
	    long total = pageInfo.getTotal();//设置总记录数	
	    result.setTotal(total);
	    return result;
	}
}
