package jplat.core.net.appdata;

import jplat.core.trans.JAppContext;
import jplat.error.exception.JSystemException;

/**
 * App读取器.
 * @author zhangcq
 * @date Dec 15, 2016
 * @comment
 */
public interface IAppDataConnector
{
	public String readInputString( JAppContext context, String charset ) throws  JSystemException;
	
	public byte[] readInputBytes( JAppContext context ) throws JSystemException;
	
	public int writeOutputString( JAppContext context, String dataString, String charset ) throws JSystemException;
	
	public int writeOutputBytes( JAppContext context, byte[] dataByte ) throws JSystemException;
}
