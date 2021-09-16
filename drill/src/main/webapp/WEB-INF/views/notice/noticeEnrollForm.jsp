<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="../common/header.jsp"/>

<div class="container">
<h2>공지사항 작성하기</h2>
	<form id="noticeForm" method="POST" enctype="multipart/form-data">
		<div class="insert_row">
			<h3 class="insert_title"><label for="category">카테고리</label></h3>
			<select name="detailCd">
				<option value="1">필독</option>
				<option value="2">일반</option>
				<option value="3">긴급</option>
			</select>
		</div>
		
		<div class="insert_row">
			<h3 class="insert_title"><label for="attchment">첨부파일</label></h3>
			<input multiple="multiple" type="file" name="upfile"/>
		</div>
		<div class="insert_row">
			<h3 class="insert_title"><label for="title">제목</label></h3>
			<input type="text" name="noticeTitle" placeholder="제목입력">
		</div>
		
		<div class="insert_row">
			<h3 class="insert_title"><label for="writer">작성자</label></h3>
			<input type="text" name="noticeWriter" value="${loginUser.memId}" >
		</div>
		
		<div class="insert_row">
			<h3 class="insert_title"><label for="content">내용</label></h3>
			<textarea name="noticeContent" rows="10" style="resize:none;"></textarea>
		</div>
		
		<div class="btnBox">
			<a type="button" class="btn btn-primary" onclick="insertNotice();">등록하기</a>
		</div>
	</form>
</div>

<script>

	function insertNotice(){
		$("#noticeForm").attr("action", "insert.no").submit();
	}
	
/* function insertNotice(){
	var form = new FormData(document.getElementById('noticeForm'));
	$.ajax({
		url:"insert.no",
		type:"POST",
		enctype:"multipart/form-data",
		contentType:false,
		processData:false,
		cache:false,
		data: form,
		success:function(result){
			var status = result.code
			if(status="success"){
				console.log("ajax성공")
				window.location.href="listView.no";
			}

		}, error: function(){
			console.log("ajax실패")
			window.location.href="errorPage.jsp";
		}
	})
} */
</script>
</body>
</html>