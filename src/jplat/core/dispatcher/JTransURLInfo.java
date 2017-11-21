package jplat.core.dispatcher;

import java.lang.reflect.Method;

import org.springframework.context.ApplicationContext;

import jplat.base.constant.KPlatResponseCode;
import jplat.error.exception.JSystemException;
import jplat.error.exception.JTransException;
import jplat.tools.config.JAppConfig;
import jplat.tools.string.JStringUtil;

public class JTransURLInfo
{
	private String moduleCode;
	private String clazzName;
	private String methodName;
	
	public JTransURLInfo( String moduleCode_, String clazzName_, String methodName_ )
	{
		moduleCode = moduleCode_;
		clazzName = JStringUtil.bigFirstCase(clazzName_);
		methodName = methodName_;
	}
	
	/**
	 * 用于缓存查找.
	 * Oct 31, 201710:42:28 AM
	 * getMapKey
	 * @return
	 */
	public String getMapKey()
	{
		return new StringBuilder().append(moduleCode).append(clazzName).append(methodName).toString();
	}
	
	/**
	 * 用于视图映射.
	 * Oct 31, 201710:42:37 AM
	 * getViewKey
	 * @param retCode
	 * @param viewCode
	 * @return
	 */
	public String getViewKey( String retCode, String viewCode )
	{
		return new StringBuilder().append("mapview").append("/").append(moduleCode)
						.append("/").append(clazzName).append("_").append(methodName).append("_").append(retCode).toString();
	}
	
	public String findPackage() throws JSystemException
	{
		String packageName = JAppConfig.getString("mdp."+moduleCode);
		try {
			JStringUtil.assertNotBlank(packageName, "模块号错误.");
		} catch (JTransException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JSystemException(e.getErrCode(),e.getErrMsg());
		}
		
		return packageName;
	}
	
	public Class findClazz() throws JSystemException
	{
		String clzName = new StringBuilder().append(findPackage()).append(".").append(clazzName).append("Trans").toString();
		try {
			return Class.forName(clzName);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();			//生产不应该打印该日志.
			throw new JSystemException(KPlatResponseCode.CD_INPUT_UNAVAIABLE,"无请求对应地址404.");
		}
	}
	
	public Method findMethod() throws JSystemException
	{
		Class clazz = findClazz();
		Method mtds[] = clazz.getMethods();
		for ( int i = 0; i < mtds.length; ++i )
		{
			Method mtd = mtds[i];
			if ( methodName.equals(mtd.getName()))
			{
				return mtd;
			}
		}
		
		throw new JSystemException(KPlatResponseCode.CD_INPUT_UNAVAIABLE,"无请求对应地址405.");
	}
	
	public Class[] findParas() throws JSystemException
	{
		Method mtd = findMethod();
		return mtd.getParameterTypes();
	}
	
	public JTransInfo getActionInfo( ApplicationContext springCtx ) throws JSystemException
	{
		Object clz = springCtx.getBean(findClazz());
		
		Method mtd = findMethod();
		JTransInfo clzInfo = new JTransInfo(clz,mtd);
		clzInfo.initParaClass();

		return clzInfo;
	}
}
