package com.dlt.server.facade;

import com.dlt.server.common.response.Response;
import com.dlt.server.facade.command.LoginCommand;
import com.dlt.server.facade.dto.SecurityUserDTO;
import com.dlt.server.facade.query.QueryUser;

/**
 * Created by xuliugen on 2017/5/2.
 */
public interface SecurityUserFacade {

    /**
     * 用户登录操作
     * @param loginCommand
     * @return
     */
    Response login(LoginCommand loginCommand);

    /**
     * 添加用户
     * @param userDTO
     * @return
     */
    Response addUser(SecurityUserDTO userDTO);

    /**
     * 根据用户名获取用户信息，用于用于注册的时候判断用户是否已经存在
     * @param userName 用户名
     * @return
     */
    Response getUserByUserName(String userName);

    Response getUserByPhone(String userPhone);

    /**
     * 更新用户信息
     * @param userDTO
     * @return
     */
    Response updateUser(SecurityUserDTO userDTO);

    Response listByCondition(QueryUser queryUser);

    Response getUserByUnitId(String unitId);

    Response delete(Integer userId);
}
