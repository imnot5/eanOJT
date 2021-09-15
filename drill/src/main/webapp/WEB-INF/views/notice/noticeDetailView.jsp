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
	<h2>공지사항상세조회</h2>
		<form id="noticeForm">
	
		<div class="insert_row">
			<h3 class="insert_title"><label for="category">카테고리</label></h3>
			<select name="detailCd">
				<option value="1">필독</option>
				<option value="2">일반</option>
				<option value="3">긴급</option>
			</select>
		</div>
		
		<div class="insert_row">
			<h3 class="insert_title"><label for="title">제목</label></h3>
			<input type="text" name="noticeTitle" placeholder="제목입력">
		</div>
		
		<div class="insert_row">
			<h3 class="insert_title"><label for="writer">작성자</label></h3>
			<input type="text" name="noticeWriter" value="${loginUser.memId}" readonly>
		</div>
		
		<div class="insert_row">
			<h3 class="insert_title"><label for="content">내용</label></h3>
			<textarea name="noticeContent" rows="10" style="resize:none;"></textarea>
		</div>
		
		<div class="btnBox">
			<a type="button" class="btn btn-primary" onclick="updateNotice();">수정하기</a>
		</div>
		</form>
</div>

<script>
$(function(){
	noticeDetail();
})

function noticeDetail(){
	$.ajax({
		url:"detailView.no",
		data:{},
		success:function(n){
			
		}, error:function(){
			
		}
	})
}
</script>
</body>
</html>