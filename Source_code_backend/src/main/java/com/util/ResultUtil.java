package com.util;

import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

public class ResultUtil {
    private Boolean success;

    private Integer code;

    private String message;

    private Map<String, Object> data = new HashMap<>();


    private ResultUtil() {
    }

    /**
     *
     * @param success 
     * @param code    
     * @param message 
     */
    private ResultUtil(Boolean success, Integer code, String message) {
        this.setSuccess(success);
        this.setCode(code);
        this.setMessage(message);
    }

    /**
     *
     * @return 
     */
    public static ResultUtil ok() {
        return new ResultUtil(true, HttpStatus.SC_OK, "Success");
    }

    /**
     *
     * @param success 
     * @param code   
     * @param message 
     * @return 
     */
    public static ResultUtil ok(Boolean success, Integer code, String message) {
        return new ResultUtil(success, code, message);
    }

    /**
     *
     * @return 
     */
    public static ResultUtil error() {
        return new ResultUtil(false, HttpStatus.SC_INTERNAL_SERVER_ERROR, "Fail");
    }

    /**
     *
     * @param success 
     * @param code    
     * @param message 
     * @return 
     */
    public static ResultUtil error(Boolean success, Integer code, String message) {
        return new ResultUtil(success, code, message);
    }

    /**
     *
     * @param success 
     * @return 
     */
    public ResultUtil success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    /**
     *
     * @param code 
     * @return 
     */
    public ResultUtil code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     *
     * @param message 
     * @return 
     */
    public ResultUtil message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * @param map 
     * @return 
     */
    public ResultUtil data(Map<String, Object> map) {
        this.getData().putAll(map);
        return this;
    }

    /**
     *
     * @param key   
     * @param value 
     * @return 
     */
    public ResultUtil data(String key, Object value) {
        this.getData().put(key, value);
        return this;
    }


    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
