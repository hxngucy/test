package com.gu.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gu.dao.UserInfoDao;
import com.gu.entity.UserInfo;
import com.gu.enums.RedisConstant;
import com.gu.service.UserInfoServer;
import com.gu.utils.SupportRedisUtils;


@Service("userInfoService")
public class UserInfoServiceImpl implements UserInfoServer{

	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private SupportRedisUtils supportRedisUtils;
	
	private static final Logger logger = LoggerFactory.getLogger(UserInfoServiceImpl.class);
	
	private static final int ONE_DAY_EXPIRE = 86400;
	@Override
	public void insert(List<UserInfo> userInfoList) {
		UserInfo user = new UserInfo();
		user.setBrithday(new Date());
		user.setUsername("gu");
		user.setPassword("gcy36lyh99");
		UserInfo user1 = new UserInfo();
		user1.setBrithday(new Date());
		user1.setUsername("gucy");
		user1.setPassword("gcy36lyh99");
		userInfoList.add(user1);
		userInfoList.add(user);
		try {
			for (UserInfo userInfo : userInfoList) {
				userInfoDao.insert(userInfo);
			}
			System.out.println("==========success==========");
		} catch (Exception e) {
			logger.error(e.getMessage());
			System.out.println("============fail===========");
		}
	}

	/**
	 * 查询
	 */
	@Override
	public UserInfo getById(Integer id) {
		UserInfo user = null;
		try {
			user = 
			user = userInfoDao.getById(id);
			System.out.println("==========success==========");
		} catch (Exception e) {
			logger.error(e.getMessage());
			System.out.println("============fail===========");
		}
		if(null == user){
			return null;
		}
		return user;
	}

	/**
	 * 修改
	 */
	@Override
	public UserInfo update(UserInfo userInfo) {
		System.out.println("修改 id=" + userInfo.getId() + " 的数据");
		try {
			userInfoDao.update(userInfo);
			System.out.println("==========success==========");
		} catch (Exception e) {
			logger.error(e.getMessage());
			System.out.println("============fail===========");
		}
		return userInfo;
	}

	/**
	 * 删除
	 */
	@Override
	public void deleteById(Integer id) {
		System.out.println("删除 id=" + id + " 的数据");
		try {
			userInfoDao.deleteById(id);
			System.out.println("==========success==========");
		} catch (Exception e) {
			logger.error(e.getMessage());
			System.out.println("============fail===========");
		}
	}

	/**
	 * 添加(同时添加缓存)
	 */
	@Override
	public UserInfo save(UserInfo userInfo) {
		logger.info("=======保存数据=======");
		String key = userInfo.getUsername();
		try {
			userInfoDao.insert(userInfo);
			supportRedisUtils.setSupportHSet(RedisConstant.USER.getValue(), key, userInfo, ONE_DAY_EXPIRE);
			logger.info("==========success==========");
		} catch (Exception e) {
			logger.error("============fail===========");
			logger.error(e.getMessage());
		}
		logger.info("保存 username =" + userInfo.getUsername() + " 的数据");
		return userInfo;
	}

	/**
	 * 查找(先在缓存中查找，没有再从数据库查找)
	 */
	@Override
	public UserInfo getOne(UserInfo userInfo) {
		UserInfo user = null;
		String key = userInfo.getUsername();
		String redisUserKey = supportRedisUtils.getKeyString(RedisConstant.USER.getValue(), key);
		if(supportRedisUtils.exists(redisUserKey)){
			logger.info("=====get  from redis====");
			user = supportRedisUtils.getSupportHSet(RedisConstant.USER.getValue(), key, UserInfo.class);
			logger.info("=====json====:"+JSONObject.toJSONString(user));
		}else{
			logger.info("=====get  from db====");
			user = userInfoDao.getOne(userInfo);
		}
		if(null == user){
			logger.info("===== user is null ====");
			return null;
		}
		return user;
	}

}
