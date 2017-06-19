package com.dlt.server.facade.query.base;

import com.alibaba.fastjson.JSONObject;
import com.dlt.server.common.page.Page;

import java.io.Serializable;

/**
 * Created by xuliugen on 16/4/23.
 */
public class QueryBaseCond implements Serializable {

    /**
     * 分页相关信息
     */
    private Page page = new Page();

    public Page getPage() {
        if (page == null) {
            page = new Page();
        }
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
