package cn.e3mall.item.message;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import cn.e3mall.item.pojo.Item;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
@Component("genhtmlListener")
public class GenHtmlListener implements MessageListener{
	/**
	 * 
	     * 说明：接收从e3-manager中的topic信息(id),根据id查找item和itemDesc
	     * 将信息写入template中,生成jsp
	     * @param message
	     * @author luowenxin
	     * @time：2017年12月14日 上午9:05:43
	 */
	@Autowired
	private ItemService itemService;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Value("${HTML_GEN_PATH}")
	private String HTML_GEN_PATH;//产生的静态页的存放目录
	@Override
	public void onMessage(Message message) {
	 try {
		 System.out.println("item-web开始监听topic信息");
		 TextMessage textmessage = (TextMessage) message;
		String text = textmessage.getText();
		long itemId = Long.parseLong(text);
		Thread.sleep(1000);//等待事务的提交	
		TbItem tbitem = itemService.getItemById(itemId);//先得到tbitem对象,再通过tbitem的构造方法得到item对象
		Item item = new Item(tbitem);
		TbItemDesc itemDesc = itemService.getItemDescById(itemId);
		Configuration configuration = freeMarkerConfigurer.getConfiguration();
		Template template = configuration.getTemplate("item.ftl");
		Map<String, Object> data = new HashMap<>();
		data.put("item",item);
		data.put("itemDesc", itemDesc);
	    Writer out =new FileWriter(new File(HTML_GEN_PATH+itemId+".html"));//生成静态文件,并指定文件名
	    template.process(data, out);
	    out.close();
	    System.out.println("监听完成");
	} catch (Exception e) {
		e.printStackTrace();
	}
	}

}
