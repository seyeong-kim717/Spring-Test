<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/users/signup_form</title>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.css" />
<script src="${pageContext.request.contextPath }/resources/js/angular.min.js"></script>
<script>
var myApp=angular.module("myApp", []);
myApp.controller("formCtrl", function($scope, $http){
	$scope.idChanged=function(){
		$http({
			url:"checkid.do",
			method:"get",
			params:{inputId:$scope.id}
		})
		.success(function(data){
			$scope.myForm.id.$valid = !data.isExist;
			$scope.myForm.id.$invalid = data.isExist;
		});
	};
	$scope.pwdChanged=function(){ 
		$scope.myForm.pwd.$valid = $scope.pwd==$scope.pwd2;
		$scope.myForm.pwd.$invalid = $scope.pwd!=$scope.pwd2;
	};
});
</script>
</head>
<body>
<div class="container" ng-controller="formCtrl">
	<h1>회원가입 폼</h1>
	<form action="signup.do" method="post" name="myForm" novalidate>
		<div class="form-group">
			<label for="id">아이디</label>
			<input class="form-control" type="text" name="id" id="id"
				ng-model="id"
				ng-required="true"
				ng-pattern="/^[a-z].{4,9}$/"
				ng-class="{'is-invalid': myForm.id.$invalid && myForm.id.$dirty,'is-valid': myForm.id.$valid}"
				ng-change="idChanged()"/>
			<small class="form-text text-muted">영문자 소문자로 시작하고 최소 5글자~10글자 이내로 입력 해야합니다.</small>
			<div class="invalid-feedback">사용할수 없는 아이디 에요.</div>
		</div>
		<div class="form-group">
			<label for="pwd">비밀번호</label>
			<input class="form-control" type="password" name="pwd" id="pwd"
				ng-model="pwd"
				ng-required="true"
				ng-minlength="5"
				ng-maxlength="10"
				ng-class="{'is-invalid': myForm.pwd.$invalid && myForm.pwd.$dirty , 'is-valid': myForm.pwd.$valid}"
				ng-change="pwdChanged()"/>
			<small class="form-text text-muted">최소 5글자~10글자 이내로 입력 해야 합니다.</small>
			<div class="invalid-feedback">비밀번호 확인 하세요.</div>
		</div>
		<div class="form-group">
			<label for="pwd2">비밀번호 확인</label>
			<input class="form-control" type="password" name="pwd2" id="pwd2"
				ng-model="pwd2"
				ng-change="pwdChanged()"/>
		</div>
		<div class="form-group">
			<label for="email">이메일</label>
			<input class="form-control" type="text" name="email" id="email"
				ng-model="email"
				ng-required="true"
				ng-pattern="/@/"
				ng-class="{'is-invalid':myForm.email.$invalid && myForm.email.$dirty, 'is-valid':myForm.email.$valid}"/>
			<div class="invalid-feedback">이메일 형식에 맞게 입력해야 합니다.</div>
		</div>
		<button ng-disabled="myForm.$invalid" class="btn btn-primary" type="submit">가입</button>
		<button class="btn btn-danger" type="reset">Reset</button>
		</div>
	</form>
</div>
</body>
</html>