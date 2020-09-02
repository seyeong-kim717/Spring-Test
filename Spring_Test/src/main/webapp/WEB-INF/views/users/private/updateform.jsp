<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/users/private/updateform.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<style>
	#profileForm{
		display:none;
	}
	#profileImage{
		width: 70px;
		height: 70px;
		border: 1px solid #cecece;
		border-radius: 70%;
		cursor: pointer;
	}
</style>
</head>
<body>
	<div class="container">
	<h1>회원정보 수정 폼</h1>
	<a href="javascript:" id="profileLink">
		<c:choose>
			<c:when test="${empty dto.profile }">
				<svg id="profileImage"  width="1em" height="1em" viewBox="0 0 16 16" class="bi bi-person-fill" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
							<path fill-rule="evenodd" d="M3 14s-1 0-1-1 1-4 6-4 6 3 6 4-1 1-1 1H3zm5-6a3 3 0 1 0 0-6 3 3 0 0 0 0 6z"/>
				</svg>
			</c:when>
			<c:otherwise>
				<img id="profileImage" 
					src="${pageContext.request.contextPath }${dto.profile }"/>
			</c:otherwise>
		</c:choose>
	</a>	
	<form action="update.do" method="post">
		<input type="hidden" name="profile" id="profile" 
				value="${dto.profile }"/>
		<div class="form-group">
			<label for="id">아이디</label>
			<input class="form-control" type="text" id="id" value="${dto.id }" disabled/>
		</div>
		<div class="form-group">
			<label for="email">이메일</label>
			<input class="form-control" type="text" id="email" name="email"
				 value="${dto.email }" />
		</div>
		<button class="btn btn-outline-primary" type="submit">수정확인</button>
		<button class="btn btn-outline-warning" type="reset">취소</button>
	</form>
	
	<form action="profile_upload.do" method="post" 
		enctype="multipart/form-data" id="profileForm">
		<input type="file" name="image" 
			accept=".jpg, .jpeg, .png, .JPG, .JPEG" id="image"/>
	</form>
</div>
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script src="${pageContext.request.contextPath }/resources/js/jquery.form.min.js"></script>
<script>
	$("#profileLink").on("click", function(){
		$("#image").click();
	});
	
	$("#image").on("change", function(){
		$("#profileForm").submit();
	});
	
	$("#profileForm").ajaxForm(function(data){
		$("#profileImage").remove();
		$("<img/>")
		.attr("id", "profileImage")
		.attr("src", "${pageContext.request.contextPath }"+data.imageSrc)
		.appendTo("#profileLink");
		
		$("#profile").val(data.imageSrc);
	});
</script>
</body>
</html>