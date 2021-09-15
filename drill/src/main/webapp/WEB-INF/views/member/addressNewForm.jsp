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
<!-- daum address -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
</head>
<body>
	<div class="pop_header"><h1>주소 정보 등록/수정</h1></div>
 	<div class="pop_body">
 	<h2>주소 정보 상세</h2>
 	<form action="newAddress.me" method="post">
 	 	<table class="table">
		 	<tr>
		 		<th>주소명</th>
		 		<td><input type=text name="aka"></td>
		 	</tr>
		 	
		 	<tr>
		 		<th>주소</th>
		 		<td>
		 		<input type="text" id="sample6_postcode" placeholder="우편번호" name="zipCode" value="${adr.zipCode}">
	        	<input type="button" class="addF" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
	        	<input type="text" id="sample6_address" placeholder="주소" name="address">
	        	<input type="text" id="sample6_detailAddress" name="address">
		 		</td>
		 	</tr>
		 	<tr>
		 		<th>기본배송지</th>
		 		<td><input type="checkbox" name="status" value="Y">기본배송지로 설정</td>
		 	</tr>
 		</table>
 		<div class="btnBox"><button type="submit">저장하기</button></div>
 	</form>
 	</div>
 	
 	<script>
 	function sample6_execDaumPostcode() {
 	    new daum.Postcode({
 	        oncomplete: function(data) {

 	            var addr = ''; // 주소 변수
 	            var extraAddr = ''; // 참고항목 변수

 	            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
 	            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
 	                addr = data.roadAddress;
 	            } else { // 사용자가 지번 주소를 선택했을 경우(J)
 	                addr = data.roadAddress;//그래도 도로명으로 넣자
 	            }

 	            // 우편번호와 주소 정보를 해당 필드에 넣는다.
 	            document.getElementById('sample6_postcode').value = data.zonecode;
 	            document.getElementById("sample6_address").value = addr;
 	            // 커서를 상세주소 필드로 이동한다.
 	            document.getElementById("sample6_detailAddress").focus();
 	        }
 	    }).open();
 	}
 	</script>
</body>
</html>