package com.gucy.dao;

import org.apache.ibatis.annotations.Param;

import com.gucy.entitys.UserInfo;

public interface UserInfoDao {

	public UserInfo getOne(@Param("id") Long id);
}
