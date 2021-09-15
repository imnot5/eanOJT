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
	<h2>공지사항</h2>
	<a class="btn btn-primary" href="enrollForm.no" style="float:right;">작성하기</a>
	<table id="nTable" class="table">
		<thead>
			<tr>
				<th>공지사항번호</th>
				<th>카테고리</th>
				<th>제목</th>
				<th>작성자</th>
				<td>작성일</td>
				<td>조회</td>
			</tr>
		</thead>
		
		<tbody>

		</tbody>
	</table>
	
	<div id="pagingArea">
    <ul class="pagination">
    </ul>
    </div>
</div>

<script>
$(function(){
	selectList();
	$(".table tbody tr").on("click", function(){
		console.log("!!")
		var nno = $(this).children().eq(0).text()
		//location.href="detail.no?nno=" + nno;
		$.ajax({
			url:"detailView.no",
			data:{nno: nno},
			success:function(result){
				if(result.code === '0000'){
					location.href="detail.no?nno="+nno;
				} else {
					console.log(result)
				}
			}, error:function(){
				console.log("ajax실패");
			}
		}) 
	})
})

function selectList(){
	$.ajax({
		url:"list.no",
		type:"post",
		async:false,
		success:function(result){
			console.log(result);
			var detail ="";
			var list = "";
			var pagination = "";
			var nList = result.list;
			for(var i=0; i<nList.length; i++){
				switch(nList[i].detailCd){
				case '1' : detail = '필독'; break;
				case '2' : detail = '일반'; break;
				case '3' : detail = '긴급'; break;
				}
				list += "<tr>" + "<td>" + nList[i].noticeNo + "</td>"
							   + "<td>" + nList[i].detailNm + "</td>"
							   + "<td>" + nList[i].noticeTitle + "</td>"
							   + "<td>" + nList[i].noticeWriter + "</td>"
							   + "<td>" + nList[i].createDate + "</td>"
							   + "<td>" + nList[i].noticeCount + "</td>"
						+"</tr>";
			};
			
			//이전버튼활성화여부
			var currentPage = result.pi.currentPage;
			if(currentPage == 1){
				pagination += '<li class="page-item disabled">'+'<a class="page-link">Previous</a></li>';
			} else {
				pagination += '<li class="page-item"><a class="page-link" href="list.no?currentPage='+ currentPage-1 +'">Previous</a></li>';
			}
			
			//페이징넘버
			var startPage = result.pi.startPage;
			var endPage = result.pi.endPage;
			for(var j=startPage; j<=endPage; j++){
				pagination += '<li class="page-item"><a class="page-link" href="list.no?currentPage='+j+'">'+j+'</a></li>'
			}
			
			//다음버튼
			var maxPage = result.pi.maxPage;
			if(currentPage == maxPage){
				pagination += '<li class="page-item disabled"><a class="page-link">Next</a></li>';
			} else {
				patination += '<li class="page-item"><a class="page-link" href="list.no?currentPage='+ currentPage+1 +'"> Next </a></li>';
			}
			$("table tbody").html(list);
			$("#pagination ul").html(pagination);
		}, error:function(){
			console.log("ajax실패");
		}
	})
}


</script>
</body>
</html>