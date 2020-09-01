package com.irene.spring.users.controller;

import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.irene.spring.users.dto.UsersDto;
import com.irene.spring.users.service.UsersService;

@Controller
public class UsersController {
	@Autowired
	private UsersService service;
	
	//회원 가입 폼 요청 처리 
	@RequestMapping("/users/signup_form")
	public String signupForm() {
		
		return "users/signup_form";
	}
	
	//아이디가 존재하는지 여부를 처리하는 요청처리
	@RequestMapping("/users/checkid")
	@ResponseBody
	public Map<String, Object> checkid(@RequestParam String inputId){
		return service.isExistId(inputId);
	}
	
	//회원 가입 요청 처리
	@RequestMapping("/users/signup")
	public ModelAndView signup(UsersDto dto, ModelAndView mView) {
		service.addUser(dto);
		mView.setViewName("users/signup");
		return mView;
	}
	@RequestMapping("/users/loginform")
	public String loginform(HttpServletRequest request) {
		String url=request.getParameter("url");
		if(url==null){
			String cPath=request.getContextPath();
			url=cPath+"/home.do";
		}
		request.setAttribute("url", url);
		return "users/loginform";
	}
	
	@RequestMapping("/users/login")
	public ModelAndView login(UsersDto dto, ModelAndView mView,
			HttpSession session, HttpServletRequest request) {
		String url=request.getParameter("url");
		String encodedUrl=URLEncoder.encode(url); 
		mView.addObject("url", url);
		mView.addObject("encodedUrl", encodedUrl);
		
		//service 객체를 이용해서 로그인 관련 처리를 한다.
		service.loginProcess(dto, mView, session);
		mView.setViewName("users/login");
		return mView;
	}
	
	@RequestMapping("/users/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/home.do";
	}
	
	//개인정보 보기 요청 처리
	@RequestMapping("/users/private/info")
	public ModelAndView info(HttpServletRequest request,ModelAndView mView) {
		service.getInfo(request.getSession(), mView);
		mView.setViewName("users/private/info");
		return mView;
	}
	@RequestMapping("/users/private/delete")
	public ModelAndView delete(HttpServletRequest request,
			ModelAndView mView) {
		service.deleteUser(request.getSession());
		mView.setViewName("users/private/delete");
		return mView;
	}
	//회원정보 수정폼 요청 처리 
	@RequestMapping("/users/private/updateform")
	public ModelAndView updateForm(HttpServletRequest request,
			ModelAndView mView) {
		service.getInfo(request.getSession(), mView);
		mView.setViewName("users/private/updateform");
		return mView;
	}
	
	// ajax 프로필 사진 업로드 요청 처리
	@RequestMapping("/users/private/profile_upload")
	@ResponseBody
	public Map<String, Object> profile_upload
				(HttpServletRequest request,@RequestParam MultipartFile image){
		Map<String, Object> map=service.saveProfileImage(request, image);
		return map;
	}
	
	//개인 정보 수정 반영 요청 처리
	@RequestMapping("/users/private/update")
	public ModelAndView update(HttpServletRequest request, 
			UsersDto dto, ModelAndView mView) {
		service.updateUser(request.getSession(), dto);
		mView.setViewName("redirect:/users/private/info.do");
		return mView;
	}
	
	@RequestMapping("/users/private/pwd_updateform")
	public ModelAndView pwdUpdateform(ModelAndView mView) {
		
		mView.setViewName("users/private/pwd_updateform");
		return mView;
	}
	
	@RequestMapping("/users/private/pwd_update")
	public ModelAndView pwdUpdate(ModelAndView mView,
			UsersDto dto, HttpServletRequest request) {
		service.updateUserPwd(request.getSession(), dto, mView);
		mView.setViewName("users/private/pwd_update");
		return mView;
	}
}
