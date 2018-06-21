package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.pojo.TbContent;

public interface ContentService {
	TaotaoResult addContent(TbContent content);
	List<TbContent> findContentListByCid(long cid);
}
