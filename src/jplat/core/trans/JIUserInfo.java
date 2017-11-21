package jplat.core.trans;

public interface JIUserInfo {

	//该方法用于唯一标示一个用户.
	public String obtUserMark();
	
	//判断该用户信息是否是新创建的.
	public boolean isNewUser();
	
	//获得密钥信息.
	public String getEncryptKey();
}
