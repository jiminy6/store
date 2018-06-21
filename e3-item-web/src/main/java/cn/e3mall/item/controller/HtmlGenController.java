package cn.e3mall.item.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;
@Controller
public class HtmlGenController {
	 
	    /**
	     * 说明：这个是用来测试产生静态化jsp
	     * @return
	     * @author luowenxin
	     * @time：2017年12月14日 上午12:51:44
	     */
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@RequestMapping("/genhtml")
	@ResponseBody
	public String getItem() throws Exception{
		Configuration configuration = freeMarkerConfigurer.getConfiguration();//Spring已经将configution中的编码,模板存放的目录设置过了
		Template template = configuration.getTemplate("hello.ftl");
		Map<Object, Object> data = new HashMap<>();//字符集
		data.put("hello", "1222");
		Writer out=new FileWriter(new File("/develop/tools/test/aa.html"));
		template.process(data, out);
		out.close();
		return "ok";
	}
}
