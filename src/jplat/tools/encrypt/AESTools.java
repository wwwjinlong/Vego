package jplat.tools.encrypt;

import java.io.UnsupportedEncodingException;

import jplat.base.constant.KPlatResponseCode;
import jplat.error.exception.JSystemException;
import jplat.tools.coder.Base64Coder;
import jplat.tools.string.StringUtil;

public class AESTools
{
	/**
	 * 加密明文字符串.
	 * @author zhangcq
	 * @date Feb 9, 2017
	 * @comment 
	 * @param plainText
	 * @param keyStr
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws Exception
	 */
	public static byte[] encrypt( String plainText, String keyStr, String charset ) throws UnsupportedEncodingException, Exception
	{
		return encrypt(plainText.getBytes(charset),keyStr);
	}
		/**
	 * 
	 * @param plaintext
	 * @param keystr
	 * @return base64编码后的密文.
	 * @throws Exception
	 */
	public static byte[] encrypt( byte[] plainData, String keyStr ) throws Exception
	{
		if( StringUtil.isEmpty(keyStr) )
		{
			throw new Exception("ERROR:AES key is empty.");
		}
		
		try
		{
			return AESEncer.encrypt( plainData, keyStr,AESEncer.DEFAULT_CIPHER_ALGORITHM );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}
	
	/**
	 * 解密获得字符串.
	 * @author zhangcq
	 * @date Feb 10, 2017
	 * @comment 
	 * @param encData
	 * @param keyStr
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	public static String decrypt( byte[] encData, String keyStr, String charset ) throws Exception
	{
		return new String( decrypt( encData, keyStr), charset );
	}

	/**
	 * AES解密函数.
	 * @param base64data
	 * @param keyStr
	 * @return utf8解码后的字符串.
	 * @throws Exception
	 */
	public static byte[] decrypt( byte[] encData, String keyStr ) throws Exception
	{
		if ( StringUtil.isEmpty(keyStr) )
		{
			throw new JSystemException(KPlatResponseCode.CD_PARA_ERROR,"AES-key is empty.");
		}

		// 交易报文解密
		try
		{
			return AESEncer.decrypt( encData, keyStr, AESEncer.DEFAULT_CIPHER_ALGORITHM );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	public static void main(String args[] ) throws Exception
	{
		String text = "有中文hello AES.hello AES.hello AES.hello AES.hello AES.hello AES.hello AES.hello AES.hello AES.hello AES.";
		String keyStr = "0123456789123456";

		byte[] endata = encrypt(text.getBytes("utf-8"),keyStr);

		System.out.println("R1::"+text);
	}
}
