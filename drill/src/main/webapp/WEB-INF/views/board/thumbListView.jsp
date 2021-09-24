<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.list-area{
		width: 80%;
		margin:auto;
	}
	.thumbnail{
		border: 1px solid black;
		width: 220px;
		display: flex;
		flex-direction: column;
		margin: 20px;
	}
	.thumbnail:hover{
		cursor:pointer;
	}
</style>
</head>
<body>
<jsp:include page="../common/header.jsp"/>

<div class="container">
	<h2>사진게시판</h2>
	<div class="list-area">
	<!-- c:forEach 넣기 -->
		<div class="thumbnail">
			<input type="hidden" value="${ t.boardNo } ">
			<div class="imgs">
			<img src="#" width="200" height="150">
			</div>
			<div class="desc">
			<p>No.11 제목 조회수</p>
			</div>
		</div>
	</div>
</div>

<script>
//아니면 ajax로 불러오기?
$(function(){
	$(".thumnail").click(function(){
		location.href="detail.th?tno=" +$(this).children().eq(0).val();
	})
})
</script>
</body>
</html>