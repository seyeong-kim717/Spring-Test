package com.irene.spring.file.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.irene.spring.file.dto.FileDto;
import com.irene.spring.file.service.FileService;

@Controller
public class FileController {
	@Autowired
	private FileService fileService;
	
	
	@RequestMapping("/file/list")
	public ModelAndView list(HttpServletRequest request,
			ModelAndView mView) {
		fileService.getList(request);
		mView.setViewName("file/list");
		return mView;
	}
	
	@RequestMapping("/file/private/upload_form")
	public ModelAndView uploadForm(ModelAndView mView) {
		
		mView.setViewName("file/private/upload_form");
		return mView;
	}
	//파일 업로드 요청 처리 
	@RequestMapping(value = "/file/private/upload", 
			method = RequestMethod.POST)
	public ModelAndView upload(FileDto dto, ModelAndView mView, 
			HttpServletRequest request) {
		fileService.saveFile(dto, mView, request);
		mView.setViewName("file/private/upload");
		return mView;
	}
	
	//파일 다운로드 요청 처리
	@RequestMapping("/file/download")
	public ModelAndView download(@RequestParam int num,
			ModelAndView mView) {
		fileService.getFileData(num, mView);
		mView.setViewName("fileDownView");
		return mView;
	}
	//파일 삭제 요청 처리 
	@RequestMapping("/file/private/delete")
	public ModelAndView delete(@RequestParam int num,
			ModelAndView mView, HttpServletRequest request) {
		fileService.deleteFile(num, request);
		mView.setViewName("redirect:/file/list.do");
		return mView;
	}
}
