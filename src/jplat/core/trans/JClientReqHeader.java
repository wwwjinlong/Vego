package jplat.core.trans;

/**
 * 客户端请求头.
 * @author zhangcq
 * @date Nov 30, 2016
 * @comment
 */
public class JClientReqHeader
{
	private String rocket;		//防重域
	private String reqJnls;		//请求流水.
	private String pageId;		//客户身份认证码.

	public String getRocket() {
		return rocket;
	}
	public void setRocket(String rocket) {
		this.rocket = rocket;
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
}
