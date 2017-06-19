package com.dlt.server.core.repository.mybatis;

import com.dlt.server.core.repository.mybatis.base.BaseMapperAsInteger;
import com.dlt.server.facade.dto.SecurityUserDTO;
import com.dlt.server.facade.query.QueryUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SecurityUserMapper extends BaseMapperAsInteger<SecurityUserDTO> {

    SecurityUserDTO selectByEntity(@Param("securityUserDTO") SecurityUserDTO securityUserDTO);

    int selectCountByUserName(@Param("userName") String userName);

    String selectCountByUnitId(@Param("unitId") String unitId);

    List<SecurityUserDTO> listByCondition(@Param("queryUser") QueryUser queryUser);
}