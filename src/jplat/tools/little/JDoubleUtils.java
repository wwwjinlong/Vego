package jplat.tools.little;

import z.log.tracelog.XLog;

public class JDoubleUtils
{
	public static boolean isEqual( double d1, double d2 )
	{
		if ( d1 - d2 < 0.0001 )
		{
			return true;
		}
		
		return false;
	}
	
	public static String format( double d1, int precision )
	{
		return String.format("%."+precision+"f", d1);
	}
	
	public static void main( String args[] )
	{
		double rate = 0.0035;
		XLog.log(format(rate*100*360,4));
		
		if ( isEqual((rate/100)*100,rate ) )
		{
			XLog.log("isEqual ---------true");
		}
		else
		{
			XLog.log("isEqual ---------false");
		}
		
		if ( (rate/100)*100 == rate )
		{
			XLog.log("== ---------true");
		}
		else
		{
			XLog.log("== ---------false");
		}
	}
}
