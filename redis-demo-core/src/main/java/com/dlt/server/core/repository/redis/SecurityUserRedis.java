package com.dlt.server.core.repository.redis;

import com.dlt.server.common.constant.ConstPunctuation;
import com.dlt.server.common.enums.ERedisEntityPrefixType;
import com.dlt.server.core.repository.redis.base.BaseRepositoryRedisAsInteger;
import com.dlt.server.facade.dto.SecurityUserDTO;

import javax.inject.Named;

/**
 * 查询的属性unitType、userName
 * Created by xuliugen on 2017/5/2.
 */
@Named
public class SecurityUserRedis extends BaseRepositoryRedisAsInteger<SecurityUserDTO> {

    /**
     * 组装存入Redis的Key的抽象方法，有子类实现具体的逻辑
     * @param userDTO
     * @return
     */
    @Override
    protected String assembleRedisKey(SecurityUserDTO userDTO) {
        return assembleRedisKey(userDTO.getUnitType(), userDTO.getUserName(), userDTO.getId());
    }

    @Override
    protected Class getEntityClassType() {
        return SecurityUserDTO.class;
    }

    /**
     * 组装Key的Pattern用于查询匹配的Redis的Key集合
     * @param userDTO
     * @return
     */
    @Override
    protected String assembleRedisKeyPattern(SecurityUserDTO userDTO) {
        String keyPrefix = assembleRedisKeyPrefix(userDTO);
        return keyPrefix + ConstPunctuation.ASTERISK;
    }

    /**
     * @param unitType 查询条件unitType
     * @param userName 查询条件userName
     * @param id
     * @return
     */
    private String assembleRedisKey(String unitType, String userName, Integer id) {
        return assembleRedisKeyPrefix(unitType, userName) + assemberIdForKey(id);
    }

    /**
     * 组装没有Id的Redis的KeyPattern
     * @param userDTO
     * @return
     */
    private String assembleRedisKeyPrefix(SecurityUserDTO userDTO) {
        return assembleRedisKeyPrefix(userDTO.getUnitType(), userDTO.getUserName());
    }

    /**
     * 使用SecurityUserDTO的unitType和userName来组装Redis的key的前缀值
     * @param unitType
     * @param userName
     * @return
     */
    private String assembleRedisKeyPrefix(String unitType, String userName) {
        String keyPrefix = ConstSecurityUserDTO.PREFIX_
                + (unitType == null ? "*" + ConstPunctuation.UNDERLINE : unitType + ConstPunctuation.UNDERLINE)
                + (userName == null ? "*" + ConstPunctuation.UNDERLINE : userName + ConstPunctuation.UNDERLINE);
        return keyPrefix;
    }

    /***
     *
     */
    public interface ConstSecurityUserDTO {
        String PREFIX = ERedisEntityPrefixType.SECURITY_USER.getValue();
        String PREFIX_ = PREFIX + ConstPunctuation.UNDERLINE;
        String PAGE = PREFIX_ + "PAGE";
        String CREATE = PREFIX_ + "CREATE";
        String UPDATE = PREFIX_ + "UPDATE";
        String REMOVE = PREFIX_ + "REMOVE";
    }

}
