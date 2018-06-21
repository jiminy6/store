package cn.e3mall.sso.service;

import cn.e3mall.common.pojo.TaotaoResult;
import cn.e3mall.pojo.TbUser;
/**
 * 
     * 说明：
     * @author luowenxin
     * @version 1.0
     * @date 2017年12月15日
 */
public interface RegisterService {
	    /**
	     * 
	         * 说明：检验用户注册的信息
	         * @param param:
	         * @param type:1:用户名;2:手机:3:email.实现在一个方法中校验三个参数
	         * @return
	         * @author luowenxin
	         * @time：2017年12月15日 下午3:46:42
	     */
		TaotaoResult checkData(String param,int type);
	    /**
	     * 
	         * 说明：
	         * @param user
	         * @return
	         * @author luowenxin
	         * @time：2017年12月15日 下午3:46:15
	     */
		TaotaoResult register(TbUser user);
}
