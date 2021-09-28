<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- jQuery 라이브러리 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<!-- BootStrap css  -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" integrity="sha384-B0vP5xmATw1+K9KRQjQERJvTumQW0nPEzvF6L/Z6nronJ3oUOFUFpCjEUQouq2+l" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>

<!-- kakao sdk  -->
<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
<!-- kakao sdk 초기화 -->
<script>
	Kakao.init('f2e27dda5cfdc6659ff812c6b423f97a');
	Kakao.isInitialized();
	console.log(Kakao.isInitialized());
</script>
<style>
#navbarNavDropdown{
	display:flex;
	justify-content:space-between;
}
</style>
</head>
<body>
	<c:if test="${ !empty alertMsg }">
		<script>
			window.alert("${alertMsg}");
		</script>
		<c:remove var="alertMsg" scope="session"/>
	</c:if>
<div class="container">
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <a class="navbar-brand" href="#">Drills</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNavDropdown">
  	<div class="nav-section1">
  	<ul class="navbar-nav">
      <li class="nav-item active">
        <a class="nav-link" href="${pageContext.request.contextPath }">Home</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="listView.no">Notice</a>
      </li>
      <li class="nav-item">
        <a class="nav-link" href="list.bo">Board</a>
      </li>
      <li class="nav-item dropdown">
        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          Dropdown link
        </a>
        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
          <a class="dropdown-item" href="#">Action</a>
          <a class="dropdown-item" href="#">Another action</a>
          <a class="dropdown-item" href="#">Something else here</a>
        </div>
      </li>
    </ul>
  	</div>
	<div class="nav-section2">
	<ul class="navbar-nav">
		<li class="nav-item active">
			<a class="nav-link" href="enrollForm.me">회원가입</a>
		</li>
		<c:choose>
			<c:when test="${empty loginUser}">
			<li class="nav-item active">
				<a class="nav-link" data-toggle="modal" data-target="#loginModal">로그인</a>
			</li>
			</c:when>
			<c:when test="${ !empty loginUser }">
			<li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          ${loginUser.memName }님 환영합니다.
		        </a>
		        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
		          <a class="dropdown-item" href="myPage.me">마이페이지</a>
		          <a class="dropdown-item" href="logout.me">로그아웃</a>
		        </div>
		      </li>
			</c:when>
			<c:otherwise>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          ${loginUserSns.name }님 환영합니다.
		        </a>
		        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
		          <a class="dropdown-item" href="myPage.me">마이페이지</a>
		          <a class="dropdown-item" href="logout.me">로그아웃</a>
		        </div>
		      </li>
			</c:otherwise>
		</c:choose>
	</ul>
	</div>
  </div>
</nav>
</div>


<!-- login Modal  -->
    <div class="modal fade" id="loginModal">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Login</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button> 
            </div>

            <form action="login.me" method="post">
                <!-- Modal Body -->
                <div class="modal-body">
                	<a href="https://kauth.kakao.com/oauth/authorize?client_id=59ea53f98098c442f2b7872c6b3b016f&redirect_uri=http://localhost:8888/login&response_type=code">
                		  <img src="//k.kakaocdn.net/14/dn/btqCn0WEmI3/nijroPfbpCa4at5EIsjyf0/o.jpg" width="222"/>
                	</a><br><br>
                	<a onclick="loginWithNaver();">
                		<img src="/resources/images/btnG_완성형.png" width="222"/>
                	</a><br>
                    <label for="memId" class="mr-sm-2">ID :</label>
                    <input type="text" class="form-control mb-2 mr-sm-2" placeholder="Enter ID" id="memId" name="memId"> <br>
                    <label for="memPwd" class="mr-sm-2">Password:</label>
                    <input type="password" class="form-control mb-2 mr-sm-2" placeholder="Enter password" id="memPwd" name="memPwd">
                    <label for="remember">아이디 저장</label>
                    <input type="checkbox" name="remember" id="remember" value="1">
                </div>
                
                <!-- Modal footer -->
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary">로그인</button>
                    <button type="button" class="btn btn-danger" data-dismiss="modal">취소</button>
                </div>
            </form>
            </div>
        </div>
    </div>

 
<script>
	$(document).ready(function(){
        var userId = getCookie("cookieUserId"); 
        $("input[name=memId]").val(userId); 
         
        if($("input[name=memId]").val() != ""){ // Cookie에 만료되지 않은 아이디가 있어 입력됬으면 체크박스가 체크되도록 표시
            $("input[name=remember]").prop("checked", true);
        }
         
        $("button[type='submit']").click(function(){ // Login Form을 Submit할 경우,
            if($("input[name=remember]").is(":checked")){ // ID 기억하기 체크시 쿠키에 저장
                var userId = $("input[name=memId]").val();
                setCookie("cookieUserId", userId, 7); // 7일동안 쿠키 보관
            } else {
                deleteCookie("cookieUserId");
            }
        }); 
	})
	
	$("#controlTest").click(function(){
		$("form").attr("action","login").submit();
	})
	
	function setCookie(cookieName, value, exdays){
		var exdate = new Date();
		exdate.setDate(exdate.getDate()+exdays);
		var cookieValue = escape(value) + ((exdays==null) ? "" : "; expires=" + exdate.toGMTString());
		document.cookie = cookieName + "=" + cookieValue;
	}
	
	function deleteCookie(cookieName){
		var expireDate = new Date();
		expireDate.setDate(expireDate.getDate()-1);
		document.cookie = cookieName + "= "+"; expires =" + expireDate.toGMTString();
	}
	function getCookie(cookieName){
        cookieName = cookieName + '=';
        var cookieData = document.cookie;
        var start = cookieData.indexOf(cookieName);
        var cookieValue = '';
        if(start != -1){
            start += cookieName.length;
            var end = cookieData.indexOf(';', start);
            if(end == -1) end = cookieData.length;
            cookieValue = cookieData.substring(start, end);
        }
        console.log(cookieName);
        return unescape(cookieValue);
	}
	
	
    function loginWithKakao() {
    	
    	//location.href="https://kauth.kakao.com/oauth/authorize?client_id=59ea53f98098c442f2b7872c6b3b016f&redirect_uri=http://localhost:8888/auth/kakao/callback&response_type=code";
     	  Kakao.Auth.authorize({
    		redirectUri:'http://localhost:8888/login'
    	}) ;
    	
/*     	$.ajax({
    		url:'/login/getKakaoAuthUrl',
    		type:'get',
    	}).done(function(res){
    		location.href = res;
    	})  */ 
      }
    
    function logoutWithKakao(){
    	$.ajax({
    		url:"klogout",
    		type:'get'
    	}).done(function(result){
    		location.href = result;
    	})
    }
    
    function loginWithNaver(){
    	
    	$.ajax({
    		url:"getNaverAuthUrl",
    		type:"get"
    	}).done(function(result){
    		location.href = result;
    	})
    }
    
    function kakaoUnlink(){
        Kakao.API.request({
        	  url: '/v1/user/unlink',
        	  success: function(response) {
        	    console.log(response);
        	  },
        	  fail: function(error) {
        	    console.log(error);
        	  },
        	});
    }
</script>
</body>
</html>