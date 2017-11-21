package ebcs.plat.trans.base;

/**
 * 平台级常量类.
 * @author zhangcq
 * @date Dec 16, 2016
 * @comment
 */
public interface KBCSPublic
{
	//密码类型
	public static final String H_ENC_TYPE = "_kENC_TYPE";
	//默认密码头（还没有用到）
	public static final String H_DFT_ENC_HEAD = "";
	//国密类型密码头
	public static final String H_GM_ENC_HEAD = "GM#";
	//默认密码类型
	public static final String H_DFT_ENC_TYPE = "0";
	//aes密码类型
	public static final String H_AES_ENC_TYPE = "1";
	//国密密码类型
	public static final String H_GM_ENC_TYPE = "2";
	//H5支付网关
	public static final String H_H5_ENC_TYPE = "4";
}
