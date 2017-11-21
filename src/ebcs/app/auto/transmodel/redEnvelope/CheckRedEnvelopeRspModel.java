package ebcs.app.auto.transmodel.redEnvelope;

import java.util.List;
import jplat.tools.data.validate.JDataRuleRequired;


/**
 * @system 行庆红包.
 * @author autocode generated by bxz
 * @date 2017/04/17/10:12:43
 * @comment 红包校验.
 * @charset utf-8
 * @change 建立新接口.
 */
 
public class CheckRedEnvelopeRspModel
{
	//商户编号 
	@JDataRuleRequired
	private String eCustId;

	//商户名称 
	@JDataRuleRequired
	private String eCustName;

	//优惠券编号 
	@JDataRuleRequired
	private String couponId;

	//优惠券名称 
	private String couponName;

	//优惠券领取状态 
	private String couponType;

	//优惠券类型 
	private String couponTemplateId;

	//发放类型 
	private String issueType;

	//优惠券类型（发券方） 
	private String couponTypeMerch;

	//口令 
	private String couponPwd;

	//是否实名制 默认为0关闭实名制 0-关闭实名制度；1-开启实名制
	private String realNameFlag;

	//客户编号 
	private String custId;

	//客户名称 
	private String custName;

	//金额 
	private String discountAmount;


	public String getECustId( ) {
		return eCustId;
	}

	public void setECustId(String arg0 ) {
		eCustId = arg0;
	}

	public String getECustName( ) {
		return eCustName;
	}

	public void setECustName(String arg0 ) {
		eCustName = arg0;
	}

	public String getCouponId( ) {
		return couponId;
	}

	public void setCouponId(String arg0 ) {
		couponId = arg0;
	}

	public String getCouponName( ) {
		return couponName;
	}

	public void setCouponName(String arg0 ) {
		couponName = arg0;
	}

	public String getCouponType( ) {
		return couponType;
	}

	public void setCouponType(String arg0 ) {
		couponType = arg0;
	}

	public String getCouponTemplateId( ) {
		return couponTemplateId;
	}

	public void setCouponTemplateId(String arg0 ) {
		couponTemplateId = arg0;
	}

	public String getIssueType( ) {
		return issueType;
	}

	public void setIssueType(String arg0 ) {
		issueType = arg0;
	}

	public String getCouponTypeMerch( ) {
		return couponTypeMerch;
	}

	public void setCouponTypeMerch(String arg0 ) {
		couponTypeMerch = arg0;
	}

	public String getCouponPwd( ) {
		return couponPwd;
	}

	public void setCouponPwd(String arg0 ) {
		couponPwd = arg0;
	}

	public String getRealNameFlag( ) {
		return realNameFlag;
	}

	public void setRealNameFlag(String arg0 ) {
		realNameFlag = arg0;
	}

	public String getCustId( ) {
		return custId;
	}

	public void setCustId(String arg0 ) {
		custId = arg0;
	}

	public String getCustName( ) {
		return custName;
	}

	public void setCustName(String arg0 ) {
		custName = arg0;
	}

	public String getDiscountAmount( ) {
		return discountAmount;
	}

	public void setDiscountAmount(String arg0 ) {
		discountAmount = arg0;
	}


}

/******** SET values to bean.

+++++++++++++

//******* GET valus from bean.

	public static __RET_CLASS__ saveCheckRedEnvelopeResponse( CheckRedEnvelopeRspModel rspModel )
	{
		__RET_CLASS__ __resultModel = new __RET_CLASS__( );
		__resultModel.setECustId( rspModel.getECustId());			//商户编号 
		__resultModel.setECustName( rspModel.getECustName());			//商户名称 
		__resultModel.setCouponId( rspModel.getCouponId());			//优惠券编号 
		__resultModel.setCouponName( rspModel.getCouponName());			//优惠券名称 
		__resultModel.setCouponType( rspModel.getCouponType());			//优惠券领取状态 
		__resultModel.setCouponTemplateId( rspModel.getCouponTemplateId());			//优惠券类型 
		__resultModel.setIssueType( rspModel.getIssueType());			//发放类型 
		__resultModel.setCouponTypeMerch( rspModel.getCouponTypeMerch());			//优惠券类型（发券方） 
		__resultModel.setCouponPwd( rspModel.getCouponPwd());			//口令 
		__resultModel.setRealNameFlag( rspModel.getRealNameFlag());			//是否实名制 默认为0关闭实名制 0-关闭实名制度；1-开启实名制
		__resultModel.setCustId( rspModel.getCustId());			//客户编号 
		__resultModel.setCustName( rspModel.getCustName());			//客户名称 
		__resultModel.setDiscountAmount( rspModel.getDiscountAmount());			//金额 

		return __resultModel;
	}


********/