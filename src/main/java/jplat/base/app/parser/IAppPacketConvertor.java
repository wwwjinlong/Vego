package jplat.base.app.parser;

import jplat.core.trans.JAppContext;
import jplat.error.exception.JSystemException;

public interface IAppPacketConvertor
{
	//app请求解包
	public boolean doAppParse( JAppContext context ) throws JSystemException;
	
	//获取model.
//	public <T> T doAppParse( JAppContext context, Class<T> klass ) throws JSystemException;
	
	//app请求组包
	public boolean doAppPack( JAppContext context ) throws JSystemException;
}
