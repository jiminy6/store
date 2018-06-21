package cn.e3mall.sso.service;

import cn.e3mall.common.pojo.TaotaoResult;

public interface LoginService {
	TaotaoResult login(String username,String password);
}
