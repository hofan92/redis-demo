package com.dlt.server.facade.impl;

import com.dlt.server.common.response.Response;
import com.dlt.server.common.util.ValidationUtil;
import com.dlt.server.core.service.SecurityUserService;
import com.dlt.server.facade.SecurityUserFacade;
import com.dlt.server.facade.command.LoginCommand;
import com.dlt.server.facade.dto.SecurityUserDTO;
import com.dlt.server.facade.impl.assembler.SecurityUserAssembler;
import com.dlt.server.facade.query.QueryUser;
import org.springframework.http.HttpStatus;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

/**
 * Created by xuliugen on 2017/5/2.
 */
@Named
public class SecurityUserFacadeImpl implements SecurityUserFacade {

    @Inject
    private SecurityUserService userService;

    public Response login(LoginCommand loginCommand) {
        Response response = new Response();

        try {
            SecurityUserDTO userDTOS = userService.login(SecurityUserAssembler.toDTO(loginCommand));
            if (userDTOS != null) {
                //返回数据
                response.setCode(HttpStatus.OK.value());
                response.setMsg("登录成功");
                response.setData(userDTOS);
            } else {
                response.setCode(HttpStatus.UNAUTHORIZED.value());
                response.setMsg("登录失败");
                response.setData(null);
            }
            return response;
        } catch (Exception e) {
            response = new Response(e);
        }
        return response;
    }


    public Response addUser(SecurityUserDTO userDTO) {
        Response response = new Response();

        try {
            ValidationUtil.getInstance().validateParams(userDTO);
            int flag = userService.addUser(userDTO);
            if (flag > 0) {
                response.setCode(HttpStatus.CREATED.value());
                response.setMsg("添加成功");
                response.setData(flag);
            } else {
                response.setCode(HttpStatus.UNAUTHORIZED.value());
                response.setMsg("添加失败");
                response.setData(null);
            }
            return response;
        } catch (Exception e) {
            response = new Response(e);
        }
        return response;
    }

    public Response getUserByUserName(String userName) {
        Response response = new Response();

        try {
            int flag = userService.getUserCountByUserName(userName);
            if (flag == 0) {
                response.setCode(HttpStatus.OK.value());
                response.setMsg("用户不存在！可以添加！");
                response.setData(flag);
            } else {
                response.setCode(HttpStatus.CONFLICT.value());
                response.setMsg("用户已存在！不可以添加！");
                response.setData(null);
            }
            return response;
        } catch (Exception e) {
            response = new Response(e);
        }
        return response;
    }


    public Response getUserByPhone(String userPhone) {
        Response response = new Response();
        try {
            int flag = userService.getUserByPhone(userPhone);
            if (flag > 0) {
                response.setCode(HttpStatus.OK.value());
                response.setMsg("手机号正确！可以修改密码！");
            } else {
                response.setCode(HttpStatus.UNAUTHORIZED.value());
                response.setMsg("对不起！手机号错误！");
            }
        } catch (Exception e) {
            response = new Response(e);
        }
        return response;
    }

    public Response updateUser(SecurityUserDTO userDTO) {
        Response response = new Response();
        try {
            int flag = userService.updateUser(userDTO);
            if (flag > 0) {
                response.setCode(HttpStatus.OK.value());
                response.setMsg("修改用户成功！");
            } else {
                response.setCode(HttpStatus.UNAUTHORIZED.value());
                response.setMsg("修改用户失败！");
            }
        } catch (Exception e) {
            response = new Response(e);
        }
        return response;
    }

    public Response listByCondition(QueryUser queryUser) {
        Response response = new Response();

        try {
            List<SecurityUserDTO> userDTOPage = userService.listByCondition(queryUser);
            if (userDTOPage != null && userDTOPage.size() > 0) {
                response.setMsg("查询成功");
                response.setData(userDTOPage);
            } else {
                response.setMsg("无查询结果");
                response.setData(null);
            }
            response.setCode(HttpStatus.OK.value());
            return response;
        } catch (Exception e) {
            response = new Response(e);
        }
        return response;
    }

    public Response getUserByUnitId(String unitId) {
        Response response = new Response();

        try {
            response.setData(userService.getUserByUnitId(unitId));
            response.setCode(HttpStatus.OK.value());
            response.setMsg("获取成功！");
        } catch (Exception e) {
            response = new Response(e);
        }
        return response;
    }

    public Response delete(Integer userId) {
        Response response = new Response();

        try {
            int flag = userService.delete(userId);
            if (flag > 0) {
                response.setMsg("删除成功");
                response.setData(flag);
                response.setCode(HttpStatus.UNAUTHORIZED.value());
                response.setMsg("删除失败");
                response.setData(0);
            }
            response.setCode(HttpStatus.OK.value());
            return response;
        } catch (Exception e) {
            response = new Response(e);
        }
        return response;
    }

}
