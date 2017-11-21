package ebcs.database.mybatis.dao;

import ebcs.database.bean.Portrait;

public interface PortraitManagerMapper {

    int insert(Portrait portrait);

    Portrait selectByCustId(String custId);

    int updateByPrimaryKey(Portrait portrait);
}