package jplat.tools.encrypt;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Properties;

import jplat.base.constant.KPlatResponseCode;
import jplat.tools.config.JConfigManager;
import jplat.tools.stream.JFileUtils;
import jplat.tools.string.StringUtil;
import z.log.tracelog.XLog;

public class RSATools
{
	/*************** 证书私钥 bankofchangsha *******************/
	private static String RSA_JKS_PATH = "rsa.jkspath"; //密钥配置文件.

	/********* 密钥信息 ************/
	private BigInteger module;		//密码模数（即大数的乘积)
	private BigInteger pubexp;		//公钥指数
	private BigInteger priexp;		//私钥指数.

	private boolean support = false;

	private RSATools()
	{
		long startTime = new Date().getTime();
		initSec();
		long endTime = new Date().getTime();
		XLog.loginit("RSATools init,time-spent="+(endTime - startTime));
	}

	private static final class SingleHolder
	{
		private static RSATools rsatool = new RSATools();
	}

	public static RSATools getInstance()
	{
		return SingleHolder.rsatool;
	}
	
	/***
	 * 解密数据,并返回字符串.
	 * @author zhangcq
	 * @date Feb 9, 2017
	 * @comment 
	 * @param encdata
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public String dencryptData( byte[] encdata, String charset ) throws Exception
	{
		return new String(dencryptData(encdata),charset);
	}

	/****
	 * 解密数据.
	 * @author zhangcq
	 * @date Feb 9, 2017
	 * @comment 
	 * @param encdata
	 * @return
	 * @throws Exception
	 */
	public byte[] dencryptData( byte[] encdata ) throws Exception
	{
		return RSAEncer.dencryptData( encdata,RSAEncer.AL_PKCSPADDING,RSAUtils.buildRSAPrivateKey(module, priexp));
	}

	/**
	 * 加密明文数据.
	 * @param base64data
	 * @return
	 * @throws Exception
	 */
	public byte[] encryptData( byte[] plaindata ) throws Exception
	{

		return RSAEncer.encryptData( plaindata,
				RSAEncer.AL_PKCSPADDING,
				RSAEncer.BLOCKSIZE_PKCSPADDING,
				RSAUtils.buildRSAPublicKey(module, pubexp) );
	}

	private void initSec()
	{
		//加载加密配置文件.
		String encFile = JConfigManager.getInstance().getSystemConfig().getString(RSA_JKS_PATH);
		if ( StringUtil.isEmpty(encFile) )
		{
			throw new RuntimeException(KPlatResponseCode.CD_CONF_ERROR+":value of "+RSA_JKS_PATH+" is not found, init for RSA fail.");
		}

		Properties encPro = new Properties();
		InputStream inStream = null;
		try {
			inStream = JFileUtils.loadFileStream(encFile);
			encPro.load( inStream );
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if( inStream != null )
				try {
					inStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

		//从JSK文件加载密钥信息.
		String loadType = encPro.getProperty("loadType");
		if ( "FILE".equals(loadType) )	//从文件中加载.
		{
			String jksFile = encPro.getProperty("jksFile").trim();
			String storePass = encPro.getProperty("storePassword").trim();
			String keyAlias = encPro.getProperty("keyAlias").trim();
			String keyPass = encPro.getProperty("keyPassword").trim();
			try
			{
				RSAPrivateKey priKey = RSAUtils.getPriKey( jksFile, storePass, keyAlias, keyPass );
				RSAPublicKey pubKey = RSAUtils.getPubkey(jksFile, storePass, keyAlias);

				XLog.loginit("RSATools.jksFile="+jksFile);

				module = priKey.getModulus();
				priexp = priKey.getPrivateExponent();
				pubexp = pubKey.getPublicExponent();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		support = true;
	}

	public static void main(String args[]) throws Exception
	{/*
//		String pt = "12345678901234567890123456789012";
		String pt = "1234567890123456";
		String endStr = RSATools.getInstance().encryptData(pt);
		log("enc:"+endStr);
		String plainText = new String( RSATools.getInstance().dencryptData1(endStr), "utf-8");
		log("plain:"+plainText);
	 */}

	public static void log( String lgmsg )
	{
		System.out.println(lgmsg);
	}
}
