package jplat.core.session;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import jplat.base.constant.KPlatResponseCode;
import jplat.core.session.redis.JRedisSession;
import jplat.core.trans.JAppConnectInfo;
import jplat.error.exception.JSystemException;
import jplat.tools.config.JAppConfig;

public class JSessionUtils {
	/**
	 * 获取Redis会话ID.
	 * 
	 * @author zhangcq
	 * @date Apr 11, 2017
	 * @comment
	 * @param request
	 * @return
	 */
	public static String obtSessionId(HttpServletRequest request)
	{
		Map<String, String> cookMap = new HashMap<String, String>();

		cookMap.put(JAppConnectInfo.H_HTOKEN_NAME,
				request.getHeader(JAppConnectInfo.H_HTOKEN_NAME));

		// 从Cookie中获取:所以,如果值重复,最后是以Cookie中的为准.
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; ++i) {
				cookMap.put(cookies[i].getName(), cookies[i].getValue());
			}
		}

		return cookMap.get(JAppConnectInfo.H_HTOKEN_NAME);
	}

	/**
	 * 检测会话是否有效.
	 * 
	 * @author zhangcq
	 * @date Apr 11, 2017
	 * @comment
	 * @param request
	 * @return
	 */
	public static JSession checkSessionAvail( HttpServletRequest request ) {
		try
		{
			return new JSessionFactory().setHttpRequest(request)
						.setSessionId(obtSessionId(request)).createSession(false);
		}
		catch ( JSystemException e)
		{
			// TODO Auto-generated catch block
			// e.printStackTrace();
			return null;
		}
	}
}
