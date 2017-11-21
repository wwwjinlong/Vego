package jplat.core.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jplat.base.constant.KConfigKeys;
import jplat.base.constant.KPlatResponseCode;
import jplat.core.trans.JIUserInfo;
import jplat.error.exception.JSystemException;
import jplat.tools.config.JConfigManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import z.log.tracelog.JTraceLogUtils;
import z.log.tracelog.KTraceLog;
import z.log.tracelog.XLog;

/**
 * 会话实现类.
 * 
 * @author zhangcq
 *
 */
public class JHttpSession implements JSession
{
	private static Logger logger = LogManager.getLogger(JHttpSession.class);
	
	private static Class usrInfoClass = getUserClass();
	
	HttpSession session;
	HttpServletRequest request;

	// 这里的逻辑是只有需要创建会话或者需要会话才会调用该方法.
	// 否则只要会话不存在则报超时.
	// 即该类是按需调用，不能乱用.
	public JHttpSession(HttpServletRequest req, boolean create)
			throws JSystemException {
		request = req;

		// 强制作废老会话.
		if (create && request.isRequestedSessionIdValid()) {
			HttpSession sess = req.getSession();
			logger.info(JTraceLogUtils.getTraceLog(KTraceLog.ACTION_SESSION,
					KTraceLog.EVENT_POINT, "",
					JTraceLogUtils.buildUserData("force-destroy", sess.getId())));
			try {
				if (sess != null)
					sess.invalidate();
			} catch (Exception e) {
				logger.error(JTraceLogUtils.getTraceLog(
						KTraceLog.ACTION_SESSION,
						KTraceLog.EVENT_FAIL,
						"",
						JTraceLogUtils.buildUserData("sess.invalidate",
								e.getMessage(), sess.getId())));
			}
		}

		session = req.getSession(create);
		
		// 不创建,但是没找到则超时.请按需调用.
		if (!create && session == null) {
			throw new JSystemException(KPlatResponseCode.CD_NO_SESSION,
					KPlatResponseCode.MSG_NO_SESSION);
		}
	}

	@Override
	public String getSessionID() {
		if (session != null) {
			return session.getId();
		}

		return null;
	}

	@Override
	public boolean isValid() {
		if (session == null /* || request.getSession( false ) == null */) {
			return false;
		}

		return true;
	}

	@Override
	public String getValue(String key) {
		// TODO Auto-generated method stub
		return getObj(key,String.class);
	}

	@Override
	public boolean setValue(String key, String value) {
		// TODO Auto-generated method stub
		return setObj(key, value);
	}
	
	@Override
	public <T> T getObj(String key, Class<T> kclass) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		
		if ( session != null )
		{
			return (T)(session.getAttribute(key));
		}
		
		return null;
	}

	@Override
	public boolean setObj(String key, Object value) {
		// TODO Auto-generated method stub
		if (session != null) {
			session.setAttribute(key, value);
			return true;
		}

		return false;
	}

	@Override
	public boolean removeObj(String key) {
		// TODO Auto-generated method stub
		if (session != null) {
			session.removeAttribute(key);
			return true;
		}
		
		return false;
	}

	@Override
	public boolean setUserInfo(Object obj) {
		// TODO Auto-generated method stub
		return setObj(K_USER_INFO, obj);
	}

	@Override
	public JIUserInfo getUserInfo() {
		// TODO Auto-generated method stub
		return (JIUserInfo)(getObj(K_USER_INFO,usrInfoClass));
	}

	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return session.isNew();
	}

	@Override
	public boolean removeValue(String key) {
		// TODO Auto-generated method stub
		return removeObj(key);
	}

	/******
	 * CTHttpSession特有的getter-setters
	 ******/
	public HttpSession getSession() {
		return session;
	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public boolean destroy() {
		// TODO Auto-generated method stub
		session.invalidate();
		
		return true;
	}

	@Override
	public int sessionType() {
		// TODO Auto-generated method stub
		return JSession.SESS_HTTP;
	}

	@Override
	public String getEncryptKey(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getEncryptKey(String type, String encKey) {
		// TODO Auto-generated method stub
		return false;
	}
	
	private static Class getUserClass()
	{
		String userInfoClz = JConfigManager.getInstance().getSystemConfig().getString(KConfigKeys.USER_INFO_KCLASS);
		logger.info(XLog.SYS_INIT+"__USER_INFO_CLASS="+userInfoClz);
		try
		{
			return Class.forName(userInfoClz);
		}
		catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("ERROR:no user info class found.");
		}
	}
}
