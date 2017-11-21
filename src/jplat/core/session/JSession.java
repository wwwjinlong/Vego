package jplat.core.session;

import jplat.base.constant.KConfigKeys;
import jplat.core.trans.JIUserInfo;
import jplat.tools.config.JConfigManager;

/**
 * 会话对象接口类. 1. 由于Redis和序列化限制,暂不支持泛型序列化. 2. 但是可以将泛型对象包装成
 * 一个成员对象，进行序列化.参考,DnAuthGrapper 2. 可以用Spring-Redis实现. 3.
 * 可以用Java对象序列化-反序列化优化，但是那样就不通用了.
 * 
 * @author zhangcq
 *
 */
public interface JSession
{
	//超时时间.
	public static final int TIME_OUT = JConfigManager.getInstance()
			.getSystemConfig().getInt(KConfigKeys.SESSION_EXPIRE_TIME, 900);
	
	//HTTP类型的会话.
	public static final int SESS_HTTP = 200;
	
	//redis类型的会话.
	public static final int SESS_REDIS = 300;
	
	//用户键.
	public static final String K_USER_INFO = "user_info_";
	
	 //创建时间.
	public static final String K_CREATE_TIME = "create_time_";

	//加密键 一般为AES密钥.
	public static final String K_ENC_KEY = "aes_key_";

	// 是否是新建会话.
	public boolean isNew();

	//获取会话类型.
	public int sessionType();

	//获取会话ID
	public String getSessionID();

	// 协商密钥
	public String getEncryptKey(String type);

	public boolean getEncryptKey(String type, String encKey);

	// 客户信息管理.
	public boolean setUserInfo(Object obj) /* throws JSystemException */;

	public JIUserInfo getUserInfo() /* throws JSystemException */;

	// 保存key-value
	public String getValue(String key);

	public boolean setValue(String key, String value);

	public boolean removeValue(String key);

	//不能保存List类型,List可以自己包装一层对象,或者写一个明确List泛型的类型的泛型方法.
	public <T> T getObj( String key, Class<T> kclass );

	public boolean setObj(String key, Object value);

	public boolean removeObj(String key);

	//检查会话是否存在.即客户是否登录了.
	public boolean isValid();

	//销毁会话
	public boolean destroy();
}
