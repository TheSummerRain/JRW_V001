package com.jryz.model;


import java.io.Serializable;

public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = -740780238124331993L;

    private String code = "";
	private String msg = "";
	private boolean success;
 	private T data;

    public ApiResult() {
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public ApiResult<T> setMsg(String msg) {
		this.msg = msg;
		return this;
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

	public ApiResult<T> setData(T data) {
		this.data = data;
		return this;
	}
	
}
