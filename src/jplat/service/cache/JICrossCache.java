package jplat.service.cache;

/**
 * 通用缓存类,只支持Sring类型的缓存.
 * 要做高级缓存，可以封装自己的类型，比如要支持对象的序列化和反序列化等.
 * 默认十分钟.
 * @author zhangcq
 * @date Jan 9, 2017
 * @comment
 */
public interface JICrossCache
{
	//前缀.
	public static String PREFIX = "jch:";
	
	public static String K_CREATE_TIME = "create_time_";	//创建时间.
	
	//seconds(秒)
	public static int TIME_OUT_SECONDS = 600;
	
	public boolean set( String key, String value );
	
	public String get( String key );
	
	public boolean remove( String key );
	
	public boolean destroy();
	
	public String getID();
	
	//剩余时间.seconds.
	public long timeleft();
	
	public boolean isNew();
}
