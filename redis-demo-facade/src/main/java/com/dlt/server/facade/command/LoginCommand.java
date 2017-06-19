package com.dlt.server.facade.command;

import javax.validation.constraints.NotNull;

/**
 * Created by xuliugen on 2017/5/20.
 */
public class LoginCommand {

    /**
     * 用户名，不是真实姓名
     */
    @NotNull
    private String userName;

    /**
     * 用户密码
     */
    @NotNull
    private String password;

    /**
     * 用户手机号码
     */
    private Long telphone;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getTelphone() {
        return telphone;
    }

    public void setTelphone(Long telphone) {
        this.telphone = telphone;
    }
}
