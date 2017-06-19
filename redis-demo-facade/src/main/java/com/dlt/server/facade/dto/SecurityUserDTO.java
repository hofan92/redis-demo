package com.dlt.server.facade.dto;

import com.dlt.server.common.domain.BaseEntityIdAsInteger;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class SecurityUserDTO extends BaseEntityIdAsInteger {

    /**
     * 用户真实姓名
     */
    @NotNull(message = "真实姓名不可以为空")
    @Size(min = 2, max = 5)
    private String realName;

    /**
     * 用户密码
     */
    @NotNull(message = "密码不可以为空")
    private String password;

    /**
     * 用户名，不是真实姓名，限制用户名必须是字母、数字或者'.'的组合，不能包含中文
     */
    @NotNull
    @Size(min = 2, max = 10)
    private String userName;

    /**
     * 用户地址
     */
    @NotNull
    private String userAddress;

    /**
     * 职位级别
     */
    private Integer occupationLevel;

    /**
     * 所属单位ID
     */
    @NotNull
    private String unitId;

    /**
     * 公司名称
     */
    @NotNull
    private String unitName;

    /**
     * 单位类型：电力公司、施工公司、设计公司
     */
    @NotNull
    private String unitType;

    /**
     * 所属部门ID
     */
    @NotNull
    private String departmentId;

    /**
     * 部门名称
     */
    @NotNull
    private String departmentName;

    /**
     * 角色ID
     */
    @NotNull
    private String roleId;

    /**
     * 角色名称
     */
    @NotNull
    private String roleName;

    /**
     * 用户手机号码
     */
    @NotNull
    private Long telephone;

    /**
     * 是否有效
     */
    private int valid;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 最后更新时间
     */
    private Date updateTime;

    public SecurityUserDTO() {
    }

    public SecurityUserDTO(String userName, String unitType) {
        this.userName = userName;
        this.unitType = unitType;
    }

    /**
     * 用户真实姓名
     * @return real_name 用户真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 用户真实姓名
     * @param realName 用户真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    /**
     * 用户密码
     * @return password 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 用户密码
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 用户名，不是真实姓名
     * @return user_name 用户名，不是真实姓名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 用户名，不是真实姓名
     * @param userName 用户名，不是真实姓名
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /**
     * 职位级别
     * @return occupation_level 职位级别
     */
    public Integer getOccupationLevel() {
        return occupationLevel;
    }

    /**
     * 职位级别
     * @param occupationLevel 职位级别
     */
    public void setOccupationLevel(Integer occupationLevel) {
        this.occupationLevel = occupationLevel;
    }

    /**
     * 所属单位ID
     * @return unit_id 所属单位ID
     */
    public String getUnitId() {
        return unitId;
    }

    /**
     * 所属单位ID
     * @param unitId 所属单位ID
     */
    public void setUnitId(String unitId) {
        this.unitId = unitId == null ? null : unitId.trim();
    }

    /**
     * 所属部门ID
     * @return department_id 所属部门ID
     */
    public String getDepartmentId() {
        return departmentId;
    }

    /**
     * 所属部门ID
     * @param departmentId 所属部门ID
     */
    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    /**
     * 用户手机号码
     * @return telephone 用户手机号码
     */
    public Long getTelephone() {
        return telephone;
    }

    /**
     * 用户手机号码
     * @param telephone 用户手机号码
     */
    public void setTelephone(Long telephone) {
        this.telephone = telephone;
    }

    /**
     * 是否有效
     * @return valid 是否有效
     */
    public int getValid() {
        return valid;
    }

    /**
     * 是否有效
     * @param valid 是否有效
     */
    public void setValid(int valid) {
        this.valid = valid;
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 最后更新时间
     * @return update_time 最后更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 最后更新时间
     * @param updateTime 最后更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}