package com.px.oad.vo;

import java.io.Serializable;


public class JsonBean implements Serializable {

    public static final Integer CODE_SUCCESS = 0;
    public static final String MSG_SUCCESS = "ok";

    public static final JsonBean success = new JsonBean();

    public static JsonBean newBean(){
        return new JsonBean();
    }


    private Integer errcode  = CODE_SUCCESS;


    private String errmsg =MSG_SUCCESS;


    private Object data;

    public JsonBean() {
    }

    public JsonBean(Integer code, String message) {
        this.errcode = code;
        this.errmsg = message;
    }


    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer code) {
        this.errcode = code;
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
        this.setErrcode(500);
        this.setErrmsg(reason);
        this.data = null;
    }

    /**
     * 根据失败原因调用该方法
     * @param reason
     */
    public void success(String reason){
        this.setErrcode(0);
        this.setErrmsg(reason);
    }


    @Override
    public String toString() {
        return "JsonBean [errcode=" + errcode + ", errmsg=" + errmsg + ", data=" + data + "]";
    }


}
