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
		<h2>Writing thumbNail Board</h2>
		<form action="insert.bo" method="POST" enctype="multipart/form-data">
		
		<table>
			<tr>
				<th>제목</th>
				<td><input type="text" name="boardTitle" placeholder="제목을입력하세요"></td>
			</tr>
			<tr>
				<th>작성자</th>
				<td><input type="text" name="boardWriter" value="${ loginUser.memId }" readonly></td>
			</tr>
			<tr>
				<th>카테고리</th>
				<td>
				<select name="detailCd">
					<option value="4">공지사항</option>
					<option value="5">일반</option>
				</select>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<textarea name="boardContent" rows="5" style="resize:none;" required></textarea>
				</td>
			</tr>
			<tr>
				<th>이미지 첨부하기</th>
				<td><input type="file" name="upfile" multiple></td>
			</tr>
			<tr>
				<td colspan="2" class="preview"></td>
			</tr>
		</table>
		<div class="btnBox">
			<button class="btn btn-primary btn-sm" type="submit">작성하기</button>
		</div>
		</form>
	</div>
	
	<script>
	//다시 올릴때 미리보기 바뀌는걸 하고 싶음어떻게 없애지???
	$("input[type=file]").on("change", function(){
		var countFiles = $(this)[0].files.length;
		
		for(var i=0; i<countFiles; i++){
			var reader = new FileReader();
			reader.onload = function(e){
				$("<img/>",{
					"src": e.target.result,
					"width": "150",
					"height": "150",
					"class":"thumImg"
				}).appendTo($(".preview"));
			}
			reader.readAsDataURL($(this)[0].files[i]);
		}
	})
	
	</script>
</body>
</html>