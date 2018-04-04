package jplat.tools.encrypt;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 1. RSA通过cer文件加密
 * 2. RSA通过指数模数加密.
 * @author zhangcq
 *
 */
public class RSAEncer
{
	//加密算法定义.
	public static String AL_NOPADDING = "RSA/ECB/NoPadding";
	public static int BLOCKSIZE_NOPADDING = 128;
	
	public static String AL_PKCSPADDING = "RSA/ECB/PKCS1Padding";
	public static int BLOCKSIZE_PKCSPADDING = 110;						//MAX = 117;
	
	/****************** 加密 ************************/
	
	/*@data 加密数据
	 *@al 加密算法
	 *@blocksize 分组大小 必须小于128.
	 *@module 模数
	 *@exp 指数 
	 */
	public static byte[] encryptData(byte[] data, String al, int blocksize, RSAPublicKey pubKey ) throws Exception
	{
		//获得加密类
		Cipher cipher = Cipher.getInstance(al);
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);

		//进行分组加密
		int fix = blocksize;
		byte[] cipherdata = null,tmp = null;
		for (int i = 0; i * fix < data.length; i++ )
		{
			// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
			tmp = cipher.doFinal(ArrayUtils.subarray(data, i * fix, Math.min(data.length, (i + 1) * fix )));
			cipherdata = ArrayUtils.addAll(cipherdata, tmp);
		}

		return cipherdata;
	}
	
	/***
	 * 解密主函数.
	 * @param data
	 * @param al
	 * @param priKey
	 * @return
	 * @throws Exception
	 */
	public static byte[] dencryptData(byte[] data, String al, RSAPrivateKey priKey ) throws Exception
	{
		Cipher cipher = Cipher.getInstance(al);
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		
		/***
		 * 1024bits密钥加密出来的都是128byte的密文.
		 * 所以和加密的分组是不同的。无论加密分组大小是1或者128字节，
		 * 加密密文都是128字节
		 */
		int fix = 128;
		byte[] plain  = null, tmp = null;

		for (int i = 0; i * fix < data.length; i++) {
			// 注意要使用2的倍数，否则会出现加密后的内容再解密时为乱码
			tmp = cipher.doFinal(ArrayUtils.subarray(data, i * fix, Math.min(data.length, (i + 1) * fix)));
			plain = ArrayUtils.addAll(plain, tmp);
		}

		return plain;
	}
}
