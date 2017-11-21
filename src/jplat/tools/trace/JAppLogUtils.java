package jplat.tools.trace;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jplat.base.constant.KDolphinq;
import jplat.core.trans.JAppConnectInfo;
import jplat.core.trans.JClientRspHeader;
import jplat.error.exception.JBaseException;
import jplat.tools.coder.JsonCoder;
import jplat.tools.string.StringUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JAppLogUtils {
	
	private static Logger logger = LogManager.getLogger(JAppLogUtils.class);
	
	public static String buildErrMessage( JBaseException sysException,HttpServletResponse response )
	{
//		logger.error(sysException.getErrCode()+":"+sysException.getErrMsg());
		
		Map<String,Object> errMap = new HashMap<String,Object>();
		
		errMap.put(KDolphinq.H_RSP_HEAD, new JClientRspHeader(sysException.getErrCode(),sysException.getErrMsg()));
		errMap.put(KDolphinq.H_RSP_BODY, new HashMap() );

		//设置错误头.
		String errCode = StringUtil.getDefString(sysException.getErrCode(),JAppConnectInfo.V_HRETCODE_FAIL);
		response.setHeader( JAppConnectInfo.H_RET_HEAD, errCode );
//		response.setContentType("application/json;charset:utf-8");
		return JsonCoder.toJsonString(errMap);
	}
	
	/**
	 * 显示请求内容
	 * 
	 * @param request
	 */
	public static String getHeadValues( HttpServletRequest request)
	{
		Enumeration e = request.getHeaderNames();

		StringBuffer sbuffer = new StringBuffer();
		sbuffer.append("client-ip:")
		.append(StringUtil.getIpAddr(request))
		.append("-")
		.append(request.getRemoteHost())
		.append(":").append(request.getRemotePort()).append("\n");
		while (e.hasMoreElements()) {
			String a = (String) e.nextElement();
			sbuffer.append(a).append(":").append(request.getHeader(a)).append("\n");
		}

		return sbuffer.toString();
	}
	
	/**
	 * 显示请求内容
	 * 
	 * @param request
	 */
	public static void showHeadValues(HttpServletRequest request)
	{
		StringBuilder sbuffer = new StringBuilder();
		logger.info("HEADER_VALUEs: "+sbuffer.append("infoq:").append(request.getHeader("infoq"))
				.append(";ttkn2:").append(request.getHeader("ttkn2")));
//				.append("\nuser-agent:").append(request.getHeader("user-agent")));

		Cookie[] cks = request.getCookies();
		if ( cks != null )
		{
			StringBuilder cb = new StringBuilder();
			for ( int i = 0; i < cks.length; ++i )
			{
				Cookie cookie = cks[i];

				cb.append(cookie.getName()).append("=")
				.append(cookie.getValue()).append(";");
			}

			logger.info("COOKIE_VALUEs: "+cb.toString());
		}
	}

}
