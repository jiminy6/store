package cn.e3mall.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.utils.FastDFSClient;
@Controller
public class PictureController {
	@Value("${IMG_SERVER_URL}")
	private String IMG_SERVER_URL;
	@RequestMapping(value="/pic/upload")
	@ResponseBody
	public Map fileupload(MultipartFile uploadFile){
		Map<Object, Object> map = new HashMap<>();
		try {
			FastDFSClient fastDFSClient = new FastDFSClient("/develop/Repository/e3-manager-web/src/main/resources/conf/client.conf");
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);//获取文件扩展名
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extName);
			url=IMG_SERVER_URL+url;
			System.out.println(url);
			map.put("error", 0);
			map.put("url", url);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", 1);
			map.put("message","文件上传失败");
		}
		return map;
	}
}
