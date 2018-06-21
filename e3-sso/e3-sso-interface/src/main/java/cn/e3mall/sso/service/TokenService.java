package cn.e3mall.sso.service;

import cn.e3mall.common.pojo.TaotaoResult;

public interface TokenService {
	TaotaoResult getUserByToken(String token);
}
