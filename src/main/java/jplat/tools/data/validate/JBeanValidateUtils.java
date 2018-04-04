package jplat.tools.data.validate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import jplat.base.constant.KPlatResponseCode;
import jplat.error.exception.JSystemException;
import jplat.tools.string.StringUtil;

public class JBeanValidateUtils
{
	/**
	 * 对于继承的对象 此对象不支持.
	 * 因为getDeclaredFields取不到.
	 * 使用method或者property可以取得到.
	 * 
	 * 由于该功能对系统损耗较大，通常是在联调测试,阶段使用.生产环境禁用。
	 * @author zhangcq
	 * @date Jan 20, 2017
	 * @comment 只能检查string和list类型.对int,double等由于默认值为零,无法判断是没传呢还是传了0.
	 * @param model
	 * @return
	 * @throws JSystemException 
	 */
	public static boolean validate( Object model ) throws JSystemException
	{
		Field[] fields = model.getClass().getDeclaredFields();

//		XLog.log("\n");
		for ( int i = 0; i < fields.length; ++i )
		{
			Field fd = fields[i];
			fd.setAccessible(true);
			
//			XLog.log("%d %s %s", i, model.getClass().getName(),fd.getName());
			
			if( fd.isAnnotationPresent(JDataRuleRequired.class) )
			{
				JDataRuleRequired dataRule = fd.getAnnotation(JDataRuleRequired.class);
				if( dataRule.value() )
				{
					Object value = null;
					try {
						value = fd.get(model);
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new JSystemException(KPlatResponseCode.CD_VALUE_ERR,fd.getName()+KPlatResponseCode.MSG_VALUE_ERR+"type=e");
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new JSystemException(KPlatResponseCode.CD_VALUE_ERR,fd.getName()+KPlatResponseCode.MSG_VALUE_ERR+"type=e");
					}
					
					if( value == null )
						throw new JSystemException(KPlatResponseCode.CD_VALUE_ERR,fd.getName()+KPlatResponseCode.MSG_VALUE_ERR);
					
					//String
					if( String.class.getName().equals(fd.getType().getName()) )
					{
						String strVal = (String)value;
						if ( StringUtil.isEmpty(strVal) )
						{
							throw new JSystemException(KPlatResponseCode.CD_VALUE_ERR,fd.getName()+KPlatResponseCode.MSG_VALUE_ERR);
						}
					}
					else if ( List.class.getName().equals(fd.getType().getName() ) )
					{
						List list = (List)value;
						for ( int n = 0; n < list.size(); ++n )
						{
							validate(list.get(n));
						}
					}
				}
			}
		}
		
		return true;
	}
	
	/*
	public static void main(String args[])
	{
		AppDirectoryQueryRspModel model = new AppDirectoryQueryRspModel();
		
		model.setHasNext("11");
		model.setHasNext1("111");
		
		List<AppDirectoryQueryRspNodeListModel> nodeList = new ArrayList<AppDirectoryQueryRspNodeListModel>();
		model.setNodeList(nodeList);
		
		AppDirectoryQueryRspNodeListModel ll = new AppDirectoryQueryRspNodeListModel();
		ll.setNodeId("11");
		nodeList.add(ll);
		
		AppDirectoryQueryRspNodeListModel ll2 = new AppDirectoryQueryRspNodeListModel();
		nodeList.add(ll2);
		
		try {
			validate(model);
		} catch (JSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return;
		}
	}*/
}
