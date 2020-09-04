package com.irene.spring.file.service;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import com.irene.spring.file.dao.FileDao;
import com.irene.spring.file.dto.FileDto;



@Service
public class FileServiceImpl implements FileService{
	@Autowired
	private FileDao fileDao;
	
	
	final int PAGE_ROW_COUNT=5;
	
	final int PAGE_DISPLAY_COUNT=5;
	
	@Override
	public void getList(HttpServletRequest request) {
		
		
		int pageNum=1;
			
		String strPageNum=request.getParameter("pageNum");
		if(strPageNum != null){
			pageNum=Integer.parseInt(strPageNum);
		}
	
		int startRowNum=1+(pageNum-1)*PAGE_ROW_COUNT;
		
		int endRowNum=pageNum*PAGE_ROW_COUNT;

		String keyword=request.getParameter("keyword");
		String condition=request.getParameter("condition");
		if(keyword==null){
			keyword=""; 
			condition="";
		}
		
		String encodedK=URLEncoder.encode(keyword);
		
		FileDto dto=new FileDto();
		dto.setStartRowNum(startRowNum);
		dto.setEndRowNum(endRowNum);
		
		if(!keyword.equals("")){ 
			if(condition.equals("title_filename")){
				dto.setTitle(keyword);
				dto.setOrgFileName(keyword);	
			}else if(condition.equals("title")){
				dto.setTitle(keyword);
			}else if(condition.equals("writer")){
				dto.setWriter(keyword);
			}
		}
	
		List<FileDto> list=fileDao.getList(dto);
	
		int totalRow=fileDao.getCount(dto);
		
		
		int totalPageCount=
				(int)Math.ceil(totalRow/(double)PAGE_ROW_COUNT);
	
		int startPageNum=
			1+((pageNum-1)/PAGE_DISPLAY_COUNT)*PAGE_DISPLAY_COUNT;
		
		int endPageNum=startPageNum+PAGE_DISPLAY_COUNT-1;
		
		if(totalPageCount < endPageNum){
			endPageNum=totalPageCount; 
		}
		
		
		request.setAttribute("list", list);
		request.setAttribute("startPageNum", startPageNum);
		request.setAttribute("endPageNum", endPageNum);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("totalPageCount", totalPageCount);
		request.setAttribute("condition", condition);
		request.setAttribute("keyword", keyword);
		request.setAttribute("encodedK", encodedK);		
	}

	@Override
	public void saveFile(FileDto dto, ModelAndView mView, 
				HttpServletRequest request) {
		
		MultipartFile myFile=dto.getMyFile();
		
		String orgFileName=myFile.getOriginalFilename();
		
		long fileSize=myFile.getSize();
		
		
		String realPath=request.getServletContext().getRealPath("/upload");
		
		String filePath=realPath+File.separator;
		
		File upload=new File(filePath);
		if(!upload.exists()) {
			upload.mkdir(); 
		}
		
		String saveFileName=
				System.currentTimeMillis()+orgFileName;
		try {
			myFile.transferTo(new File(filePath+saveFileName));
			System.out.println(filePath+saveFileName);
		}catch(Exception e) {
			e.printStackTrace();
		}
		String id=(String)request.getSession().getAttribute("id");
		dto.setWriter(id); 
		dto.setOrgFileName(orgFileName);
		dto.setSaveFileName(saveFileName);
		dto.setFileSize(fileSize);
		
		fileDao.insert(dto);
		
		mView.addObject("dto", dto);
	}

	@Override
	public void getFileData(int num, ModelAndView mView) {
		FileDto dto=fileDao.getData(num);
		mView.addObject("dto", dto);
	}

	@Override
	public void deleteFile(int num, HttpServletRequest request) {
		FileDto dto=fileDao.getData(num);
		String saveFileName=dto.getSaveFileName();
		String path=request.getServletContext().getRealPath("/upload")+
				File.pathSeparator+saveFileName;
		new File(path).delete();
		fileDao.delete(num);
	}
	
}

