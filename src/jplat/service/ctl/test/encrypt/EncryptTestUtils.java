package jplat.service.ctl.test.encrypt;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import jplat.tools.coder.Base64Coder;
import jplat.tools.encrypt.AESTools;
import jplat.tools.encrypt.RSATools;
import jplat.tools.string.JRandomUtil;
import z.log.tracelog.XLog;

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
		
//		testAES();
		
		testRSA();
	}
	
	public static void testMap()
	{
		Map<String,String> map = new HashMap<String,String>();
		Field[] fds = map.getClass().getDeclaredFields();
		for ( int i = 0; i < fds.length; ++i )
		{
			XLog.log("%d:%s", i,fds[i].getName());
		}
	}
	
	public static void testAES()
	{

		String plainText = "headS:101111";
		String aesKey = "HrO96bda7XHNOKHx";
		
		String cipher = showAESCipher(plainText,aesKey);
		XLog.log("STAFF cihper:[%s]", cipher);
		
		String encData = "wgIcg7BspAg2MoXYjcmoicF838wGTzEvKDH5TpN0YS3NgfHMaCaJ5w40hZj7EsJig1d3VyM0QTpbn3YwCtrt2sV14iuvZjpGhOIiZZhBiLE/HBvxF1eVKNcffxJwnC4fc/je8xh80+/TozCPkmTzV+DDRgJCZdCOZkdKG6UCy9OgO2zyzwJSChkI9tp+snlvn/gOfj+Aa9x2Nl8RAamUWhYBRk8CTczLeMitZreICDtgPnCdIsSIFmqdvulq1U5o";
		doAESDecrypt(encData,aesKey);
	
	}
	
	public static void testRSA()
	{
		String reqMsg = getPlainBody("测试一下RSA");
		
		//生成aes密钥
		String aesKey = JRandomUtil.getRandomSequence(16);
		XLog.log("-----------AESKey-plain=[ %s ]", aesKey);
		
		//加密aes密钥.
		String aes64Key = showRSACipher(aesKey);
		XLog.log("AESKey-RSA64:%s", aes64Key);
		
		//加密明文
		String cipher64 = showAESCipher(reqMsg,aesKey);
		
		//拼接密文.
		XLog.log("----------RSACipher:[%s#%s]", aes64Key,cipher64);
		
		/****************** 解密 ********************/
		//返回的base64密文.
		String base64String = "27Vc0ALzn2fYpSv4dn5tAt/YYoOgJ+RJ6lRt77qalb+QEdfv+c6Fd2gvDqGkfCTFbt8W619ATZbvQ6xsAYK3oK46ZrTbXhFyB3s8LD+gN3o+bm7rp7n7udq8aBWG4ckFYd/XvGPlRBxo3lyB3NaWR6o+jek3fKp9DvsjwTp76v6OpSMRfLEg+nU5yFMd/dyLE/4I1af0XnIE9HiL5WwDDLndWtT+8qY0sVHgYTikG9k=";
		
		String rAesKey= "srwi1GzXR_Ao4o7k";
		doAESDecrypt(base64String,rAesKey);
	}
	
	private static String showAESCipher( String plainText, String aesKey )
	{
		plainText = getPlainBody(plainText);
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
	
	private static String getPlainBody( String plainText )
	{
		return String.format("{\"head\":{},\"body\":{\"text\":\"%s\",\"note\":\"this is encrypt test.\"}}", plainText);
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
