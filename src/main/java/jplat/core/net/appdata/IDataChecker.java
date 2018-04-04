package jplat.core.net.appdata;

import jplat.core.trans.JAppContext;
import jplat.error.exception.JSystemException;

/**
 * 数据检查器 目前只有报文防重复等.
 * 作用：1. 防止报文重复提交。 2. 防止报文被截获后,反复提交。
 * 报文防重分为两类：一类是带用户状态的.这类防重就算被截获,在会话时间外重发也会无效.所以这一类一段时间内可以进行清理。
 * 					另外就是不带用户状态的,这类报文需要做到永久防重复,比如登录.
 * 另外，防重检测比价消耗资源，所以只有在需要的时候才检测.
 * @author zhangcq
 * @date Feb 10, 2017
 * @comment
 */
public interface IDataChecker
{
	/***
	 * 
	 * Oct 25, 20179:42:30 AM
	 * checkRepeat
	 * @param appCtx
	 * @param funcCode 功能代码号,按照功能进行拦截管理.
	 * @param userData 用户自定义数据,用于日志追踪.
	 * @return true - 检查通过.
	 * @throws JSystemException
	 */
	public boolean checkRepeat( JAppContext appCtx, String funcCode, String userData ) throws JSystemException;
	
	/**
	 * 检查反重功能是否打开.
	 * @author zhangcq
	 * @date Feb 10, 2017
	 * @comment 
	 * @param sysCode
	 * @return
	 */
	public boolean openCheck( JAppContext appCtx, String funcCoode ) throws JSystemException;
}
