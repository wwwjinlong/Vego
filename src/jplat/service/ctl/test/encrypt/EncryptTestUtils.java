package jplat.service.ctl.test.encrypt;

import java.io.UnsupportedEncodingException;

import z.log.tracelog.XLog;
import jplat.tools.coder.Base64Coder;
import jplat.tools.encrypt.AESTools;
import jplat.tools.encrypt.RSATools;
import jplat.tools.string.JRandomUtil;

/**
 * 加密构造类.
 * @author zhangcq
 * @date May 8, 2017
 * @comment
 */
public class EncryptTestUtils
{
	public static void main( String args[] )
	{
//		RSATools.getInstance();
		
		testAES();
		
//		testRSA();
	}
	
	public static void testAES()
	{

		String plainText = "S:101111";
		String aesKey = "2JUsV1qnscFvnkPM";
		
		String cipher = showAESCipher(plainText,aesKey);
		XLog.log("STAFF cihper:%s", cipher);
		
		String encData = "CJUCvQ8wsBCwD9UXtU+RSNoqxlc/ssLFVnQIlOqQy7ZJz4JwG67Dy0ePYVxmGx0bEnQA5c2Y5CEli6rm1AKkz1w62m57PXMJpEER2G/vpxknlbNH1QbHgwauzhqqUGpEMY8ezmqsMtZG7Q2lDYHZfCdxP8Fw0+wmxiXkIsu52vng9wiVPyxlptE1k6w0AbS05sJnTFO0FociyKN24oL4Hkh5j+kbKJ/1iEiyLTnfYW1QhfIeYV6kXRX+t2h48T/K";
		doAESDecrypt(encData,aesKey);
	
	}
	
	public static void testRSA()
	{
		String reqMsg = "{\"head\":{},\"body\":{\"couponPwd\":\"今天星期1,RSA\"}}";
		
		//生成aes密钥
		String aesKey = JRandomUtil.getRandomSequence(16);
		XLog.log("-----------AESKey-plain=[ %s ]", aesKey);
		
		//加密aes密钥.
		String aes64Key = showRSACipher(aesKey);
		XLog.log("AESKey-RSA64:%s", aes64Key);
		
		//加密明文
		String cipher64 = showAESCipher(reqMsg,aesKey);
		
		//拼接密文.
		XLog.log("----------RSACipher:[ %s#%s ]", aes64Key,cipher64);
		
		/****************** 解密 ********************/
		//返回的base64密文.
		String base64String = "27Vc0ALzn2fYpSv4dn5tAt/YYoOgJ+RJ6lRt77qalb+QEdfv+c6Fd2gvDqGkfCTFbt8W619ATZbvQ6xsAYK3oK46ZrTbXhFyB3s8LD+gN3o+bm7rp7n7udq8aBWG4ckFYd/XvGPlRBxo3lyB3NaWR6o+jek3fKp9DvsjwTp76v6OpSMRfLEg+nU5yFMd/dyLE/4I1af0XnIE9HiL5WwDDLndWtT+8qY0sVHgYTikG9k=";
		
		String rAesKey= "srwi1GzXR_Ao4o7k";
		doAESDecrypt(base64String,rAesKey);
	}
	
	private static String showAESCipher( String plainText, String aesKey )
	{
		try
		{
			String base64Data = Base64Coder.toBase64String(AESTools.encrypt(plainText.getBytes("utf-8"), aesKey));
			
			XLog.log("AES64Data:%s", base64Data);
			
			return base64Data;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	private static void doAESDecrypt( String encdata, String keyStr )
	{
		try {
			
			byte[] encBytes = Base64Coder.fromBase64String(encdata);
			byte[] plainData = AESTools.decrypt(encBytes, keyStr);
			
			XLog.log("AES-PLAIN:%s", new String(plainData,"utf-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static String showRSACipher( String plainText )
	{
		try
		{
			byte[] encData = RSATools.getInstance().encryptData(plainText.getBytes("utf-8"));
			String base64Data = Base64Coder.toBase64String(encData);
			return base64Data;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
	private static String doRSAdecrypt( String base64Data )
	{
		byte[] encData = Base64Coder.fromBase64String(base64Data);
		
		try {
			String plainText = RSATools.getInstance().dencryptData(encData,"utf-8");
			
			XLog.log("RSA_plainText:%s", plainText);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return "";
	}
	
}
