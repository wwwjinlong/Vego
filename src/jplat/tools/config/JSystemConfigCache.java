package jplat.tools.config;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import jplat.core.session.JSession;

/**
 * 用于缓存常用配置信息.
 * 这样动态载入参数后会缓存到该类中.
 * 通过Loader加载数据,重载loader之后,再次new 缓存的时候数据也同步更新了.
 * 但是有些配置是被静态加载到了具体的实现中，这些参数将不能被重载.
 * @author zhangcq
 *
 */
public class JSystemConfigCache
{
	/**************
	 * 				#### 1.平台配置 ### ****************/
	//app名字.
	public String APP_NAME = getLoader().getString("app.name");
	
	//是否是测试环境.
	public boolean IS_TEST = getLoader().getBoolean("app.test");
	
	//通信报文字符集.
	public String APP_CHARSET = getLoader().getString("app.charset","utf-8");
	
	//通信报文最大值. 5M default.
	public int APP_MAX_PACKET = getLoader().getInt("app.max_packet",5242880);
	
	//缓存数据有效时间.
	public String APP_CACHE_TIME_OUT = getLoader().getString("app.cache_expires");
	
	/*********>>>>>>> 平台配置 END <<<<<<< *****************/
	
	/**************
	 * 				### 2.会话配置 ### ****************/
	//session类型. 200-HTTP 300-REDIS.
	public int SESSION_TYPE = getLoader().getInt("session.type",JSession.SESS_REDIS);
	
	//会话超时时间.
	public int SESSION_TIME_OUT = getLoader().getInt("session.expires", 900);
	
	//客户信息类
	public String SESSION_USER_CLAZZ = getLoader().getString("session.user.class");
	
	/********>>>>>>>> 会话配置 END <<<<<<< *****************/
	
	/**************
	 * 				### 3.安全配置 ### ****************/
	//监测是否开启反重功能.
	public boolean SAFE_REPEAT = getLoader().getBoolean("safe.repeat");
	
	//用于校验数据的key值.
	public String SAFE_SIGN_KEY = getLoader().getString("safe.sign.key");
	
	//公钥配置文件路径.
	public String SAFE_JKS_PATH = getLoader().getString("safe.jkspath");

	/********>>>>>>>> 安全配置 END <<<<<<< *****************/
	
	/**************
	 * 				### 4.Redis配置 ### ****************/
	//地址
	public String REDIS_SERVER = getLoader().getString("redis.server",true);
	
	//端口
	public int REDIS_PORT = getLoader().getInt("redis.port",true);
	
	//密码
	public String REDIS_PASSWORD = getLoader().getString("redis.password");
	
	//redis访问日志打印开关.
	public boolean REDIS_LOGON = getLoader().getBoolean("redis.logon");						
	
	//链接最大超时时间.(毫秒)
	public int REDIS_CONNECT_TIME_OUT =  getLoader().getInt("redis.connect.timeout",true);
	
	//阻塞最大等待时间.(毫秒)
	public int REDIS_MAX_WAIT_MILLIS = getLoader().getInt("redis.max.wait.millis",true);
	
	//最大链接数.
	public int REDIS_MAX_CONNECT = getLoader().getInt("redis.max.connect",true);
	
	//最小空闲数.
	public int REDIS_MIN_IDLE = getLoader().getInt("redis.min.idle",true);
	
	//集群服务地址和端口.
	public String REDIS_CLUSTER = getLoader().getString("redis.cluster");
	
	//集群重定向最大次数.
	public int REDIS_REDIRECT = getLoader().getInt("redis.redirect");
	
	/*********>>>>>>>> Redis配置 END <<<<<<< *****************/

	/**************
	 * 				### 5.异步任务配置 ### ****************/
	//初始线程数
	public int ASYN_CORE_POOL_SIZE = getLoader().getInt("asyntask.coretd",100);
	
	//队列 满后的最大线程数
	public int ASYN_MAX_POOL_SIZE = getLoader().getInt("asyntask.maxtd",1000);
	
	//队列最大数.
	public int ASYN_MAX_QUEUE_SIZE = getLoader().getInt("asyntask.maxque",8000);
	
	/*********>>>>>>>> 异步任务配置 END <<<<<<< *****************/
	
	/**************
	 * 				### 6.日志配置 ### ****************/
	//异常日志栈打印行数
	public int LOG_TRACE_CNT = getLoader().getInt("log.trace.cnt",0);
	
	//打印网络信息.
	public boolean LOG_PRINT_NET = getLoader().getBoolean("log.net.info");
	
	//打印网络读取等信息.
	public boolean LOG_PRINT_IO = getLoader().getBoolean("log.net.io");
	
	/*********>>>>>>>> 日志配置 END <<<<<<< *****************/
	
	
	/**************
	 * 				### 7.管理配置 ### ****************/
	//重载配置文件授权key.
	public String MGR_AUTHKEY = getLoader().getString("mgr.authkey");
	
	//配置版本号
	public String MGR_CONFIG_VERSION = getLoader().getString("mgr.config.version");
	
	/*********>>>>>>>> 管理配置  END <<<<<<< *****************/
	
	
	private JSystemConfigLoader getLoader()
	{
		return JConfigManager.getInstance().getSystemConfigLoader();
	}
	
	public Map<String,Object> convert2Map()
	{
		Map<String,Object> paraMap = new HashMap<String,Object>();
		
		Field[] fields = getClass().getDeclaredFields();
		
		for ( int i = 0; i < fields.length; ++i )
		{
			Field field = fields[i];
			
			//该方法不是说这个成员是否可以访问，而是说是否进行访问检查.
			//由于访问检查比较消耗性能 所以默认关闭的把.
//			JLog.log("toString called:%s",field.isAccessible());
//			if (field.isAccessible())		//false even for public field.
//			{
				try {
					Object value = field.get(this);
					if ( value == null )
					{
						value = "__NULL__";
					}
					
					paraMap.put(field.getName(), value);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//			}
		}
		
		return paraMap;
	
	}
	
	//TODO 打印所有参数.
	public String toString()
	{
		StringBuilder sbuilder = new StringBuilder();
		
		Field[] fields = getClass().getDeclaredFields();
		
		for ( int i = 0; i < fields.length; ++i )
		{
			Field field = fields[i];
			
			//该方法不是说这个成员是否可以访问，而是说是否进行访问检查.
			//由于访问检查比较消耗性能 所以默认关闭的把.
//			JLog.log("toString called:%s",field.isAccessible());
//			if (field.isAccessible())		//false even for public field.
//			{
				try {
					Object value = field.get(this);
					if ( value == null )
					{
						value = "__NULL__";
					}
					
					sbuilder.append(field.getName()).append(":").append(value.toString()).append("|");
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//			}
		}
		
		return sbuilder.toString();
	}
}
