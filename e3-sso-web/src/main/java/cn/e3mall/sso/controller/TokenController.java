package cn.e3mall.sso.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.sso.service.TokenService;

@Controller
public class TokenController {
	@Autowired
	private TokenService tokenService;
	@RequestMapping(value="/user/token/{token}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	//老版本写法
	public String  getUserByToken(@PathVariable String token,String callback){
		TaotaoResult result = tokenService.getUserByToken(token);
		if(StringUtils.isNotBlank(callback)){
			System.out.println("这个进来了=========================================================");
			//判断是否是jsonp请求
			//如果是就返回callback的js函数
			return callback+"("+JsonUtils.objectToJson(result)+")";
		}
		return JsonUtils.objectToJson(result);
	}
	
//	@RequestMapping(value="/user/token/{token}",produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
//	@ResponseBody
	//这个写法在spring4.2之后支持
	/**
	 * 
	     * 说明：根据token查询用户信息
	     * @param token
	     * @param callback jsonp的回调函数
	     * @return
	     * @author luowenxin
	     * @time：2017年12月18日 下午9:07:05
	 */
//	public Object  getUserByToken(@PathVariable String token,String callback){
//		TaotaoResult result = tokenService.getUserByToken(token);
//		if(StringUtils.isNotBlank(callback)){
//			//判断是否是jsonp请求
//			//如果是就返回callback的js函数
//			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
//			mappingJacksonValue.setJsonpFunction(callback);
//			return mappingJacksonValue;
//		}
//		return result;
//	}
}
