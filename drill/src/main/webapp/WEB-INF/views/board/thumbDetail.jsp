<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
body{box-sizing:border-box;}

            .replyTop {
                margin: 30px 0;
            }
            .reply_head {
                display: flex;
                flex-direction: column;
                margin-bottom: 10px;
            }
            .reply_content {
                margin-bottom: 15px;
            }

			.reply_contain{
				padding-left: 30px;
			}
            .reply_contain {
                display: none;
                margin: 15px 0;
            }
            .rereply{
            	border: 1px solid black;
            	margin-bottom: 20px !important;
            	padding-left: 20px;
            	background-color:rgba(0, 0, 0, 0.016);
            }
            .show {
                display: block !important;
            }
            #rereBlank {
                width: 100%;
            }
            .btn_box {
                float: right;
            }
            .reply_count {
                margin-bottom: 15px;
            }
            
		textarea{
			border: 1px solid rgb(233, 236, 239);
	        color: rgb(33, 37, 41);
	        width:100%;
		 }
		.btn{
			color: white !important;
		}
	.list-area{
		border: 1px solid black;
		margin:auto;
	}
	.rereArea{
		display:none;
		width:80%;
		margin:auto;
	}
	.hide{
		display:none;
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
		
		<!-- 댓글작성  -->
            <div class="replyArea">
            	<!-- 댓글 작성란  -->
            	<div class="replyTop">
                <div class="reply_count"><b><span id="count"></span> 개의 댓글</b></div>
                <c:choose>
                <c:when test="${ empty loginUser }">
	                <div class="reply_blank">
	                    <textarea id="rereBlank" rows="3" style="resize: none" readonly>로그인이 필요합니다</textarea>
	                </div>
	                <div class="btn_box">
	                    <a class="btn btn-success btn-sm disabled">댓글작성</a>
	                </div>
                </c:when>
                <c:otherwise>
	                <div class="reply_blank">
	                    <textarea id="reBlank" rows="3" style="resize: none">댓글을 작성하세요</textarea>
	                </div>
	                <div class="btn_box">
	                    <a class="btn btn-success btn-sm" onclick="insertReply()">댓글작성</a>
	                </div>
                </c:otherwise>
                </c:choose>
            	</div>
            
            <!-- 댓글들  -->
            <div class="reply">
                <div class="reply_head">
                    <b>user06</b>
                    <small>2021-10-03</small>
                </div>
                <div class="reply_content">내용내용</div>
                <a class="btn btn-success btn-sm rereplyBtn">답글달기</a>
                <hr />
                <!--대댓글 작성란  -->
	         	<div class="rereply">
	                <div class="reply_blank">
	                    <textarea id="rereBlank" rows="3" style="resize: none">댓글을 작성하세요</textarea>
	                </div>
	                <div class="btn_box">
	                    <a class="btn btn-secondary btn-sm cancleReply">취소</a>
	                    <a class="btn btn-success btn-sm">댓글작성</a>
	                </div>
	            </div>
            </div>

			<!-- 대댓글이 존재할 경우  -->
			<div class="reply_contain">
                <div class="reply_head">
                    <b>user03</b>
                    <small>2021-10-03</small>
                </div>
                <div class="reply_content">대댓글내용내용</div>
                <a class="btn btn-success btn-sm rereplyBtn">답글달기</a>
                <hr />
			</div>

            </div>
	</div>
	
	<script>
		$(function(){
			selectReplyList();
			
			//아코디언테스트
			$(".rereply").hide();
			$(document).on("click",".rereplyBtn", function(){
				console.log("!")
				if($(this).next().next().css("display") == "none"){
					$(this).next().next().slideDown("fast");
				} else {
					$(this).next().next().slideUp("fast");
				}
				
				if($(this).text() == '답글달기'){
					$(this).text('숨기기');
				} else {
					$(this).text('답글달기')
				}
			})
			/*
    		$(document).on("click", ".rereplyBtn", function(){
    			console.log("!!")
    			 $(".rereply").toggleClass("show");
    	    	if($(".rereplyBtn").text()=="답글달기"){
    	    			$(".rereplyBtn").text("숨기기")
    	    		} else {
    	    			$(".rereplyBtn").text("답글달기")
    	    		}
    		})*/

    		$(document).on("click", ".cancelReply", function(){
    			$(".rereply").slideUp("fast");
    		})

			
			/* depth */
			var replyDepth = $("input[name=replyDepth]").val();
			/* order */
			var replyOrder = $("input[name=replyOrder]").val();
			

		})
    		var replyNo = "";
			/* 번호가져오기  */
			$(document).on("click", ".rereplyBtn", function(){
				console.log($(this).prev().prev().children().eq(0).val());
				replyNo = $(this).prev().prev().children().eq(0).val();
			})
			/* 대댓글구현  */
			function insertRereply(){
				
				$.ajax({
					url:"insertReRply.bo",
					data:{replyContent:$("#rereBlank").val()
						, detailCd: ${b.detailCd}
						, refBno: ${b.boardNo}
						, replyNo: replyNo
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
			/* 댓글등록 */
			function insertReply(){
				if($("#reBlank").val().trim().length != 0){
					$.ajax({
						url:"insertRply.bo",
						data:{replyContent:$("#reBlank").val()
							, detailCd: ${b.detailCd}
							, refBno : ${ b.boardNo }
							, replyWriter: '${ loginUser.memId}'},
						type:"post",
						success:function(status){
							if(status == "success"){
								selectReplyList();
								$("#reBlank").val("");
							}
						}, error:function(){
							console.log("ajax실패")
						}
					})
				} else {
					window.alert("유효한 댓글 작성이 필요합니다");
				}
			}
		/* 댓글조회  */
		function selectReplyList(){
        
			$.ajax({
				url:"selectRply.bo",
				data:{bno: ${b.boardNo}},
				async:false,
				success:function(list){
					console.log(list);
					var result ="";
    				for(var i=0; i<list.length; i++){
    					result += "<div class='reply_head'>"
    									+"<input type='hidden' class='rno' value="+ list[i].replyNo +">"
    									+"<input type='hidden' name='replyDepth' value="+ list[i].replyDepth +">"
    									+"<input type='hidden' name='replyOrder' value="+ list[i].replyOrder +">"
    									+"<b>"+ list[i].replyWriter + "</b><br>"
    									+ "<small>" + list[i].createDate + "</small>"
    							+"</div>"
    							+ "<div class='reply_content'>"
    								+ list[i].replyContent
    							+"</div>";
    							
    					if(list[i].depth > 0){//여기서 if문으로?depth>0이면 버튼이 답글달기가 아니라 n개의 답글보기로 바꾸기
    						result += "<a class='btn btn-success btn-sm rereplyBtn'>" + list.length + "개의 답글</a><hr>"
    						//해당 댓글을 넣어줘야하는데 반복문으로 어떻게 돌리냥
    						
    					} else {
    						result += "<a class='btn btn-success btn-sm rereplyBtn'>답글달기</a><hr>"
    								+"<div class='rereply'>"
											+"<div class='reply_blank'>"
											+"<textarea id='rereBlank' rows='3' style='resize:none'>대댓작성</textarea>"
										+"</div>"
										+"<div class='btn_box'>"
											+"<a class='btn btn-secondary btn-sm cancelReply'>취소</a>"
											+"<a class='btn btn-success btn-sm' onclick='insertRereply()'>댓글작성</a>"
										+"</div>"
									+"</div>"
    					}
    							
    				};
    				$(".reply").html(result);
    				$("#count").html(list.length);
    				
				}, error:function(){
					console.log("ajax실패");
				}
			})
		}
	</script>
</body>
</html>