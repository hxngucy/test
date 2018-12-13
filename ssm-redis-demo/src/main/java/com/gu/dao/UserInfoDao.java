package com.gu.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gu.entity.UserInfo;


@Repository
public interface UserInfoDao {

	public void insert(@Param("userInfo")UserInfo userInfo);
    
    public UserInfo getById(@Param("id")Integer id);
    
    public void update(@Param("userInfo")UserInfo userInfo);
    
    public void deleteById(@Param("id")Integer id);
    
    public UserInfo getOne(@Param("userInfo")UserInfo userInfo);
}
