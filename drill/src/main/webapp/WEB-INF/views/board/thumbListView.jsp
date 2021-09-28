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

	}
	.thumbnail:hover{
		cursor:pointer;
	}
	.imgs{
		border: 1px solid black;
		display: flex;
		margin: 20px;
	}
	
	
	#pagingArea{
		border: 1px solid black;
		display: flex;
  		align-items: center;
  		justify-content: center;
	}
</style>
</head>
<body>
<jsp:include page="../common/header.jsp"/>

<div class="container">
	<h2>thumbNail BoardList</h2>
	<div class="btnBox">
		<a  class="btn btn-secondary btn-sm" href="enrollForm.bo">작성하기</a>
	</div>
	<div class="list-area">
		<div class="thumbnail">
		<c:choose>
		<c:when test="${!empty glist }">
			<c:forEach var="g" items="${ glist }">
			<div class="content">
				<input type="hidden" value="${ g.board.boardNo } ">
				<div class="imgs">
					<img src="${g.changeName }" width="200" height="200">
					<div class="desc">
						<p> No.${g.board.boardNo } <br> ${g.board.boardWriter } <br>  ${g.board.boardTitle } <br> 조회수: ${ g.board.boardCount }</p>
					</div>
				</div>
			</div>
			</c:forEach>
		</c:when>
		<c:otherwise>
			<p>게시물이 없습니다</p>
		</c:otherwise>
		</c:choose>
		</div>
	</div>
	
	<div id="pagingArea">
		<ul class="pagination">
		
		<c:choose>
			<c:when test="${pi.currentPage eq 1 }">
			<li class="page-item disabled"><a class="page-link">Previous</a></li>
			</c:when>
			<c:otherwise>
			<li class="page-item"><a class="page-link" href="list.bo?currentPage=${ pi.currentPage-1 }">Previous</a></li>
			</c:otherwise>
		</c:choose>
		
		<c:forEach var="p" begin="${pi.startPage }" end="${pi.endPage }">
			<li class="page-item"><a class="page-link" href="list.bo?currentPage=${ p }">${ p }</a></li>
		</c:forEach>

		<c:choose>
			<c:when test="${pi.currentPage eq pi.maxPage }">
			<li class="page-item disabled"><a class="page-link">Next</a></li>
			</c:when>
			<c:otherwise>
			<li class="page-item"><a class="page-link" href="list.bo?currentPage=${ pi.currentPage+1 }">Next</a></li>
			</c:otherwise>
		</c:choose>
		</ul>
	</div>
	
</div>

<script>
//아니면 ajax로 불러오기?
$(function(){
	$(".content").click(function(){
		location.href="detail.bo?bno=" +$(this).children().eq(0).val();
	})
})
</script>
</body>
</html>