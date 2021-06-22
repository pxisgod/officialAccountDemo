package com.px.oad.vo;

import java.io.Serializable;


public class JsonBean implements Serializable {

    public static final Integer CODE_SUCCESS = 0;
    public static final String MSG_SUCCESS = "ok";

    public static final JsonBean success = new JsonBean();

    public static JsonBean newBean(){
        return new JsonBean();
    }


    private Integer errorcode  = CODE_SUCCESS;


    private String errmsg =MSG_SUCCESS;


    private Object data;

    public JsonBean() {
    }

    public JsonBean(Integer code, String message) {
        this.errorcode = code;
        this.errmsg = message;
    }


    public Integer getErrorcode() {
        return errorcode;
    }

    public void setErrorcode(Integer code) {
        this.errorcode = code;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String message) {
        this.errmsg = message;
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
        this.setErrorcode(500);
        this.setErrmsg(reason);
        this.data = null;
    }

    /**
     * 根据失败原因调用该方法
     * @param reason
     */
    public void success(String reason){
        this.setErrorcode(0);
        this.setErrmsg(reason);
    }


    @Override
    public String toString() {
        return "JsonBean [errorcode=" + errorcode + ", errmsg=" + errmsg + ", data=" + data + "]";
    }


}
