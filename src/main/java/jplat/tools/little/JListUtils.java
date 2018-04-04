package jplat.tools.little;

import java.util.List;

import jplat.base.constant.KPlatResponseCode;
import jplat.error.exception.JTransException;


public class JListUtils
{
	public static void assertNotEmpty( List list, String errMsg ) throws JTransException
	{
		if ( list == null || list.size() == 0 )
		{
			throw new JTransException(KPlatResponseCode.CD_INPUT_UNAVAIABLE,errMsg);
		}
	}
	
	public static boolean isEmpty( List list )
	{
		if ( list == null || list.size() == 0 )
		{
			return true;
		}
		
		return false;
	}
}
