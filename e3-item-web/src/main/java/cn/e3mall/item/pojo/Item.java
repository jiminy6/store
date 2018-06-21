package cn.e3mall.item.pojo;

import cn.e3mall.pojo.TbItem;

 /**
     * 说明：从页面接收的图片是用，拼接的字符串。
     * 继承e3-common-pojo的tbitem，用来实现将字符串分割，储存成多个image数组
     * @author luowenxin
     * @version 1.0
     * @date 2017年12月12日
     */
public class Item extends TbItem  {
	public String[] getImages(){
		String image2 = this.getImage();
		if(image2!=null&&!"".equals(image2)){
			String[] split = image2.split(",");
			return split;
		}
		return null;
	}
	public Item (TbItem tbItem){
		this.setBarcode(tbItem.getBarcode());
		this.setId(tbItem.getId());
		this.setCreated(tbItem.getCreated());
		this.setNum(tbItem.getNum());
		this.setPrice(tbItem.getPrice());
		this.setSellPoint(tbItem.getSellPoint());
		this.setStatus(tbItem.getStatus());
		this.setTitle(tbItem.getTitle());
		this.setUpdated(tbItem.getUpdated());
		this.setImage(tbItem.getImage());
		this.setCid(tbItem.getCid());
	}
	
}
