package jplat.tools.encrypt;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import jplat.tools.coder.Base64Coder;

/***
 * 使用X509的编码密钥进行RSA加密,具体见RSACryptUtil
 * @author zhangcq
 *
 */
public class EncryptBase
{
	/**
	 * RSA加密
	 * 
	 * @param plain 明文
	 * @param keyStr 公钥
	 * @return
	 */
	public static byte[] rsaEncrypt(String plain, String keyStr) throws NoSuchAlgorithmException,
	NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
	InvalidKeySpecException, UnsupportedEncodingException {

		KeyFactory kf = KeyFactory.getInstance("RSA");
		Key publicKey = kf.generatePublic(new X509EncodedKeySpec(decode(keyStr)));

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		return cipher.doFinal(plain.getBytes("UTF-8"));
	}

	/**
	 * RSA解密
	 * 
	 * @param plain 密文
	 * @param keyStr 私钥
	 * @return
	 */
	public static byte[] rsaDecrypt(String result, String keyStr) throws NoSuchAlgorithmException,
	NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException,
	InvalidKeySpecException, UnsupportedEncodingException {

		KeyFactory kf = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(decode(keyStr)));

		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return cipher.doFinal(decode(result));
	}

	/**
	 * AES加密
	 * 
	 * @param plain 明文
	 * @param keyStr 密钥
	 * @return
	 */
	public static String aesEncrypt(String plain, String keyStr) throws Exception {
		Key key = new SecretKeySpec(keyStr.getBytes("UTF-8"), "AES");
		Cipher c = Cipher.getInstance("AES/GCM/NoPadding");//not supported in Java7
		GCMParameterSpec spec = new GCMParameterSpec(128, key.getEncoded());
		c.init(Cipher.ENCRYPT_MODE, key, spec);

		byte[] encVal = c.doFinal(plain.getBytes("UTF-8"));
		return encode(encVal);
	}

	/**
	 * AES解密
	 * 
	 * @param encryptedData 密文
	 * @param keyStr 密钥
	 * @return
	 */
	public static String aesDecrypt(String encryptedData, String keyStr) throws Exception {
		Key key = new SecretKeySpec(keyStr.getBytes(), "AES");
		Cipher c = Cipher.getInstance("AES/GCM/NoPadding");
		GCMParameterSpec spec = new GCMParameterSpec(128, key.getEncoded());
		c.init(Cipher.DECRYPT_MODE, key, spec);

		byte[] decValue = c.doFinal(decode(encryptedData));
		return new String(decValue, "UTF-8");
	}

	public static String encode(byte[] data) {
		return Base64Coder.toBase64String(data);
	}

	public static byte[] decode(String data) {
		return Base64Coder.fromBase64String(data);
	}
	
	/**** code below is for test *****/
	
	public static void main(String[] args)
	{
		testRSA();
	}
	
	private static void testRSA()
	{
		String pubKey = "AAAAB3NzaC1yc2EAAAADAQABAAABAQDwUAi3g6OQyrvemY6gRZjMt+iglIa0fGxN4ATDnNL52C/0HjzeUgwhb2X294iMGeM1J6v0M12riUps9yDlo3mqbz4VbI3RMYUyA8b1g+Ggn00kCQ0MShQ2us1haJvQLDvtMi3/NmqrewYkNo08gDVDtCw9TuLhaL4Nl3vVvy1G/UNmPEK5uAur87R4FUpw8+bX6qRGAIv7dRifzDRABVDm1sergYgTRH9Bw+pE8IzTgG4m1Vl8AOoBpwrX7Xj8Sn80mAklTQAt/wBKIB9P8pl3/HPYYIAchO8By9y8fLgJlnMPST4YTh7i4EnobArqnOvJ08fMDuz92xfexJd8/la3";
		String priKey = "";
		
		try {
			byte[] encData = rsaEncrypt( "hello RSA", pubKey );
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException | InvalidKeySpecException
				| UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void testAES()
	{
		String aesKey = "0123456789123456";
		try {
			String aesEnc = aesEncrypt( "hello aes,hello aes,hello aes,", aesKey );
			String aesPlain = aesDecrypt(aesEnc, aesKey);
			
			log(aesPlain);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void log( String msg )
	{
		System.out.println(msg);
	}
}
