package jplat.tools.encrypt;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import jplat.tools.stream.JFileUtils;

public class RSAUtils
{
	public static int radix_16 = 16;
	public static int radix_10 = 10;
	
	/*************
	 * 			 公钥获取 ***************/
	
	//按照十六进制公钥获取
	public static RSAPublicKey getPubKey(String pubModules,String pubExp )
	{
		return buildRSAPublicKey(new BigInteger(pubModules, radix_16 ), new BigInteger(pubExp, radix_16 ));
	}
	
	//按照进制公钥获取
	public static RSAPublicKey getPubKey(String pubModules,String pubExp, int radix )
	{
		return buildRSAPublicKey(new BigInteger(pubModules, radix ), new BigInteger( pubExp, radix ));
	}
	
	//按照十进制公钥获取
	public static RSAPublicKey getPubKeyDeci(String pubModules,String pubExp )
	{
		return buildRSAPublicKey(new BigInteger(pubModules, radix_10 ), new BigInteger( pubExp, radix_10 ));
	}
	
	//按照模数和指数获得公钥.
	public static RSAPublicKey buildRSAPublicKey(BigInteger modulus, BigInteger publicExponent)
	{
		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, publicExponent);
			return (RSAPublicKey) kf.generatePublic(spec);
		}
		catch (Exception e)
		{
			throw new IllegalStateException(
					"cannot build public key by modulus and exponent", e);
		}
	}
	
	/**
	 * 根据cer文件获取公钥.
	 * @param cerfile
	 * @return
	 * @throws Exception
	 */
	public static RSAPublicKey getPubKey( String cerfile ) throws Exception
	{
		CertificateFactory cff = null;
		RSAPublicKey pk1 = null;
		cff = CertificateFactory.getInstance("X.509");
		InputStream in = new FileInputStream(cerfile);
		//			InputStream in = app.getBaseContext().getResources()
		//					.openRawResource(R.raw.zjrcbank); // 证书文件
		Certificate cf = cff.generateCertificate(in);
		pk1 = (RSAPublicKey)cf.getPublicKey(); // 得到证书文件携带的公钥

		in.close();

		return pk1;
	}
	
	/**
	 * 通过JKS文件读取公钥信息.
	 * @param jskFile
	 * @param storePass
	 * @param keyAlias
	 * @return
	 */
	public static RSAPublicKey getPubkey( String jskFile, String storePass, String keyAlias )
	{
		try
		{
			KeyStore keyStore = KeyStore.getInstance("JKS");

			InputStream fin =  JFileUtils.loadFileStream(jskFile);
			keyStore.load(fin, storePass.toCharArray());
			fin.close();
			Certificate cert = keyStore.getCertificate(keyAlias);
			RSAPublicKey pubkey = (RSAPublicKey)cert.getPublicKey();

			return pubkey;
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	/*************
	 * 			私钥获取 *************/
	//按照十六进制私钥获取
	public static RSAPrivateKey getPriKey(String pubModules, String priExp ) {
		
		return buildRSAPrivateKey(new BigInteger(pubModules, radix_16 ), new BigInteger( priExp, radix_16 ));
	}
	
	//按照进制获取私钥
	public static RSAPrivateKey getPriKey(String pubModules, String priExp, int radix ) {
		
		return buildRSAPrivateKey(new BigInteger(pubModules, radix ), new BigInteger( priExp, radix ));
	}
	
	//按照十进制私钥获取
	public static RSAPrivateKey getPriKeyDeci(String pubModules, String priExp ) {
		
		return buildRSAPrivateKey(new BigInteger(pubModules, radix_10 ), new BigInteger( priExp, radix_10 ));
	}

	//按照模数获取私钥.
	public static RSAPrivateKey buildRSAPrivateKey(BigInteger modulus, BigInteger publicExponent) {
		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			RSAPrivateKeySpec spec = new RSAPrivateKeySpec(modulus,
					publicExponent);
			return  (RSAPrivateKey)kf.generatePrivate(spec);
		} catch (Exception e) {
			throw new IllegalStateException(
					"cannot build private key by modulus and exponent", e);
		}
	}
	
	/***********
	 * 通过文件获得私钥
	 * @throws FileNotFoundException 
	 * @throws KeyStoreException 
	 */
	public static RSAPrivateKey getPriKey( String jksfile, String storepwd, String keyalias, String keypwd ) throws Exception
	{
		char[] storep = storepwd.toCharArray(); // 证书库密码
		char[] keyp = keypwd.toCharArray(); // 证书密码
		RSAPrivateKey pk2 = null;
		
		InputStream fis2 = JFileUtils.loadFileStream(jksfile);
		KeyStore ks = KeyStore.getInstance("JKS"); // 加载证书库
		ks.load(fis2, storep );
		pk2 = (RSAPrivateKey) ks.getKey(keyalias, keyp ); // 获取证书私钥
		fis2.close();

		return pk2;
	}
	
	/**
	 * 根据encoded的字节获取私钥.
	 * @param keyBytes
	 * @return
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(byte[] keyBytes) throws Exception { 
		PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePrivate(spec);
	}
}
