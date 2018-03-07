package com.shouxiu.wanandroid.network.bean;

/**
 * @author yeping
 * @date 2018/2/28 14:41
 * @description ${TODO}
 */

public class LzyResponse<T> {

    /**
     * data : {"collectIds":[],"email":"","icon":"","id":447,"password":"ping112233","type":0,"username":"shouxiu_yp@163.com"}
     * errorCode : 0
     * errorMsg :
     */

    private T data;
    private int errorCode;
    private String errorMsg;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
