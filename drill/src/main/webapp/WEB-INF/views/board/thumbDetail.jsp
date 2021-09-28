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
		border: 1px solid black;
		margin:auto;
	}
	.rereArea{
		display:none;
		width:80%;
		margin:auto;
	}
	.show{
	 	display:block !important;
	}
	.hide{
		display:none;
	}
	#rrBtn{
		float:right;
	}
</style>
</head>
<body>

	<jsp:include page="../common/header.jsp"/>
	<!-- 댓글기능 이후에  enctype multipart form으로 crud 추가할것 -->
	<div class="container">
		<h2>thumbNail Details</h2>
		<div class="list-area">
			<div class="title">
			<p>No.${b.boardNo}<br>${ b.boardTitle }<br>${b.boardWriter }</p>
			</div>
			<div class="content">
				<div class="thumbnail">
				<c:forEach var="t" items="${ list }">
				<img src="${t.changeName}" width="200" height="200">			
				</c:forEach>
				</div>
			</div>
			<div class="desc">
				<p>${ b.boardContent }</p>
			</div>
		</div>
		
		<div class="reply-area">
			<table class="table">
				<thead>
				<tr>
				<c:choose>
					<c:when test="${ empty loginUser }">
					<th colspan="2">
						<textarea id="content" cols="55" rows="2" style="resize:none; width:100%" readonly>로그인!</textarea>
					</th>
					<th style="vertical-align: middle"><button class="btn btn-secondary" disabled>등록하기</button></th>
					</c:when>
					<c:otherwise>
					<th colspan="2">
						<textarea id="content" cols="55" rows="2" style="resize:none; width:100%"></textarea>
					</th>
					<th style="vertical-align: middle"><button class="btn btn-primary" onclick="insertReply();">등록하기</button></th>
					</c:otherwise>
				</c:choose>
				</tr>
				
				
				<tr>
					<td colspan="3">댓글(<span id="rpCount"></span>)</td>
				</tr>
				</thead>
				
				<tbody>
					<!--  ajax  -->
				</tbody>
			</table>
		</div>
	</div>
	
	<!-- 대댓글창  -->
	<div class="rereArea">
	<textarea id="recontent" cols="55" rows="2" style="resize:none; width:100%"></textarea>
	<button class="btn btn-primary btn-sm" id="rrBtn" onclick="insertReReply();">등록하기</button>
	</div>
	
	<script>
		$(function(){
			selectReplyList();
		})
		
		/* 댓글등록 */
		function insertReply(){
			if($("#content").val().trim().length != 0){
				$.ajax({
					url:"insertRply.bo",
					data:{replyContent:$(".reply-area textarea").val()
						, detailCd: ${b.detailCd}
						, refBno : ${ b.boardNo }
						, replyWriter: '${ loginUser.memId}'},
					type:"post",
					success:function(status){
						if(status == "success"){
							selectReplyList();
							$("#content").val("");
						}
					}, error:function(){
						console.log("ajax실패")
					}
				})
			} else {
				window.alert("유효한 댓글 작성이 필요합니다");
			}
		}
		/* 대댓글구현  */
		function insertReReply(){
			$.ajax({
				url:"insertReRply.bo",
				data:{replyContent:$(".rereArea textarea").val()
					, detailCd: {b.detailCd}
					, refBno: ${b.boardNo}
					, replyParent: ${r.replyNo}
					, replyWriter: '${loginUser.memId}'},
				type:"post",
				success:function(status){
					if(status=="success"){
						selectReplyList();
						$("#recontent").val("");
					}
				}, error:function(){
					console.log("ajax실패");
				}
			})
		}
		/* 댓글조회  */
		function selectReplyList(){
			$.ajax({
				url:"selectRply.bo",
				data:{bno: ${b.boardNo}},
				success:function(list){
					console.log(list);
					var result ="";
    				for(var i=0; i<list.length; i++){
    					result += "<tr>"
    								+ "<td>" + list[i].replyWriter + "</td>"
    								+ "<td>" + list[i].replyContent + "</td>"
    								+ "<td>" + list[i].createDate + "</td>"
    							+ "</tr>"
    							+ "<tr>"
    								+"<td colspan='3'><a class='btn btn-success btn-sm' id='rere'>답글달기</a></td>"
    							+ "</tr>";		
    				};
    				$(".reply-area tbody").html(result);
    				$("#rpCount").html(list.length);
				}, error:function(){
					console.log("ajax실패");
				}
			})
		}
		
		$(document).on("click", "#rere", function(){
			console.log("!!");
			$(".rereArea").toggle(
					function(){$(".rereArea").addClass("show")},
					function(){$(".rereArea").removeClass("show")}
					)
			if($(this).text() == '답글달기'){
				$(this).text("숨기기");
			} else {
				 $(this).text("답글달기");
			}
			
					//function(){$("#rere").text("답글달기")}
			//$(this).text("숨기기");
			//$(".rereArea").addClass("show");
		})
	</script>
</body>
</html>