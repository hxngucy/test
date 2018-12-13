package com.gu.entity;

import java.io.Serializable;
import java.util.Date;


public class UserInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String username;

    private String password;

    private Date brithday;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBrithday() {
		return brithday;
	}

	public void setBrithday(Date birthday) {
		this.brithday = birthday;
	}

	public UserInfo(Integer id, String username, String password, Date birthday) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.brithday = birthday;
	}

	public UserInfo() {
		super();
	}

	public UserInfo(String username, String password, Date brithday) {
		super();
		this.username = username;
		this.password = password;
		this.brithday = brithday;
	}
}
