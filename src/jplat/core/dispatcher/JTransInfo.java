package jplat.core.dispatcher;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import jplat.base.constant.KPlatResponseCode;
import jplat.error.exception.JSystemException;

/**
 * 类型,方法,参数元信息.
 * @author zhangcq
 *
 */
public class JTransInfo
{
	public static final String REQ_MODEL = "ReqModel";
	public static final String RSP_MODEL = "RspModel";
	
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
	
	public void invokeTrans( Object ...args ) throws JSystemException
	{
		try {
			actionMethod.invoke(this.actionObj, args);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JSystemException(KPlatResponseCode.CD_FRAMEWORK_ERR,KPlatResponseCode.MSG_FRAMEWORK_ERR);
		}
	}
}
