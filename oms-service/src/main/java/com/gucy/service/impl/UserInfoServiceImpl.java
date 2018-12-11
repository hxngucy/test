package com.gucy.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gucy.dao.UserInfoDao;
import com.gucy.entitys.UserInfo;
import com.gucy.service.UserInfoService;

@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoDao userInfoDao;
	
	private static final Logger logger = LogManager.getLogger(UserInfoServiceImpl.class);
	
	@Override
	public UserInfo getOne(Long id) {
		if(null == id){
			return null;
		}
		UserInfo user = null;
		try {
			user = userInfoDao.getOne(id);
		} catch (Exception e) {
			logger.error("根据id查询用户信息出错！");
			logger.error(e.getMessage());
			return null;
		}
		return user;
	}

}
