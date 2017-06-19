package com.dlt.server.facade.impl.assembler;

import com.dlt.server.facade.command.LoginCommand;
import com.dlt.server.facade.dto.SecurityUserDTO;
import org.springframework.beans.BeanUtils;

/**
 * Created by xuliugen on 2017/5/20.
 */
public class SecurityUserAssembler {


    /**
     * 将loginCommand转换为DTO
     * @param loginCommand
     * @return
     */
    public static SecurityUserDTO toDTO(LoginCommand loginCommand) {
        SecurityUserDTO dto = new SecurityUserDTO();
        BeanUtils.copyProperties(loginCommand, dto);
        return dto;
    }
}
