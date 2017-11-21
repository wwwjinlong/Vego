package jplat.core.session;

import javax.servlet.http.HttpServletRequest;

import jplat.base.constant.KPlatResponseCode;
import jplat.core.session.redis.JRedisSession;
import jplat.core.trans.JAppContext;
import jplat.core.trans.impl.JServletAppContext;
import jplat.error.exception.JSystemException;
import jplat.tools.config.JAppConfig;

/**
 * 创建会话,一定是在需要的时候调用.
 * @author zhangcq
 *
 */
public class JSessionFactory
{
	public static int sessType = JAppConfig.SESSION_TYPE;
	
	public HttpServletRequest request;
	public String sessionId;
	
	public JSession createSession( boolean create ) throws JSystemException
	{
		switch (sessType)
		{
		case JSession.SESS_HTTP:
			return new JHttpSession( request, create );
		case JSession.SESS_REDIS:
			if (create) {
				return new JRedisSession();
			}

			return new JRedisSession( sessionId );

		default:
			throw new JSystemException(KPlatResponseCode.CD_CONF_ERROR,	KPlatResponseCode.MSG_CONF_ERROR);
		}
	
	}
	
	public JSessionFactory setHttpRequest( HttpServletRequest req )
	{
		request = req;
		return this;
	}
	
	public JSessionFactory setSessionId( String sessId )
	{
		sessionId = sessId;
		return this;
	}
}
