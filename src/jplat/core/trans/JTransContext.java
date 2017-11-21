package jplat.core.trans;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jplat.base.constant.KPlatResponseCode;
import jplat.tools.coder.xml.JNXmlParser;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ebcs.base.utils.DataUtils;

/**
 * 一次交易请求的上下文.比如像ICOP发起一起请求.
 * @author zhangcq
 * @date Nov 22, 2016
 * @comment
 */
public class JTransContext
{
	//相应信息和错误码.
	private String retCode;
	private String retMsg;
	
	private Map<String,Object> ctxMap;
	
	//返回报文.
	private String retPack;
	
	//客户标识.
	private String userInfoMark;
	
	//用于交易追踪和日志埋点.
	//比如客户信息和会话信息用于追踪用户.
	private String transMark;
	
	//交易码.
	private String transCode;
	
	//交易请求头.
	private Object reqHeader;
	
	//交易请求体
	private Object reqBody;
	
	//交易响应头报文.
	private String rspHeader;
	
	//交易响应体报文.
	private Object rspBody;
	
	//交易响应体报文.
	private String rspBodyStr;
	
	//用于计算程序性能.
	private long startTime = new Date().getTime();
	
	private Logger logger = LogManager.getLogger(JTransContext.class);
	
	public JTransContext()
	{
		this.retCode = KPlatResponseCode.CD_SUCCESS;
		this.retMsg = "交易成功.";
		
		ctxMap = new HashMap<String,Object>();
	}
	
	//不带请求体.
	public JTransContext( String transcode )
	{
		this();
		this.transCode = transcode;
		
	}
	
	//带请求体.
	public JTransContext( String transcode, Object reqBody )
	{
		this();
		this.transCode = transcode;
	}
	
	//带请求体和请求头.
	public JTransContext( String _transCode, Object _reqBody, Object _reqHeader )
	{
		this();
		this.transCode = _transCode;
		this.reqBody = _reqBody;
		this.reqHeader = _reqHeader;
	}
	
	/**
	 * 过滤数据.
	 * @author zhangcq
	 * @date Apr 19, 2017
	 * @comment 
	 * @param clazz
	 * @return
	 */
	public <T> T obtainRspBody( Class<T> clazz )
	{
		return obtainRspBody(clazz,false);
	}
	
	/**
	 * 反序列化报文，后续会支持Map.
	 * @author zhangcq
	 * @date Nov 22, 2016
	 * @comment 
	 * @param clazz
	 * @return
	 */
	public <T> T obtainRspBody( Class<T> clazz, boolean dataFiler )
	{
		if ( rspBody != null )
		{
			return (T)rspBody;
		}
		
//		XLog.log("___RETURN_BODY_XML:%s", rspBodyStr);
		rspBody = (T)JNXmlParser.xml2Obj( rspBodyStr, clazz );
		
		//reset
		rspBodyStr = null;
		
		if ( dataFiler )
		{
			int rmCnt = DataUtils.removeEmptyList(rspBody);
			
			logger.info(String.format("rm count of %d for class %s", rmCnt,""+clazz.getName()));
		}
		
		return (T)rspBody;
	}
	
	/***
	 * 交易失败.
	 * @author zhangcq
	 * @date Nov 22, 2016
	 * @comment 
	 * @param errcode
	 * @param errmsg
	 * @return
	 */
	public JTransContext fail( String errcode, String errmsg )
	{
		retCode = errcode;
		retMsg = errmsg;
		
		return this;
	}
	
	/**
	 * 存放其他信息.
	 * @author zhangcq
	 * @date Nov 23, 2016
	 * @comment 
	 * @param key
	 * @param value
	 */
	public void putValue( String key, Object value )
	{
		ctxMap.put(key, value);
	}
	
	public Object getValue( String key )
	{
		return ctxMap.get(key);
	}
	
	public String getString( String key )
	{
		return (String)ctxMap.get(key);
	}
	
	/**
	 * 用于跟踪程序性能.
	 * @author zhangcq
	 * @date Nov 22, 2016
	 * @comment 
	 * @param mark
	 */
	public void tick( String mark )
	{
		Date now = new Date();
		logger.info("__TIME_COUNT:"+mark+":"+(now.getTime()-startTime));
		
		//reset
		startTime = now.getTime();
	}
	
	/***************/
	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public Object getReqHeader() {
		return reqHeader;
	}

	public void setReqHeader(Object reqHeader) {
		this.reqHeader = reqHeader;
	}

	public Object getReqBody() {
		return reqBody;
	}

	public void setReqBody(Object reqBody) {
		this.reqBody = reqBody;
	}

	public String getTransMark() {
		return transMark;
	}

	public void setTransMark(String transMark) {
		this.transMark = transMark;
	}

	public String getRspHeader() {
		return rspHeader;
	}

	public void setRspHeader(String rspHeader) {
		this.rspHeader = rspHeader;
	}
	
	public String getRspBodyStr() {
		return rspBodyStr;
	}

	public void setRspBodyStr(String rspBodyStr) {
		this.rspBodyStr = rspBodyStr;
	}

	public String getRetPack() {
		return retPack;
	}

	public void setRetPack(String retPack) {
		this.retPack = retPack;
	}

	public String getUserInfoMark() {
		return userInfoMark;
	}

	public void setUserInfoMark(String userInfoMark) {
		this.userInfoMark = userInfoMark;
	}
}
