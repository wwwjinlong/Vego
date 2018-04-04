package jplat.core.trans;

/**
 * 记录本次请求所有所需的信息
 * @author zhangcq
 * @date Nov 30, 2016
 * @comment
 */
public class JAppConnectInfo
{
	public static String V_ENC_AES = "a";
	public static String V_ENC_RSA = "r";
	public static String V_ENC_PLAIN = "p";
	public static String V_HRETCODE_SUCC = "0000";				//返回头表示成功,返回数据符合预期，可以正常处理.
	public static String V_HRETCODE_FAIL = "FFFF";				//返回头表示失败,返回标准失败信息.
	
	public static String H_RET_HEAD = "rethd1";					//返回头.
	public static String H_HTOKEN_NAME = "ttkn2";				//会话令牌名.		req-Header中.
	public static String H_SET_HTOKEN_NAME = "set-ttkn2";		//设置会话令牌名.		rsp-Header中.
	public static String H_INFO_KEY = "infoq";					//http-header.请求信息 key=value&形式
																//主要包含渠道ch=[ebank|web],加密类型e=[a|r|p],签名s=[md5]
																//追踪标识tr=[rand string]用于一次App启动后的所有请求.
	public static String K_CHL_TYPE = "ch";		//渠道类型
	public static String K_ENC_TYPE = "e";		//加密类型
	public static String K_ENC_TKN = "et";		//加密令牌.
	public static String K_TRACE_FLAG = "tr";	//追踪标识.
	public static String K_CLI_TYPE = "ct";		//客户端类型
	public static String K_CLI_VERSION = "cv";	//app版本.
	public static String K_DATA_SIGN = "s";		//数据签名
	
	//会话令牌信息
	private String sessTkn;
	
	//加密类型
	private String encType;
	
	//(协商)加密密钥,即登录时协商的AES密钥.
	private String encKey;
	
	//RSA中上送的AES密钥.
	private String raKey;
	
	//密钥令牌
	private String encTkn;						//根据令牌获取密钥.
	
	//签名校验值.
	private String signKey;		//md5中的盐值.
	
	//签名算法.
	private String sigAlg;
	
	//请求渠道
	private String reqChl;
	
	//客户端平台
	private String cliType;
	
	//客户端版本
	private String cliVersion;
	
	//客户端IP地址
	private String cliIp;
	
	//客户端MAC地址
	private String cliMac;
	
	//客户端轨迹追踪标记
	private String traceFlag;
	
	//服务器IP地址.
	private String localIp;
	
	//数据类型.
	private String contentType;
	
	//协议类型.
	private String protocol;

	public String getEncTkn() {
		return encTkn;
	}

	public void setEncTkn(String encTkn) {
		this.encTkn = encTkn;
	}

	public String getEncType() {
		return encType;
	}

	public void setEncType(String encType) {
		this.encType = encType;
	}

	public String getEncKey() {
		return encKey;
	}

	public void setEncKey(String encKey) {
		this.encKey = encKey;
	}

	public String getRaKey() {
		return raKey;
	}

	public void setRaKey(String raKey) {
		this.raKey = raKey;
	}

	public String getSignKey() {
		return signKey;
	}

	public void setSignKey(String signKey) {
		this.signKey = signKey;
	}

	public String getSigAlg() {
		return sigAlg;
	}

	public void setSigAlg(String sigAlg) {
		this.sigAlg = sigAlg;
	}

	public String getReqChl() {
		return reqChl;
	}

	public void setReqChl(String reqChl) {
		this.reqChl = reqChl;
	}

	public String getCliType() {
		return cliType;
	}

	public void setCliType(String cliType) {
		this.cliType = cliType;
	}

	public String getCliVersion() {
		return cliVersion;
	}

	public void setCliVersion(String cliVersion) {
		this.cliVersion = cliVersion;
	}

	public String getCliIp() {
		return cliIp;
	}

	public void setCliIp(String cliIp) {
		this.cliIp = cliIp;
	}

	public String getCliMac() {
		return cliMac;
	}

	public void setCliMac(String cliMac) {
		this.cliMac = cliMac;
	}

	public String getSessTkn() {
		return sessTkn;
	}

	public void setSessTkn(String sessTkn) {
		this.sessTkn = sessTkn;
	}

	public String getTraceFlag() {
		return traceFlag;
	}

	public void setTraceFlag(String traceFlag) {
		this.traceFlag = traceFlag;
	}
	
	public String getLocalIp() {
		return localIp;
	}

	public void setLocalIp(String localIp) {
		this.localIp = localIp;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
}
