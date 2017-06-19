package com.dlt.server.core.repository.redis.impl;

import com.dlt.server.base.BaseTest;
import com.dlt.server.core.repository.redis.SecurityUserRedis;
import com.dlt.server.facade.dto.SecurityUserDTO;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Set;

/**
 * Created by xuliugen on 2017/6/5.
 */
public class SecurityUserRedisImplTest extends BaseTest {

    @Inject
    private SecurityUserRedis securityUserRedis;

    @Test
    public void testInsert() {
        SecurityUserDTO userDTO = new SecurityUserDTO();
        userDTO.setId(4);
        userDTO.setUserName("xuliugen");
        userDTO.setRealName("徐刘根");
        userDTO.setTelephone(15928536760L);
        userDTO.setDepartmentId("123");
        userDTO.setDepartmentName("测试部门");
        userDTO.setRoleId("2");
        userDTO.setRoleName("测试角色");
        userDTO.setUnitType("供电所");
        userDTO.setUnitName("测试单位");
        userDTO.setPassword("123456");

        int flag = securityUserRedis.insertSelective(userDTO);
        System.out.println(flag);
    }

    @Test
    public void testqUERY() {
        SecurityUserDTO userDTO = new SecurityUserDTO();
        userDTO.setId(1);
        userDTO.setUserName("xuliugen");
        userDTO.setDepartmentId("123");
        userDTO.setUnitType("供电所");
        userDTO.setUnitName("测试单位");
        Set<SecurityUserDTO> set = securityUserRedis.listByCondition(userDTO);
        System.out.println(set.size());
    }
}
