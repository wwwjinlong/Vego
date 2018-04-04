package z.log.tracelog;

public class JLog
{
	//域客户端通讯相关的日志.
	public static String CONN_MARK = "__APPCONN:";
	
	//与后端系统的报文.
	public static String TRANS_MARK = "__TRANS_INFO:";
	
	public static String SYS_INIT = "__SYS_INIT__:";
	
	public static void log( String fmt, Object...args )
	{
		String curr = Thread.currentThread().getName();
		System.out.println( "["+curr+"]"+String.format(fmt, args));
	}
	
	//平台级别初始化日志.
	public static void loginit( String fmt, String...args )
	{
		String curr = Thread.currentThread().getName();
		System.out.println( "["+curr+"]-__SYS_INIT__:"+String.format(fmt, args));
	}
	
	//客户级别初始化日志.
	public static void logcinit( String fmt, Object...args )
	{
		String curr = Thread.currentThread().getName();
		System.out.println( String.format("[%s]__CUST_INIT__:"+fmt,args));
	}
	
	/**
	 * 打印错误日志.
	 * @param msg
	 */
	public static void logerr( String msg )
	{
		System.err.println(msg);
	}
	
	public static void main(String args[])
	{
		
	}

}
