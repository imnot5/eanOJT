<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	.hide{
		color:red;
	}
</style>
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<div class="container">
	<h2>공지사항상세조회</h2>
		<form id="noticeForm" method="POST" enctype="multipart/form-data">
		<input type="hidden" name="noticeNo" value="${n.noticeNo }">
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
			<input type="text" name="noticeTitle" placeholder="제목입력" value="${n.noticeTitle }">
		</div>
		
		<div class="insert_row">
			<h3 class="insert_title"><label for="writer">작성자</label></h3>
			<input type="text" name="noticeWriter" value="${loginUser.memId}" readonly>
		</div>
		
		<div class="insert_row">
			<h3 class="insert_title"><label for="content">내용</label></h3>
			<textarea name="noticeContent" rows="10" style="resize:none;">${n.noticeContent }</textarea>
		</div>
		
		<div class="insert_row">
			<h3 class="insert_title"><label for="attchment">첨부파일</label></h3>
			<div class="atts">
			<c:forEach var="i" items="${ list }" varStatus="status">
				<c:choose>
					<c:when test="${ !empty i.originName }">
					<ul>
						<li id="attList">
						<a href="${ i.changeName }" download="${i.changeName }">${ i.originName }</a>
						<a type="button" name="attUpdate" class="btn btn-primary btn-sm">수정하기</a>
						<input type="file" class="refile" name="reUpfile">
						<input type="hidden" name="atList[${status.index }].attachmentNo" value="${ i.attachmentNo }">
						<input type="hidden" name="atList[${status.index }].changeName" value="${i.changeName }">
						<a type="button" name="attDelete" class="btn btn-danger btn-sm">삭제하기</a>
						</li>
					</ul>
					</c:when>
					<c:otherwise>
						첨부파일이 없습니다
					</c:otherwise>
				</c:choose>
			</c:forEach>
			</div>
		</div>
		<div class="btnBox">
			<a type="button" class="btn btn-success" onclick="postFormNotice(1);">수정하기</a>
			<a type="button" class="btn btn-warning" onclick="postFormNotice(2);">삭제하기</a>
		</div>
		</form>
</div>

<script>
$(function(){
	$(".refile").hide();
	
	$("select[name=detailCd] option").each(function(){
		if($(this).val() == '${n.detailCd}'){
			$(this).prop("selected", true);
		}
	})
	$("a[name=attUpdate]").on("click", function(){
		$(this).next().click();
	})
	
	$("a[name=attDelete]").on("click", function(){
			$(this).parent().remove();
	})


})

function postFormNotice(num){
	if(num == 1){
		$("#noticeForm").attr("action", "update.no").submit();
	} else {
		$("#noticeForm").attr("action", "delete.no").submit();
	}
	
}
</script>
</body>
</html>