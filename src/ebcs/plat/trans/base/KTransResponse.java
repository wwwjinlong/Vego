package ebcs.plat.trans.base;

public class KTransResponse
{
	/*** 9901 --- 9950 ****/
	public static final String CD_ICOP_CONNECT_FAIL = "9901";
	public static final String MSG_ICOP_CONNECT_FAIL = "与渠道通讯异常";
	
	public static final String CD_ICOP_EMPTY_RETBODY = "9902";
	public static final String MSG_ICOP_EMPTY_RETBODY = "渠道返回报文为空.";
	
	public static final String CD_ICOP_PACK_ERR = "9903";
	public static final String MSG_ICOP_PACK_ERR = "无法解析渠道返回报文.";
	
	public static final String CD_ICOP_TRANS_FAIL = "9904";		//渠道返回错误.
	
	public static final String CD_INIT_TRANSCTX_FAIL = "9905";
	public static final String MSG_INIT_TRANSCTX_FAIL = "初始化系统出错,t=icp";
}
