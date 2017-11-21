
package jplat.tools.coder;

import org.apache.commons.codec.binary.Base64;

public class Base64Coder
{
	private static String hexCharStr = "0123456789ABCDEF";
	
	//encode raw bytes to base64 String;
	public static String toBase64String( byte[] data )
	{
		return Base64.encodeBase64String(data);
	}
	
	//decode base64 String to raw bytes.
	public static byte[] fromBase64String( String base64 )
	{
		return Base64.decodeBase64(base64);
	}
	
	/**
	 * �?6进制明文转换成byte[]
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;

	}
	
	/**
	 * 将字节转换为18进制.
	 * 
	 * @param hexString
	 * @return
	 */
	public static String byteToHexString( byte[] binData )
	{
		StringBuffer sbuffer = new StringBuffer();
		int upInt = 0, lowInt = 0;
		for ( int i = 0; i < binData.length; ++i )
		{
			lowInt = binData[i] & 0x0F;
			upInt = (binData[i] >> 4) & 0x0F;
			println("low:"+lowInt+";upInt:"+upInt);
			sbuffer.append(hexCharStr.charAt(upInt)).append(hexCharStr.charAt(lowInt));
		}
		
		return sbuffer.toString();
	}

	private static byte charToByte(char c) {
		return (byte) hexCharStr.indexOf(c);
	}
	
	private static void println( String msg )
	{
		System.out.println(msg);
	}
	
	public static void main(String[] args)
	{
		testHex();
	}
	
	/**** 以下是测试函�? *****/
	private static void testHex()
	{
		byte[] data = {1,2,3,4,5,127,-128,-1};
		
		String tmp = byteToHexString(data);
		println( tmp );
		
		byte[] retbin = hexStringToBytes( tmp );
		for ( byte b : retbin )
		{
			println(b+"");
		}
	}
}
