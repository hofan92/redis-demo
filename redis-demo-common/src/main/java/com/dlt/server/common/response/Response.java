package com.dlt.server.common.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.validation.ValidationException;
import java.io.Serializable;

/**
 * 领域实体返回Response
 * Created by xuliugen on 16/1/11.
 */
public class Response<T> implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(Response.class);

    /**
     * 默认的错误显示消息
     */
    private static final String DEFAULT_ERROR_MESSAGE = "Oops!不知道发生了什么错！客官对不起！";

    /**
     * 编码
     */
    private Integer code;

    /**
     * 描述信息
     */
    private String msg;

    private T data;

    /**
     * 无参构造函数，默认为成功
     */
    public Response() {

    }

    public Response(Integer code) {
        this.code = code;
    }

    public Response(String msg) {
        this.msg = msg;
    }

    public Response(Throwable throwable) {
        logger.error(throwable.getMessage(), throwable);
        if (throwable instanceof ValidationException || throwable instanceof IllegalArgumentException) {
            this.code = HttpStatus.UNAUTHORIZED.value();
            this.msg = throwable.getMessage();
        } else {
            this.code = HttpStatus.UNAUTHORIZED.value();
            this.msg = DEFAULT_ERROR_MESSAGE;
        }
    }

    public Response(Throwable throwable, Integer code) {
        logger.error(throwable.getMessage(), throwable);
        this.code = code;
        this.msg = throwable.getMessage();
    }

    /**
     * @param data
     */
    public Response(T data) {
        this.data = data;
    }

    /**
     * @param data
     */
    public Response(T data, String msg) {
        this.msg = msg;
        this.data = data;
    }

    public Response(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Response(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * @param businessResult the data to set
     */
    public void setData(T businessResult) {
        this.data = businessResult;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
    }
}
