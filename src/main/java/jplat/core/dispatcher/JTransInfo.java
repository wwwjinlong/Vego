package jplat.core.dispatcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jplat.base.constant.KPlatResponseCode;
import jplat.error.exception.JBaseException;
import jplat.error.exception.JSystemException;
import jplat.error.exception.JTransException;
import jplat.tools.config.JAppConfig;
import z.log.tracelog.JTraceLogUtils;

/**
 * 类型,方法,参数元信息.
 * 由于Class和Method都是全局共享对象，不同的JTransInfo中都是同一个Class的引用.
 * @author zhangcq
 */
public class JTransInfo
{
	private static Logger logger = LoggerFactory.getLogger(JTransInfo.class);
	
	public static final String REQ_MODEL = "ReqModel";
	public static final String RSP_MODEL = "RspModel";

	//用于统计调用情况的 建立淘汰策略.
	public int callCnt;
	public Date lastCallDate;
	
	public Object actionObj;
	public Method actionMethod;
	private Class reqClass;
	private Class rspClass;
	
	public JTransInfo( Object targetObj, Method targetMtd )
	{
		actionObj = targetObj;
		actionMethod = targetMtd;
	}
	
	/**
	 * JAppContext[,Req|Rsp]. 出于效率原因不在反射接口请求和返回,而由appCtx获取和设置.
	 * Oct 31, 201711:38:35 AM
	 * parseParaClass
	 * @return
	 * @throws JSystemException
	 */
	public boolean initParaClass() throws JSystemException
	{
		Class[] paraClz = actionMethod.getParameterTypes();
		if ( paraClz == null || paraClz.length == 0 || paraClz.length > 3 )
		{
			throw new JSystemException(KPlatResponseCode.CD_FRAMEWORK_ERR,KPlatResponseCode.MSG_FRAMEWORK_ERR);
		}
		
		//jump context by default.
		for ( int i = 1; i < paraClz.length; ++i )
		{
			Class clz = paraClz[i];
			if ( clz.getName().endsWith(REQ_MODEL) )
			{
				reqClass = clz;
				continue;
			}
			
			rspClass = clz;
		}
		
		return true;
	}
	
	public Class getReqParaClass()
	{
		return reqClass;
	}
	
	public Class getRspParaClass()
	{
		return rspClass;
	}
	
	public void invokeTrans( Object ...args ) throws JTransException
	{
		callCnt++;					//not thread safe but it does not matter,I think.
		lastCallDate = new Date();
		
		try
		{
			actionMethod.invoke(actionObj, args);
		}
		catch ( IllegalAccessException | IllegalArgumentException e )
		{
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error(JTraceLogUtils.getExceptionFullLog(e, JAppConfig.getConfigCache().LOG_TRACE_CNT, true));
			
			throw new JTransException(KPlatResponseCode.CD_FRAMEWORK_ERR,KPlatResponseCode.MSG_FRAMEWORK_ERR);
		}
		catch (InvocationTargetException e)
		{
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.error(JTraceLogUtils.getExceptionFullLog(e, JAppConfig.getConfigCache().LOG_TRACE_CNT, true));
			
			Throwable te = e.getTargetException();
			if ( te != null && ( te instanceof JTransException || te instanceof JSystemException ) )
			{
				JBaseException tbe = (JBaseException)te;
				throw new JTransException(tbe.getErrCode(),tbe.getErrMsg());
			}
			
			throw new JTransException(KPlatResponseCode.CD_FRAMEWORK_ERR,KPlatResponseCode.MSG_FRAMEWORK_ERR);
		}
	}
}
