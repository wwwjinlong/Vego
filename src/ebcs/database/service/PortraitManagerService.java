package ebcs.database.service;

import org.springframework.stereotype.Service;

import ebcs.database.bean.Portrait;

@Service
public interface PortraitManagerService
{
	public int insert( Portrait portrait );
	
	//根据客户号查询头像地址
	public Portrait selectCustFile( String custId);
	
	public int update( Portrait portrait);
}
