package com.gu.controller;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gu.entity.UserInfo;
import com.gu.service.UserInfoServer;

@Controller
@RequestMapping("userinfo")
public class UserInfoController {

	@Autowired
	private UserInfoServer userInfoService;
	private static final Logger logger = LoggerFactory.getLogger(UserInfoController.class);
	
	@RequestMapping({"","index"})
	public String index(){
		logger.info("======================");
		logger.info("*********login********");
		logger.info("======================");
		UserInfo userInfo = new UserInfo();
		userInfo.setUsername("刘备");
		userInfo.setPassword("redis125");
		userInfo.setBrithday(new Date());
		userInfoService.save(userInfo);
		return "login";
	}
	
//	@RequestMapping("save")
//	public String add(@ModelAttribute("userInfo") UserInfo userInfo){
//		userInfoService.save(userInfo);
//		return "login";
//	}
	
	/**
	 * 登录
	 * @param userInfo
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public String login(@ModelAttribute("userInfo") UserInfo userInfo,ModelMap model){
		UserInfo user = userInfoService.getOne(userInfo);
		model.addAttribute("userinfo", user);
		return "index";
	}
}
