package com.irene.spring.users.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.irene.spring.users.dao.UsersDao;
import com.irene.spring.users.dto.UsersDto;

@Service
public class UsersServiceImpl implements UsersService{
	@Autowired
	private UsersDao dao;

	@Override
	public Map<String, Object> isExistId(String inputId) {
		boolean isExist=dao.isExist(inputId);
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("isExist", isExist);
		return map;
	}

	@Override
	public void addUser(UsersDto dto) {
		String inputPwd=dto.getPwd(); 
		BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
		String encodedPwd=encoder.encode(inputPwd); 
		dto.setPwd(encodedPwd);
		 
		dao.insert(dto);
	}

	@Override
	public void loginProcess(UsersDto dto, ModelAndView mView, 
			HttpSession session) {

		boolean isValid=false;
		UsersDto resultDto=dao.getData(dto.getId());
		if(resultDto != null) {
			String encodedPwd=resultDto.getPwd();
			String inputPwd=dto.getPwd();
			isValid=BCrypt.checkpw(inputPwd, encodedPwd);
		}
		
		if(isValid) {
			session.setAttribute("id", dto.getId());
			mView.addObject("isSuccess", true);
		}else {
			mView.addObject("isSuccess", false);
		}
	}

	@Override
	public void getInfo(HttpSession session, ModelAndView mView) {
		String id=(String)session.getAttribute("id");
		UsersDto dto=dao.getData(id);
		mView.addObject("dto", dto);
	}

	@Override
	public void deleteUser(HttpSession session) {
		String id=(String)session.getAttribute("id");
		dao.delete(id);
		session.invalidate();
	}

	@Override
	public Map<String, Object> saveProfileImage(HttpServletRequest request, MultipartFile mFile) {
		String orgFileName=mFile.getOriginalFilename();
		String realPath=request.getServletContext().getRealPath("/upload");
		String filePath=realPath+File.separator;
		File upload=new File(filePath);
		if(!upload.exists()) {
			upload.mkdir();
		}
		String saveFileName=
				System.currentTimeMillis()+orgFileName;
		try {
			mFile.transferTo(new File(filePath+saveFileName));
			System.out.println(filePath+saveFileName);
		}catch(Exception e) {
			e.printStackTrace();
		}
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("imageSrc","/upload/"+saveFileName);
		
		return map;
	}

	@Override
	public void updateUser(HttpSession session, UsersDto dto) {
		String id=(String)session.getAttribute("id");
		dto.setId(id);
		dao.update(dto);
	}
	
	@Override
	public void updateUserPwd(HttpSession session, UsersDto dto, ModelAndView mView) {
		String id=(String)session.getAttribute("id");
		dto.setId(id);
		boolean isSuccess=false;
		UsersDto resultDto=dao.getData(id);
		String encodedPwd=resultDto.getPwd();
		String inputPwd=dto.getPwd(); 
		boolean isValid=BCrypt.checkpw(inputPwd, encodedPwd);
		if(isValid) {
			BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
			String encodedNewPwd=encoder.encode(dto.getNewPwd());
			dto.setNewPwd(encodedNewPwd);
			dao.updatePwd(dto);
			isSuccess=true;
		}
		
		mView.addObject("isSuccess", isSuccess);
	}
	
}

