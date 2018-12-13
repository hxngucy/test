package com.gu.enums;

public enum RedisConstant {

	/**
     * 运营中心缓存统一命名空间
     */
//    public final static String SUPPORT_NAME_SPACE = "Support";
    
    USER("USER");
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	RedisConstant(String value) {
		this.value = value;
	}
    
    /**
     * 运营中心缓存通过通过字典type，code查找 dict实体信息
     */
//    public final static String REDIS_PREFIX_TYPE_CODE_DICT = SUPPORT_NAME_SPACE + ": type_code_dict";
}
