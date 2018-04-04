package jplat.base.constant;


/**
 * 0001到2000位保留错误码.
 * @author zhangcq
 * @date Nov 28, 2016
 * @comment 平台级别错误码.
 */
public class KPlatResponseCode
{
	public static String CD_SUCCESS = "0000";
	public static String MSG_SUCCESS = "交易成功";
	
	public static String CD_FAIL = "0001";
	public static String MSG_FAIL = "交易失败";
	
	//head为空.
	public static String CD_PARA_ERROR = "0003";
	public static String MSG_PARA_ERROR = "系统参数错误";
	
	public static String CD_CONF_ERROR = "0004";
	public static String MSG_CONF_ERROR = "系统参数配置错误";

	public static String CD_NO_SESSION = "0005";
	public static String MSG_NO_SESSION = "会话超时.";
	
	public static String CD_INPUT_ERROR = "0006";
	public static String MSG_INPUT_ERROR = "数据异常";		//无法解析,格式错误等.
	
	public static String CD_INPUT_UNAVAIABLE = "0007";							//body为空.
	public static String MSG_INPUT_UNAVAIABLE = "输入数据为空或者格式非法.";		//字段为空.
	
	public static String CD_AES_ENC_FAIL = "0009";
	public static String MSG_AES_ENC_FAIL = "加解密错误,类型1";
	
	public static String CD_RSA_ENC_FAIL = "0010";
	public static String MSG_RSA_ENC_FAIL = "加解密错误,类型2";
	
	public static String CD_USERGET_FAIL = "0011";
	public static String MSG_USERGET_FAIL = "无法获取客户信息";
	
	public static String CD_APPCONN_ERROR = "0012";
	public static String MSG_APPCONN_ERROR = "读取请求数据错误.";
	
	public static String CD_WRITE_ERR = "0013";
	public static String MSG_WRITE_ERR = "返回数据出错.";
	
	public static String CD_CONF_NOT_FOUND = "0014";
	public static String MSG_CONF_NOT_FOUND = "配置参数没找到.";
	
	public static String CD_REDIS_SESSION_INIT_ERR = "0015";
	public static String MSG_REDIS_SESSION_INIT_ERR = "会话建立失败,0";
	
	public static String CD_VALUE_ERR = "0016";
	public static String MSG_VALUE_ERR = " :field is not allowed empty.";
	
	public static String CD_REQ_DUPLICATE = "0017";
	public static String MSG_REQ_DUPLICATE = "交易已受理,请勿重复提交.";
	
	//缓存创建失败.
	public static String CD_CACHE_FAILED = "0018";
	public static String MSG_CACHE_FAILED = "系统忙碌...请稍后重试.-2";
	
	//系统框架层出错.
	public static String CD_FRAMEWORK_ERR = "0020";
	public static String MSG_FRAMEWORK_ERR = "系统调用出错.";
	
	public static String CD_IO_ERR = "0021";
	public static String MSG_IO_ERR = "系统调用出错.";
}
