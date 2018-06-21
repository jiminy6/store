package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
@Service("contentService")
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper tbContentMapper;
	@Override
	public TaotaoResult addContent(TbContent content) {
		content.setCreated(new Date());
		content.setUpdated(new Date());
		tbContentMapper.insert(content);
		return TaotaoResult.ok();
	}
	@Override
	public List<TbContent> findContentListByCid(long cid) {
		TbContentExample example = new TbContentExample();
		List<TbContent> list = tbContentMapper.selectByExampleWithBLOBs(example);//这个是返回所有的信息
		return list;
	}

}
