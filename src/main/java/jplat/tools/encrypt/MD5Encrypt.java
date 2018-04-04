package jplat.tools.encrypt;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Random;

/**
 * MD5加密
 * 
 */
public class MD5Encrypt {

	/**
	 * 對字符串進行MD5加密
	 * 
	 * @param s
	 * @return 加密後的字符串
	 */
	public final static String MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			if (s != null && !"".equals(s.trim())) {
				byte[] strTemp = s.getBytes("utf-8");
				MessageDigest mdTemp = MessageDigest.getInstance("MD5");
				mdTemp.update(strTemp);
				byte[] md = mdTemp.digest();
				int j = md.length;
				char str[] = new char[j * 2];
				int k = 0;
				for (int i = 0; i < j; i++) {
					byte byte0 = md[i];
					str[k++] = hexDigits[byte0 >>> 4 & 0xf];
					str[k++] = hexDigits[byte0 & 0xf];
				}
				return new String(str);
			} else {
				return "";
			}
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 随机数生成（数字、大小写字母、下划线）
	 * @param length长度
	 * @return 随机数
	 */
	public final static String generateString (int length){
		String ALLCHAR = "0123456789abcdefghijkmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_";
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for(int i=0; i < length; i++ ){
			sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
		}
		return sb.toString();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		// 000000 : 670b14728ad9902aecba32e22fa4f6bd
		//System.out.println("你好！：" + MD5Encrypt.MD5("你好！"));
	}

}
