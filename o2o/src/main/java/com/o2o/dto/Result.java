package com.o2o.dto;

/**
 * 封装json对象，所有返回结果都使用它
 * @param <T>
 */
public class Result<T> {
    private boolean success; // 是否成功
    private T data; // 成功时返回的数据
    private String errMsg; // 错误信息
    private int errCode;

    public Result() {
    }

    public Result(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public Result(boolean success, String errMsg, int errCode) {
        this.success = success;
        this.errMsg = errMsg;
        this.errCode = errCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public int getErrCode() {
        return errCode;
    }

    public void setErrCode(int errCode) {
        this.errCode = errCode;
    }
}
