<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/users/loginform.jsp</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
</head>
<body>
<div class="container">
	<h1>로그인 폼</h1>
	<form action="login.do" method="post">
		<input type="hidden" name="url" value="${url }"  />
		<div class="form-group">
			<label for="id">아이디</label>
			<input type="text" class="form-control" name="id" id="id" />
		</div>
		<div class="form-group">
			<label for="pwd">비밀번호</label>
			<input type="password" class="form-control" name="pwd" id="pwd" />
		</div>
		<button class="btn btn-primary" type="submit">로그인</button>
	</form>
</div>
</body>
</html>