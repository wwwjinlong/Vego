package jplat.core.session.redis;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jplat.base.constant.KPlatResponseCode;
import jplat.core.cache.redis.JRedisConnectorImpl;
import jplat.core.session.JSession;
import jplat.core.session.JSessionUtils;
import jplat.core.trans.JIUserInfo;
import jplat.error.exception.JSystemException;
import jplat.tools.coder.JsonCoder;
import jplat.tools.string.DateUtil;
import jplat.tools.string.JRandomUtil;
import jplat.tools.string.StringUtil;
import z.log.tracelog.JTraceLogUtils;
import z.log.tracelog.KTraceLog;

/**
 * Redis会话实现类,只有在认证通过之后才建立会话.
 * 该类定位为一个轻量级的redis会话.
 * @author zhangcq
 *
 */
public class JRedisSession implements JSession
{
	private static Logger logger = LoggerFactory.getLogger(JRedisSession.class);
	
	private static Class usrInfoClass = JSessionUtils.getUserClass();
	
	private static JRedisConnectorImpl connector = JRedisConnectorImpl.getInstance();
	
	private String sessToken;				//有效token
	private String createTime;				//首次创建时间.

	private boolean isNew = false;			//是否有效.
	private boolean isValid = false;		//是否可用.(not used).

	private static long REDIS_SETOK_0 = 0;
	private static long REDIS_SETOK_1 = 1;
	
	private Map<String,String> valueMap;

	public JRedisSession() throws JSystemException
	{
		sessToken = JRandomUtil.getUUID();
		createTime = DateUtil.todayStr(DateUtil.FMT_ALL);
		
		//检测是否存在.
		if( connector.hset(sessToken,K_CREATE_TIME,createTime,TIME_OUT) == JRedisConnectorImpl.EX_OLD )
		{
			logger.error(JTraceLogUtils.getTraceLog(KTraceLog.ACTION_REDIS, KTraceLog.EVENT_FAIL, "", JTraceLogUtils.buildUserData("duplicate",sessToken)));
			throw new JSystemException(KPlatResponseCode.CD_REDIS_SESSION_INIT_ERR,KPlatResponseCode.MSG_REDIS_SESSION_INIT_ERR);
		}
		
		isNew = true;
		isValid = true;
	}

	/**
	 * 仅仅检测会话是否可用.
	 * @param sessionID
	 * @throws JSystemException
	 */
	public JRedisSession( String sessionID ) throws JSystemException
	{
		if ( StringUtil.isEmpty(sessionID) )
		{
			logger.error(JTraceLogUtils.getTraceLog(KTraceLog.ACTION_REDIS, KTraceLog.EVENT_FAIL, "", JTraceLogUtils.buildUserData("session_timeout","empty-id")));
			throw new JSystemException(KPlatResponseCode.CD_NO_SESSION,KPlatResponseCode.MSG_NO_SESSION);
		}
		
		//检测是否存在.
		if( StringUtil.isEmpty(connector.hget(sessionID,K_CREATE_TIME,TIME_OUT)))
		{
			logger.error(JTraceLogUtils.getTraceLog(KTraceLog.ACTION_REDIS, KTraceLog.EVENT_FAIL, "", JTraceLogUtils.buildUserData("session_timeout",sessToken)));
			throw new JSystemException(KPlatResponseCode.CD_NO_SESSION,KPlatResponseCode.MSG_NO_SESSION);
		}
		
		sessToken = sessionID;
		isValid = true;
	}
	
	/**
	 * 一般检测会话就是为了获取用户信息.
	 * @param sessionID
	 * @throws JSystemException
	 */
	public JRedisSession( String sessionID, String ...fields ) throws JSystemException
	{
		if ( StringUtil.isEmpty(sessionID) )
		{
			logger.error(JTraceLogUtils.getTraceLog(KTraceLog.ACTION_REDIS, KTraceLog.EVENT_FAIL, "", JTraceLogUtils.buildUserData("session_timeout","empty-id")));
			throw new JSystemException(KPlatResponseCode.CD_NO_SESSION,KPlatResponseCode.MSG_NO_SESSION);
		}
		
		for ( int i = 0; i < fields.length; ++i )
		{
			valueMap = connector.hmget(sessionID, TIME_OUT, fields);
		}
		
		sessToken = sessionID;
		isValid = true;
	}

	@Override
	public String getValue(String key) {
		// TODO Auto-generated method stub
		return connector.hget(sessToken,key,TIME_OUT);
	}

	@Override
	public boolean setValue(String key, String value) {
		// TODO Auto-generated method stub
		long retl = connector.hset(sessToken,key,value,TIME_OUT);
		return retl==REDIS_SETOK_0||retl==REDIS_SETOK_1?true:false;
	}

	@Override
	public <T> T getObj(String key, Class<T> kclass) {

		// TODO Auto-generated method stub
		String value = getValue(key);
		if (StringUtil.isEmpty(value) )
		{
			return null;
		}
		
		return JsonCoder.fromJsonString(value,kclass);
	}

	@Override
	public boolean setObj(String key, Object value)
	{
		// TODO Auto-generated method stub
		String json = JsonCoder.toJsonString(value);
		
		return setValue(key,json);
	}

	@Override
	public boolean isValid() {
		// TODO Auto-generated method stub
		return isValid;
	}

	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return isNew;
	}

	@Override
	public int sessionType() {
		// TODO Auto-generated method stub
		return JSession.SESS_REDIS;
	}

	@Override
	public String getSessionID() {
		// TODO Auto-generated method stub
		return sessToken;
	}

	//记得要getObj后要set回来，不然不会更新.
	@Override
	public boolean setUserInfo(Object obj) {
		// TODO Auto-generated method stub
		return setObj(K_USER_INFO,obj);
	}

	@Override
	public JIUserInfo getUserInfo() {
		// TODO Auto-generated method stub
		return (JIUserInfo)(getObj(K_USER_INFO,usrInfoClass))	;
	}

	@Override
	public boolean removeValue(String key){
		// TODO Auto-generated method stub
		long ret = connector.hdel(sessToken,key);
		return (ret==1 || ret == 0)?true:false;
	}

	@Override
	public boolean removeObj(String key) {
		// TODO Auto-generated method stub
		return removeValue(key);
	}

	@Override
	public boolean destroy() {
		// TODO Auto-generated method stub
		return false;
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
	
/*	public static void main(String args[]) throws JSystemException
	{
		JRedisSession session = new JRedisSession();
		
		Map<String,String> data = new HashMap<String,String>();
		data.put("k1", "v1");
		data.put("k2", "v2");
		session.setObj("k1", data );
		XLog.log(""+session.getSessionID());
		
		data = (HashMap<String,String>)session.getObj("k1");
		XLog.log(JsonCoder.toJsonString(data));
	}*/
}
