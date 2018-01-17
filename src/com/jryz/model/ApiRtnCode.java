package com.jryz.model;

public class ApiRtnCode {

	/**
	 * 接口验证失败
	 */
	public final static String API_VERIFY_FAIL = "201";

	/**
	 * 数据不存在 如产品不存在 代表 产品的error code "3**"
	 */
	public final static String DATA_NOT_EXIST = "103";

	/**
	 * 参数不全
	 */
	public final static String PARAM_NOT_EXIST = "102";
	
	/**
	 * 数据校验不通过 如 秘钥错误 密码错误
	 */
	public final static String DATA_VALIDATION_FAILURE = "101";
	
	/*
	 * ** 数据不可用 / 资源被占用 如 账户被锁定
	 */
	public final static String DATA_UNAVAILABLE = "202";

	/*
	 * 无权限访问 如 无产品权限
	 */
	public final static String NO_ACCESS = "203";
	
	/**
	 * 服务器错误
	 */
	public final static String SERVICE_ERROR = "300";
	
	/**
	 * 第三方接口异常
	 */
	public final static String EXTERNAL_API_ERROR = "301";
}
