package com.xwbing.controller.sys;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 项目名称: boot-module-demo
 * 创建时间: 2018/2/26 14:09
 * 作者: xiangwb
 * 说明: 登陆首页
 */
@Controller
public class IndexController
{
	@RequestMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	@GetMapping("/unauthor")
	public String unauthor() {
		return "redirect:login";
	}
	@GetMapping("/index")
	public String index() {
		return "suecess.html";
	}
}
