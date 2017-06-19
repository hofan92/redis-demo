package com.dlt.server.core.service.impl;

import com.dlt.server.common.util.CollectionUtils;
import com.dlt.server.common.util.encry.MD5EncryptUtil;
import com.dlt.server.core.aspect.db.ReadWriteData;
import com.dlt.server.core.repository.mybatis.SecurityUserMapper;
import com.dlt.server.core.repository.redis.SecurityUserRedis;
import com.dlt.server.core.service.SecurityUserService;
import com.dlt.server.facade.dto.SecurityUserDTO;
import com.dlt.server.facade.query.QueryUser;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

/**
 * Created by xuliugen on 2017/5/2.
 */
@Named
public class SecurityUserServiceImpl implements SecurityUserService {

    @Inject
    private SecurityUserMapper userMapper;

    @Inject
    private SecurityUserRedis userRedis;

    @ReadWriteData("read")
    public SecurityUserDTO login(SecurityUserDTO userDTO) {
        //对密码加密处理
        userDTO.setPassword(MD5EncryptUtil.encryptPassword(userDTO.getPassword(), MD5EncryptUtil.encryptPassword(userDTO.getUserName())));
        return userMapper.selectByEntity(userDTO);
    }

    @ReadWriteData("read")
    public int getUserCountByUserName(String userName) {
        return userMapper.selectCountByUserName(userName);
    }

    @ReadWriteData("write")
    public int addUser(SecurityUserDTO userDTO) {
        userDTO.setPassword(MD5EncryptUtil.encryptPassword(userDTO.getPassword(), MD5EncryptUtil.encryptPassword(userDTO.getUserName())));
        int userId = userMapper.insertSelective(userDTO);
        //添加缓存
        userRedis.insertSelective(userDTO);
        return userId;
    }

    @ReadWriteData("read")
    public int getUserByPhone(String userPhone) {
        SecurityUserDTO userDTO = new SecurityUserDTO();
        userDTO.setTelephone(Long.parseLong(userPhone));
        return userMapper.selectByEntity(userDTO) == null ? 0 : 1;
    }

    @ReadWriteData("write")
    public int updateUser(SecurityUserDTO userDTO) {
        int flag = userMapper.updateByPrimaryKeySelective(userDTO);
        if (flag > 0) {
            userRedis.updateByPrimaryKey(userDTO);
        }
        return flag;
    }

    @Override
    public int delete(Integer userId) {
        int flag = userMapper.deleteByPrimaryKey(userId);
        if (flag > 0) {
            userRedis.deleteByPrimaryKey(userId);
        }
        return flag;
    }

    /**
     * 根据供电所的单位ID获取供电所下边的所有人员
     * @return
     */
    @ReadWriteData("read")
    public List<SecurityUserDTO> listByCondition(QueryUser queryUser) {
        //从Redis查询数据
        Set<SecurityUserDTO> usersFromRedis = userRedis.listByCondition(new SecurityUserDTO(queryUser.getUserName(), queryUser.getUnitType()));
        if (usersFromRedis.isEmpty()) {
            //从MySQL中查询数据
            List<SecurityUserDTO> userDTOS = userMapper.listByCondition(queryUser);
            return userDTOS;
        }
        return (List<SecurityUserDTO>) CollectionUtils.setToArrayList(usersFromRedis);
    }


    @Override
    public int getUserByUnitId(String unitId) {
        String id = userMapper.selectCountByUnitId(unitId);
        if (id != null && !id.equals("")) {
            return 1;
        }
        return 0;
    }

}
