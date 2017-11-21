package ebcs.database.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ebcs.database.bean.Portrait;
import ebcs.database.mybatis.dao.PortraitManagerMapper;
import ebcs.database.service.PortraitManagerService;

@Service
public class PortraitManagerServiceImpl implements PortraitManagerService
{
	@Autowired
	private PortraitManagerMapper dao;

	@Override
	public int insert( Portrait portrait )
	{
		return dao.insert(portrait);
	}

	@Override
	public Portrait selectCustFile( String custId)
	{
		return dao.selectByCustId(custId);
	}

	@Override
	public int update(Portrait portrait )
	{
		return dao.updateByPrimaryKey(portrait);
	}

}
