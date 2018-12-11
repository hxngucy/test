package com.gucy.controller;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gucy.entitys.UserInfo;
import com.gucy.service.UserInfoService;

@Controller
@RequestMapping("/user")
public class UserInfoController {

	public static final Logger logger = LogManager.getLogger(UserInfoController.class);
	@Autowired
	public UserInfoService userInfoService;
	
	@RequestMapping({"","/init","/index"})
	public String init(){
		return "index";
	}
	
	@RequestMapping("/login")
	public String login(UserInfo userInfo,ModelMap model){
		
		UserInfo user = userInfoService.getOne(userInfo.getId());
		if(null == user){
			model.addAttribute("message", "查询结果为空！");
		}
		model.addAttribute("user", user);
		return "welcome";
	}
}
