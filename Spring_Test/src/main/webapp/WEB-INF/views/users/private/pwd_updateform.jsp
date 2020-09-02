<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/users/private/pwd_updateform.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
</head>
<body>
<div class="container">
	<h1>비밀번호 수정 폼</h1>
	<form action="pwd_update.do" method="post" id="myForm">
		<div class="form-group">
			<label for="pwd">기존 비밀번호</label>
			<input class="form-control" type="password" name="pwd" id="pwd"/>
		</div>
		<div class="form-group">
			<label for="newPwd">새 비밀번호</label>
			<input class="form-control" type="password" name="newPwd" id="newPwd"/>
		</div>
		<div class="form-group">
			<label for="newPwd2">새 비밀번호 확인</label>
			<input class="form-control" type="password" name="newPwd2" id="newPwd2"/>
		</div>
		<button class="btn btn-outline-primary" type="submit">수정하기</button>
	</form>
</div>
<script src="${pageContext.request.contextPath }/resources/js/jquery-3.5.1.js"></script>
<script>
	$("#myForm").on("submit", function(){
		var pwd1=$("#newPwd").val();
		var pwd2=$("#newPwd2").val();
		if(pwd1 != pwd2){
			alert("새로운 비밀번호가 일치 하지 않아요");
			$("#newPwd").val("").focus();
			$("#newPwd2").val("");
			return false;
		}
	});
</script>
</body>
</html>