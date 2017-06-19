package com.dlt.server.web.controller;

import com.dlt.server.common.constant.ConstCommonString;
import com.dlt.server.common.response.Response;
import com.dlt.server.common.util.LogUtil;
import com.dlt.server.facade.SecurityUserFacade;
import com.dlt.server.facade.command.LoginCommand;
import com.dlt.server.facade.dto.SecurityUserDTO;
import com.dlt.server.facade.query.QueryUser;
import com.dlt.server.web.controller.base.BaseController;
import org.apache.log4j.MDC;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * 用户控制器
 * Created by xuliugen on 2017/5/2.
 */
@RestController
@RequestMapping(value = "/user", produces = {ConstCommonString.APP_JSON_UTF_8})
public class SecurityUserController extends BaseController {

    @Inject
    private SecurityUserFacade securityUserFacade;

    /**
     * 添加用户
     */
    @PostMapping(value = "/add")
    public Response addUser(@RequestBody SecurityUserDTO userDTO) {
        MDC.put(ConstCommonString.TRACE_ID, LogUtil.getTraceId("USER_ADD"));
        logger.info("Request--" + userDTO.toString());

        return securityUserFacade.addUser(userDTO);
    }

    /**
     * 用户信息查询显示，前端分页
     * @param queryUser
     * @return
     */
    @GetMapping(value = "/list")
    public Response listByCondition(QueryUser queryUser) {
        MDC.put(ConstCommonString.TRACE_ID, LogUtil.getTraceId("USER_LIST_USER_BY_CONDITION"));
        logger.info("Request--" + queryUser.toString());

        return securityUserFacade.listByCondition(queryUser);
    }

    /**
     * 更新用户信息
     * @param userDTO
     * @return
     */
    @PatchMapping(value = "/update")
    public Response updateUser(@RequestBody SecurityUserDTO userDTO) {
        MDC.put(ConstCommonString.TRACE_ID, LogUtil.getTraceId("USER_UPDATE"));
        logger.info("Request--" + userDTO.toString());

        return securityUserFacade.updateUser(userDTO);
    }

    /**
     * 添加用户的时候判断用户名是否已经存在
     * @param userName 用户名,不是用户真实姓名：限制用户必须是字母和数字或者'.'的组合，不能包含中文
     * @return
     */
    @GetMapping(value = "/getby/name")
    public Response getByUserName(@RequestParam("userName") String userName) {
        MDC.put(ConstCommonString.TRACE_ID, LogUtil.getTraceId("USER_GET_USER_BY_USERNAME"));
        logger.info("Request--" + userName.toString());

        return securityUserFacade.getUserByUserName(userName);
    }

    /**
     * 根据用户手机号查找用户信息
     * @param userPhone 用户电话号码
     * @return
     */
    @GetMapping(value = "/getby/phone")
    public Response getByPhone(@RequestParam("userPhone") String userPhone) {
        MDC.put(ConstCommonString.TRACE_ID, LogUtil.getTraceId("USER_GET_USER_BY_PHONE"));
        logger.info("Request--" + userPhone);

        return securityUserFacade.getUserByPhone(userPhone);
    }

    /**
     * 判断当前的单位下边是否已经有人员分配
     * @param unitId 单位ID
     * @return 如果有的话就返回1，如果没有的话就直接返回0
     */
    @GetMapping(value = "/getby/unitId")
    public Response getByUnitId(@RequestParam("unitId") String unitId) {
        MDC.put(ConstCommonString.TRACE_ID, LogUtil.getTraceId("USER_GET_USER_BY_UNITID"));
        logger.info("Request--" + unitId);

        return securityUserFacade.getUserByUnitId(unitId);
    }

    /**
     * 用户登录
     * @param loginCommand
     * @return
     */
    @PostMapping(value = "/login")
    public Response login(@RequestBody LoginCommand loginCommand) {
        MDC.put(ConstCommonString.TRACE_ID, LogUtil.getTraceId("USER_LOGIN"));
        logger.info("Request--" + loginCommand.toString());

        return securityUserFacade.login(loginCommand);
    }

    /**
     * 用户删除
     * @param userId
     * @return
     */
    @DeleteMapping(value = "/delete")
    public Response delete(@RequestParam("userId") Integer userId) {
        MDC.put(ConstCommonString.TRACE_ID, LogUtil.getTraceId("USER_DELETE"));
        logger.info("Request--" + userId);

        return securityUserFacade.delete(userId);
    }

}
