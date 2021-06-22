package com.px.oad.vo;

import java.io.Serializable;


public class JsonBean implements Serializable {

    public static final String CODE_SUCCESS = "200";

    public static final JsonBean success = new JsonBean();

    public static JsonBean newBean(){
        return new JsonBean();
    }


    private String code  = CODE_SUCCESS;


    private String message;


    private Object data;

    public JsonBean() {
    }

    public JsonBean(String code, String message) {
        this.code = code;
        this.message = message;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static JsonBean getSuccess() {
        return success;
    }

    /**
     * 根据失败原因调用该方法
     * @param reason
     */
    public void fail(String reason){
        this.setCode("500");
        this.setMessage(reason);
        this.data = null;
    }

    /**
     * 根据失败原因调用该方法
     * @param reason
     */
    public void success(String reason){
        this.setCode("200");
        this.setMessage(reason);
    }


    @Override
    public String toString() {
        return "JsonBean [code=" + code + ", message=" + message + ", data=" + data + "]";
    }


}
