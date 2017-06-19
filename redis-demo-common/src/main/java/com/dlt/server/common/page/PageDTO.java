package com.dlt.server.common.page;

/**
 * 用于保存查询条件再返回之后的url请求条件地址
 * Created by xuliugen on 2016/3/17.
 */
public class PageDTO<T> extends Page {

    /**
     * 请求路径和参数
     */
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}