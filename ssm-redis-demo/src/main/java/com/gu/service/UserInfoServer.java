package com.gu.service;

import java.util.List;

import com.gu.entity.UserInfo;


public interface UserInfoServer {

	public void insert(List<UserInfo> userInfoList);
    
    public UserInfo getById(Integer id);
    
    public UserInfo update(UserInfo userInfo);
    
    public void deleteById(Integer id);
    
    public UserInfo save(UserInfo userInfo);
    
    public UserInfo getOne(UserInfo userInfo);
}
