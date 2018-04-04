package jplat.core.net.appdata;

import jplat.core.trans.JAppContext;
import jplat.error.exception.JSystemException;

/**
 * 数据加密器
 * @author zhangcq
 * @date Dec 15, 2016
 * @comment
 */
public interface IDataGuard
{
	//加密
	public byte[] encryptData( JAppContext appCtx, byte[] endData ) throws JSystemException;
	
	//解密
	public byte[] dencryptData( JAppContext appCtx, byte[] plainData ) throws JSystemException;
}
