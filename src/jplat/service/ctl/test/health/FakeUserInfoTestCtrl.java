package jplat.service.ctl.test.health;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jplat.base.constant.KPlatResponseCode;
import jplat.core.session.JSession;
import jplat.core.session.JSessionFactory;
import jplat.core.session.JSessionUtils;
import jplat.core.trans.JAppBaseService;
import jplat.core.trans.JIUserInfo;
import jplat.error.exception.JSystemException;
import jplat.tools.coder.JsonCoder;
import jplat.tools.config.JAppConfig;
import jplat.tools.stream.JServletStreamUtils;
import jplat.tools.string.JStringUtil;

@Controller
public class FakeUserInfoTestCtrl extends JAppBaseService
{
	private Logger logger = LoggerFactory.getLogger(FakeUserInfoTestCtrl.class);
	
	/****
	 * 查询客户信息.
	 * 2018年2月11日下午3:46:58
	 * loadFakeUserInfo
	 * @param request
	 * @param response
	 * @param sessId
	 * @return
	 * @throws JSystemException
	 */
	@RequestMapping("/test/user/query.do")
	public void loadFakeUserInfo( HttpServletRequest request, HttpServletResponse response ) throws JSystemException
	{
		//优先使用传过来的.
/*		String qSessId = request.getParameter("sessId");
		if ( JStringUtil.isEmpty(qSessId) )
		{
			qSessId = JSessionUtils.obtSessionId(request);
		}*/
		
		String qSessId = JSessionUtils.obtSessionId(request);
		
		logger.info("FAKE_SESSIONID_QUERY:sessId={}",qSessId);
		JSession jsession = new JSessionFactory().setHttpRequest(request)
				.setSessionId(qSessId).createSession(false);
		
//		String usrStr = jsession.getValue(JSession.K_USER_INFO);
		JIUserInfo userInfo = jsession.getUserInfo();
		
		logger.info("USER[{}]",JsonCoder.toJsonString(userInfo));
		
		try {
			JServletStreamUtils.writeHttpResponse(response, JsonCoder.toJsonString(userInfo).getBytes("utf-8"), "application/json");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JSystemException(KPlatResponseCode.CD_IO_ERR,KPlatResponseCode.MSG_IO_ERR);
		}
	}
	
	/**
	 * 新增和修改客户信息.
	 * 2018年2月11日下午3:55:18
	 * updateFakeUserInfo
	 * @param request
	 * @param response
	 * @param sessId
	 * @return
	 * @throws JSystemException
	 */
	@RequestMapping("/test/user/add.do")
	@ResponseBody
	public Map<String,String> updateFakeUserInfo( HttpServletRequest request, HttpServletResponse response ) throws JSystemException
	{
		Map<String,String> retMap = new HashMap<String,String>();
		
		if ( !JAppConfig.IS_TEST )
		{
			throw new JSystemException(KPlatResponseCode.CD_CONF_NOT_FOUND,KPlatResponseCode.MSG_CONF_NOT_FOUND);
		}
		
		boolean ifCreate = false;
		
		//优先使用传过来的.
/*		String qSessId = request.getParameter("sessId");
		if ( JStringUtil.isEmpty(qSessId) )
		{
			//然后从cookie和头里面取.
			qSessId = JSessionUtils.obtSessionId(request);
			if ( JStringUtil.isEmpty(qSessId) )
			{
				//都没有则创建.
				ifCreate = true;
				qSessId = "";
			}
		}*/
		
		String qSessId = JSessionUtils.obtSessionId(request);
		if ( JStringUtil.isEmpty(qSessId) )
		{
			ifCreate = true;
		}
		
		logger.info("FAKE_SESSIONID_ADD_IN:sessId={},create={}",qSessId,ifCreate);
		
		//创建会话.
		JSession jsession = new JSessionFactory().setHttpRequest(request)
				.setSessionId(qSessId).createSession(ifCreate);
		
		//读取用户数据.
		String userStr = JServletStreamUtils.readInputString(request,"utf-8",1024*1024);
		
		logger.info("FAKE_SESSIONID_ADD_USER_INPUT:sessId={},userStr={}",jsession.getSessionID(),userStr);
		
		//修改客户数据.
//		Class<?> userClazz = JSessionUtils.getUserClass();
		jsession.setValue(JSession.K_USER_INFO, userStr);
		
		//set cookie.
		Cookie sessCk = new Cookie("ttkn2",jsession.getSessionID() );
		sessCk.setPath("/");
		response.addCookie(sessCk);
		
		retMap.put("retCode", "0000");
		retMap.put("retMsg", "success");
		retMap.put("sessId", jsession.getSessionID() );
		
		return retMap;
	}
}
