package jplat.core.net.appdata.encrypt;

import java.io.UnsupportedEncodingException;

import jplat.base.constant.KPlatResponseCode;
import jplat.core.net.appdata.IDataGuard;
import jplat.core.trans.JAppConnectInfo;
import jplat.core.trans.JAppContext;
import jplat.error.exception.JSystemException;
import jplat.tools.coder.Base64Coder;
import jplat.tools.config.JAppConfig;
import jplat.tools.encrypt.AESTools;
import jplat.tools.encrypt.RSATools;
import jplat.tools.string.StringUtil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 基本加密器.
 * 包括对称密钥和非对称密钥等.
 * @author zhangcq
 * @date Dec 15, 2016
 * @comment
 */
public class JAppRADataGuard implements IDataGuard
{
	private Logger logger = LogManager.getLogger(JAppRADataGuard.class);
	
	/****
	 * appCtx 应用上下文.
	 * data dataonly.
	 * @throws JSystemException 
	 */
	@Override
	public byte[] dencryptData( JAppContext appCtx, byte[] rawData ) throws JSystemException
	{
		String encType = appCtx.getConnInfo().getEncType();
		if( StringUtil.isEmpty(encType)
				|| JAppConnectInfo.V_ENC_PLAIN.equals(encType) )
		{
			return rawData;
		}
		
		//进行数据校验.
		String encData=null;
		try {
			encData = new String(rawData,JAppConfig.PACK_CHARSET);
			logger.debug("FROM_APP_RAW:["+encData+"]");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JSystemException(KPlatResponseCode.CD_INPUT_ERROR,KPlatResponseCode.MSG_INPUT_ERROR);
		}
		
		//AES使用协商密钥进行解密.
		if ( JAppConnectInfo.V_ENC_AES.equals(encType) )
		{
			return dencryptDataAES(appCtx,encData);
		}
		//RSA
		else if( JAppConnectInfo.V_ENC_RSA.equals(encType) )
		{
			return dencryptDataRSA( appCtx, encData );
		}
		else
		{
			throw new JSystemException(KPlatResponseCode.CD_INPUT_ERROR,KPlatResponseCode.MSG_INPUT_ERROR+":sec");
		}
	}

	/**
	 * 加密数据之所以是String类型,
	 * 肯定是因为做了编码.这里默认使用base64
	 * 加密格式:BASE64(RSA(AES))#BASE64(AES(encJson))
	 */
	@Override
	public byte[] encryptData( JAppContext appCtx,  byte[] plainData ) throws JSystemException
	{
		JAppConnectInfo connInfo = appCtx.getConnInfo();
		
		String encType = connInfo.getEncType();
		if( StringUtil.isEmpty(encType)
				|| JAppConnectInfo.V_ENC_PLAIN.equals(encType) )
		{
			return plainData;
		}
		
		//AES使用协商密钥进行解密.
		if ( JAppConnectInfo.V_ENC_AES.equals(encType)
				|| JAppConnectInfo.V_ENC_RSA.equals(encType) )
		{
			byte[] encData = encryptDataAES(appCtx,plainData);
			
			String encStr = Base64Coder.toBase64String(encData);
			
			return encStr.getBytes();
		}
		else
		{
			throw new JSystemException(KPlatResponseCode.CD_INPUT_ERROR,KPlatResponseCode.MSG_INPUT_ERROR+":e");
		}
	}
	
	/**
	 * 加密格式:BASE64(AES(encJson))
	 * @author zhangcq
	 * @date Feb 10, 2017
	 * @comment 
	 * @param appCtx
	 * @param base64data
	 * @return
	 * @throws JSystemException
	 */
	private byte[] dencryptDataAES( JAppContext appCtx, String base64data ) throws JSystemException
	{
		try
		{
			return AESTools.decrypt(Base64Coder.fromBase64String(base64data), appCtx.getConnInfo().getEncKey());
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JSystemException(KPlatResponseCode.CD_AES_ENC_FAIL,KPlatResponseCode.MSG_AES_ENC_FAIL);
		}
	}

	/****
	 * 加密格式:BASE64(RSA(AES))#BASE64(AES(encJson))
	 * @author zhangcq
	 * @date Feb 10, 2017
	 * @comment 
	 * @param appCtx
	 * @param base64data
	 * @return
	 * @throws JSystemException
	 */
	private byte[] dencryptDataRSA( JAppContext appCtx, String base64data ) throws JSystemException
	{
		String datas[] = base64data.split("#");
		
		String a64 = datas[0];
		String d64 = datas[1];
		
		try
		{
			//获取aes密钥.
			String aesKey = RSATools.getInstance().dencryptData(Base64Coder.fromBase64String(a64),JAppConfig.PACK_CHARSET);
			
			//save the aes key to build response.
			appCtx.getConnInfo().setEncKey(aesKey);
			
			//使用AES密钥机密原文数据.
			return AESTools.decrypt(Base64Coder.fromBase64String(d64), aesKey);
		}
		catch ( Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JSystemException(KPlatResponseCode.CD_RSA_ENC_FAIL,KPlatResponseCode.MSG_RSA_ENC_FAIL);
		}
		
//		return null;
	}
	
	/**
	 * 加密明文数据.
	 * @author zhangcq
	 * @date Feb 10, 2017
	 * @comment 
	 * @param appCtx
	 * @param plainData
	 * @return
	 * @throws JSystemException
	 */
	private byte[] encryptDataAES( JAppContext appCtx, byte[] plainData ) throws JSystemException
	{
		try
		{
			return AESTools.encrypt(plainData, appCtx.getConnInfo().getEncKey());
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new JSystemException(KPlatResponseCode.CD_AES_ENC_FAIL,KPlatResponseCode.MSG_AES_ENC_FAIL);
		}
	}	
}
