package jplat.tools.string;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

public class StringUtil
{
	public static boolean isEqualAny( String s0, String ...s1 )
	{
		if ( isEmpty(s0) )
		{
			return false;
		}
		
		//只要有任何一个等于就返回true.
		s0 = s0.trim();
		for ( int i = 0; i < s1.length; ++i )
		{
			if ( s0.equals(s1[i]) )
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取默认值.
	 * @author zhangcq
	 * @date Nov 23, 2016
	 * @comment 
	 * @param s1
	 * @param def
	 * @return
	 */
	public static String getDefString( String s1, String def )
	{
		if ( isEmpty(s1) )
		{
			return def;
		}
		
		return s1;
	}
	/**
	 * 
	 * @param args
	 * @return
	 */
	public static String concat( String[] args, String join )
	{
		if ( args == null || args.length == 0 )
		{
			return "";
		}

		StringBuffer sb = new StringBuffer();
		for ( String str : args )
		{
			sb.append(str+join);
		}

		return sb.toString();
	}
	
	/**
	 * 将第一个字母大小.
	 * @param fdName
	 * @return
	 */
	public static String bigFirstCase( String fdName )
	{
		if ( fdName == null || fdName.length() == 0 )
		{
			return fdName;
		}

		char firstCh = fdName.charAt(0);
		if ( firstCh >= 97 && firstCh <= 122 )
		{
			firstCh = (char)((int)firstCh - 32);
		}

		return new StringBuilder().append(firstCh).append(fdName.substring(1)).toString();
//		return ""+firstCh+fdName.substring(1);
	}
	
	/**
	 * 将第一个字母大小.
	 * @param fdName
	 * @return
	 */
	public static String lowFirstCase( String fdName )
	{
		if ( fdName == null || fdName.length() == 0 )
		{
			return fdName;
		}

		char firstCh = fdName.charAt(0);
		if ( firstCh >= 65 && firstCh <= 90 )
		{
			firstCh = (char)((int)firstCh + 32);
		}

		return ""+firstCh+fdName.substring(1);
	}
	
	public static String getLocalIP() {
		String result = "";
		try {
			result = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			//logger.error("get the local ip unknow host error!", e);
		}
		return result;
	}

	/**
	 * <li>判断字符串是否为空值</li> <li>NULL、空格均认为空值</li>
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isEmpty(String value) {
		return null == value || "".equals(value.trim()) || "null".equalsIgnoreCase(value.trim());
	}
	
	public static boolean isAnyOfEmpty( String... values )
	{
		for ( String value : values )
		{
			if ( isEmpty( value ) )
			{
				return true;
			}
		}
		
		return false;
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0)
			return true;
		for (int i = 0; i < strLen; i++)
			if (!Character.isWhitespace(str.charAt(i)))
				return false;

		return true;
	}

	/**
	 * 内容不为空
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNotEmpty(String value) {
		return !isEmpty(value);
	}

	/**
	 * 重复字符串 如 repeatString("a",3) ==> "aaa"
	 * 
	 * @author uke
	 * @param src
	 * @param repeats
	 * @return
	 */
	public static String repeatString(String src, int repeats) {
		if (null == src || repeats <= 0) {
			return src;
		} else {
			StringBuffer bf = new StringBuffer();
			for (int i = 0; i < repeats; i++) {
				bf.append(src);
			}
			return bf.toString();
		}
	}

	/**
	 * 左对齐字符串 * lpadString("X",3); ==>" X" *
	 * 
	 * @param src
	 * @param length
	 * @return
	 */
	public static String lpadString(String src, int length) {
		return lpadString(src, length, " ");
	}

	/**
	 * 以指定字符串填补空位，左对齐字符串 * lpadString("X",3,"0"); ==>"00X"
	 * 
	 * @param src
	 * @param byteLength
	 * @param temp
	 * @return
	 */
	public static String lpadString(String src, int length, String single) {
		if (src == null || length <= src.getBytes().length) {
			return src;
		} else {
			return repeatString(single, length - src.getBytes().length) + src;
		}
	}

	/**
	 * 右对齐字符串 * rpadString("9",3)==>"9 "
	 * 
	 * @param src
	 * @param byteLength
	 * @return
	 */
	public static String rpadString(String src, int byteLength) {
		
		return rpadString(src, byteLength, " ");
	}

	/**
	 * 以指定字符串填补空位，右对齐字符串 rpadString("9",3,"0")==>"900"
	 * 
	 * @param src
	 * @param byteLength
	 * @param single
	 * @return
	 */
	public static String rpadString(String src, int length, String single) {
		int srcLen = 0;
		try {
			srcLen = src.getBytes("GBK").length;
			if (src == null || length <= srcLen) {
				return src;
			} else {
				return src + repeatString(single, length - srcLen);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	} 
	
//	public static String rpadString(String src, int length, String single) {
//		if (src == null || length <= src.getBytes().length) {
//			return src;
//		} else {
//			return src + repeatString(single, length - src.getBytes().length);
//		}
//	}

	/**
	 * 去除,分隔符，用于金额数值去格式化
	 * 
	 * @param value
	 * @return
	 */
	public static String decimal(String value) {
		if (null == value || "".equals(value.trim())) {
			return "0";
		} else {
			return value.replaceAll(",", "");
		}
	}

	/**
	 * 在数组中查找字符串
	 * 
	 * @param params
	 * @param name
	 * @param ignoreCase
	 * @return
	 */
	public static int indexOf(String[] params, String name, boolean ignoreCase) {
		if (params == null)
			return -1;
		for (int i = 0, j = params.length; i < j; i++) {
			if (ignoreCase && params[i].equalsIgnoreCase(name)) {
				return i;
			} else if (params[i].equals(name)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * 将字符转数组
	 * 
	 * @param str
	 * @return
	 */
	public static String[] toArr(String str) {
		String inStr = str;
		String a[] = null;
		try {
			if (null != inStr) {
				StringTokenizer st = new StringTokenizer(inStr, ",");
				if (st.countTokens() > 0) {
					a = new String[st.countTokens()];
					int i = 0;
					while (st.hasMoreTokens()) {
						a[i++] = st.nextToken();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}

	/**
	 * 字符串数组包装成字符串
	 * 
	 * @param ary
	 * @param s
	 *            包装符号如 ' 或 "
	 * @return
	 */
	public static String toStr(String[] ary, String s) {
		if (ary == null || ary.length < 1)
			return "";
		StringBuffer bf = new StringBuffer();
		bf.append(s);
		bf.append(ary[0]);
		for (int i = 1; i < ary.length; i++) {
			bf.append(s).append(",").append(s);
			bf.append(ary[i]);
		}
		bf.append(s);
		return bf.toString();
	}

	/**
	 * 取整数值
	 * 
	 * @param map
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static int getInt(Map map, String key, int defValue) {
		if (null != map && isNotEmpty(key)) {
			try {
				return Integer.parseInt((String) map.get(key));
			} catch (Exception e) {
			}
		}
		return defValue;
	}

	/**
	 * 取字符串
	 * 
	 * @param map
	 * @param key
	 * @param defValue
	 * @return
	 */
	public static String getString(Map map, String key, String defValue) {
		if (null != map && isNotEmpty(key)) {
			try {
				return (String) map.get(key);
			} catch (Exception e) {
			}
		}
		return defValue;
	}

	/**
	 * 將key=value;key2=value2……轉換成Map
	 * 
	 * @param params
	 * @return
	 */
	public static Map gerneryParams(String params) {
		Map args = new HashMap();
		if (!isEmpty(params)) {
			try {
				String map, key, value;
				StringTokenizer st = new StringTokenizer(params, ";");
				StringTokenizer stMap;
				while (st.hasMoreTokens()) {
					map = st.nextToken();
					if (isEmpty(map.trim()))
						break;
					stMap = new StringTokenizer(map, "=");
					key = stMap.hasMoreTokens() ? stMap.nextToken() : "";
					if (isEmpty(key.trim()))
						continue;
					value = stMap.hasMoreTokens() ? stMap.nextToken() : "";
					args.put(key, value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return args;
	}

	/**
	 * 页面格式化日期:yyyyMMdd ---> yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(String date) {
		return isEmpty(date) ? "" : DateUtil.format(date, "yyyyMMdd",
				"yyyy-MM-dd");
	}

	/**
	 * 解析文件的扩展名
	 * 
	 * @param oldName
	 * @return
	 */
	public static String getFileExtName(String oldName) {
		String ext = "";
		int lastIndex = oldName.lastIndexOf(".");
		if (lastIndex > 0) {
			ext = oldName.substring(lastIndex);
		}
		return ext;
	}

	public static void generyXmlEntry(StringBuffer bf, String entry,
			Object value) {
		bf.append("<").append(entry).append(">");
		if (null != value)
			bf.append(value.toString().trim());
		bf.append("</").append(entry).append(">");
	}

	public static void generyXmlEntryCData(StringBuffer bf, String entry,
			Object value) {
		bf.append("<").append(entry).append("><![CDATA[");
		if (null != value)
			bf.append(value);
		bf.append("]]></").append(entry).append(">");
	}

	/**
	 * 生成图片访问全路径
	 * 
	 * @param rootUrl
	 *            图片服务器根目录
	 * @param dir
	 *            分类目录
	 * @param imgId
	 *            图片ID
	 * @param imgType
	 *            图片类型
	 * @return
	 */
	public static String generyImgUrl(Object rootUrl, Object dir, Object imgId,
			Object imgType) {
		StringBuffer bf = new StringBuffer();
		try {
			bf.append(rootUrl).append("/");
			bf.append(dir).append("/");
			bf.append(imgId).append(".").append(imgType);
		} catch (Exception e) {
			bf.append("");
		}
		return bf.toString();
	}

	public static int string2Int(String s) {
		if (s == null || "".equals(s) || "undefined".equals(s))
			return 0;
		int result = 0;
		try {
			if (s.indexOf(",") != -1) {
				s = s.substring(0, s.indexOf(","));
			} else if (s.indexOf(".") != -1) {
				s = s.substring(0, s.indexOf("."));
			} else if (s.indexOf("|") != -1) {
				s = s.substring(0, s.indexOf("|"));
			}
			result = Integer.parseInt(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean string2boolean(String s) {
		return "true".equals(s);
	}

	public static String formatAccount(String acctNo, String format) {
		if ("#### ####".equals(format)) {
			if (isNotEmpty(acctNo) && acctNo.length() > 4) {
				StringBuffer bf = new StringBuffer();
				int max = acctNo.length() - 4;
				int i = 0;
				while (i < max) {
					bf.append(acctNo.substring(i, i + 4)).append(" ");
					i += 4;
				}
				bf.append(acctNo.substring(i));
				return bf.toString();
			}
		}
		return acctNo;
	}

	/**
	 * 转整数
	 * 
	 * @param str
	 * @return
	 */
	public static int parseInt(String str) {
		return Integer.parseInt(str);
	}

	/**
	 * 
	 * @param str
	 * @param def
	 * @return
	 */
	public static int parseInt(String str, int def) {
		try {
			return parseInt(str);
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 当前日期 
	 * @return
	 */
	public static String today() {
		return DateUtil.todayStr();
	}
	
	/**
	 * 当前日期
	 * 
	 * @param format
	 *            格式化
	 * @return
	 */
	public static String todayStr(String format) {
		return DateUtil.todayStr(format);
	} 
	
	/**
	 * 提前N天的日期
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static String beforeDate(Date curDate, int days) {
		java.util.Calendar c = java.util.Calendar.getInstance();
		c.setTime(curDate);
		c.add(java.util.Calendar.DAY_OF_YEAR, -days);
		return DateUtil.formatDateToStr(c.getTime(), DateUtil.DATE_FORMATTER);

	}
	
	public static String beforeNDate(Integer days) {
		return beforeDate(DateUtil.today(),days);
	}
	/**
	 * null转成""
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static String nullToStr(Object param) {
		return (String) (null==param?"":param);
	}
	/**
	 * null转成0
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public static int nullToInt(Object param) {
		return (null==param?0:Integer.parseInt((String) param));
	}
	
	public static String beforeDate() {
		return beforeDate(DateUtil.today(),7);
	}

	//1转换为一，2转换为二
	public static String changeNumToString(int num) {
		String numStr="";
		if(num<10){
			switch (num) {
			case 1:
				return "一";
			case 2:
				return "二";
			case 3:
				return "三";
			case 4:
				return "四";
			case 5:
				return "五";
			case 6:
				return "六";	
			case 7:
				return "七";
			case 8:
				return "八";
			case 9:
				return "九";
			default:
				return "XX";
			}
		}else{
			return "XX";
		}
	}
	
	// 是否数字或者字母判断
	public static boolean isNumOrWord(String pwd) {
		boolean flag = true;
		// 判断是否只有字母或者数字
		String reg = "^[0-9a-zA-Z]*$";
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(pwd);
		flag = m.matches();
		
		return flag;
	}
	
	public static String getIpAddr(HttpServletRequest request) { 
		          String ip = request.getHeader("x-forwarded-for"); 
		           if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		               ip = request.getHeader("Proxy-Client-IP"); 
		           } 
		           if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		               ip = request.getHeader("WL-Proxy-Client-IP"); 
		           } 
		           if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
		              ip = request.getRemoteAddr(); 
		          } 
		          return ip; 
		   }
}
