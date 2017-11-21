package jplat.base.constant;

//配置键.
public class KConfigKeys
{
	//缓存有效时间.
	public static String CACHE_EXPIRE_TIME = "app.cache.expires";
	
	//会话有效时间.
	public static String SESSION_EXPIRE_TIME = "app.session.expires";
	
	public static String USER_INFO_KCLASS = "app.user.class";
	
	//Redis-config keys
	public static String REDIS_SERVER = "redis.server";
	public static String REDIS_PORT = "redis.port";
	public static String REDIS_CONNECT_TIME_OUT = "redis.connect.timeout";	//链接最大超时时间.(毫秒)
	
	public static String REDIS_MAX_WAIT_MILLIS = "redis.max.wait.millis";	//阻塞最大等待时间.(毫秒)
	public static String REDIS_MAX_CONNECT = "redis.max.connect";			//最大链接数.
	public static String REDIS_MIN_IDLE = "redis.min.idle";					//最小空闲数.
	public static String REDIS_PASSWORD = "redis.password";					//密码.
	public static String REDIS_LOGON = "redis.logon";						//redis访问日志开关.
}
