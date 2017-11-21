package ebcs.app.auto.transmodel.redEnvelope;

import java.util.List;
import jplat.tools.data.validate.JDataRuleRequired;


/**
 * @system 行庆红包.
 * @author autocode generated by bxz
 * @date 2017/11/21/17:34:26
 * @comment 根据活动主题获取当前生效的红包信息.
 * @charset utf-8
 * @change 建立新接口.
 */
 
public class ActivityRedEnvelopeReqModel
{
	//活动主题 
	@JDataRuleRequired
	private String activitySubject;


	public String getActivitySubject( ) {
		return activitySubject;
	}

	public void setActivitySubject(String arg0 ) {
		activitySubject = arg0;
	}


}

/******** SET values to bean.

		ActivityRedEnvelopeReqModel reqModel = new ActivityRedEnvelopeReqModel( );
		reqModel.setActivitySubject( null );			//活动主题 


//******* GET valus from bean.

+

********/