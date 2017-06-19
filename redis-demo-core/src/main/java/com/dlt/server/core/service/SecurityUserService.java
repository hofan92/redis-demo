package com.dlt.server.core.service;

import com.dlt.server.facade.dto.SecurityUserDTO;
import com.dlt.server.facade.query.QueryUser;
import com.taobao.api.ApiException;

import java.util.List;

/**
 * Created by xuliugen on 2017/5/2.
 */
public interface SecurityUserService {

    SecurityUserDTO login(SecurityUserDTO securityUserDTO);

    int getUserCountByUserName(String userName);

    int addUser(SecurityUserDTO userDTO);

    int getUserByPhone(String userPhone);

    int updateUser(SecurityUserDTO userDTO);

    List<SecurityUserDTO> listByCondition(QueryUser queryUser);

    int getUserByUnitId(String unitId);

    int delete(Integer userId);
}
