package jplat.tools.string;

import jplat.base.constant.KPlatResponseCode;
import jplat.error.exception.JTransException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JStringUtil extends StringUtil
{
	private static Logger logger = LogManager.getLogger(JStringUtil.class);
	
	public static String concat( String args[], String join )
	{
		StringBuilder strBuff = new StringBuilder();
		for ( int i = 0; i < args.length; ++i )
		{
			strBuff.append(args[i]);
			if ( i != args.length -1 )
			{
				strBuff.append(join);
			}
		}
		
		return strBuff.toString();
	}
	
	/**
	 * 断言值不为空.
	 * @param value
	 * @param errMsg
	 * @throws JTransException
	 */
	public static void assertNotBlank( String value, String errMsg ) throws JTransException
	{
		if ( isEmpty( value ) )
		{
			throw new JTransException(KPlatResponseCode.CD_INPUT_UNAVAIABLE,errMsg);
		}
	}
	
	/**
	 * 断言两个值相等.
	 * @param value1
	 * @param value2
	 * @param errMsg
	 * @throws JTransException
	 */
	public static void assertEqual( String value1, String value2, String errMsg ) throws JTransException
	{
		assertNotBlank(value1,errMsg);
		if ( !value1.equals(value2) )
		{
			logger.error(String.format("value1[%s] not equal to value2[%s]", value1, value2 ));
			throw new JTransException(KPlatResponseCode.CD_INPUT_UNAVAIABLE,errMsg);
		}
	}
}
