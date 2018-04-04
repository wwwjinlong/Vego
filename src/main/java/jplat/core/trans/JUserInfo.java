package jplat.core.trans;

/**
 * 定义的最小基本用户信息.
 * 如果需要继承此类扩展. 该类是设计参考类用于数字保管箱中.
 * @author zhangcq
 * @date Jan 22, 2017
 * @comment
 */
@Deprecated
public class JUserInfo implements JIUserInfo
{
	private String loginId;		//登录ID
	private String name;		//姓名
	private String userId;		//客户号
	private String idNo;		//身份证号
	private String idType;		//身份证类型
	private String mobileNo;	//手机号
	private String gender;		//性别
	
	private String loginChl;	//登录渠道
	private String state;		//用户状态.
	private String vtoken;		//登录令牌pageId
	
	private String aesKey;		//协商密钥.
	
	//不要序列化.
	private transient  boolean isNew;		//是否新建.
	
	@Override
	public String obtUserMark() {
		// TODO Auto-generated method stub
		return userId;
	}
	
	@Override
	public boolean isNewUser() {
		// TODO Auto-generated method stub
		return isNew;
	}

	@Override
	public String getEncryptKey() {
		// TODO Auto-generated method stub
		return aesKey;
	}
	
	// *******getter setters ******//
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	
	public String getLoginChl() {
		return loginChl;
	}
	public void setLoginChl(String loginChl) {
		this.loginChl = loginChl;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getVtoken() {
		return vtoken;
	}
	public void setVtoken(String vtoken) {
		this.vtoken = vtoken;
	}
	public String getAesKey() {
		return aesKey;
	}
	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}
	public boolean isNew() {
		return isNew;
	}
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
}
