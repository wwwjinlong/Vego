package jplat.core.trans;

import jplat.base.constant.KPlatResponseCode;

/**
 * 返回客户端报文头.
 * @author zhangcq
 * @date Dec 15, 2016
 * @comment
 */
public class JClientRspHeader
{
	private String retCode;
	private String retMsg;
	
	private String appendMsg;		//附加消息.
	private String reqJnls;			//原请求流水 用于异步通知类行为. 类似消息ID一样的东西.
	
	private String pageId;
	private String ttkn2;
	
	public JClientRspHeader( String code, String msg )
	{
		retCode = code;
		retMsg = msg;
	}
	
	public JClientRspHeader()
	{
		retCode = KPlatResponseCode.CD_SUCCESS;
		retMsg = KPlatResponseCode.MSG_SUCCESS;
	}

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

	public String getAppendMsg() {
		return appendMsg;
	}

	public void setAppendMsg(String appendMsg) {
		this.appendMsg = appendMsg;
	}

	public String getReqJnls() {
		return reqJnls;
	}

	public void setReqJnls(String reqJnls) {
		this.reqJnls = reqJnls;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getTtkn2() {
		return ttkn2;
	}

	public void setTtkn2(String ttkn2) {
		this.ttkn2 = ttkn2;
	}
}
