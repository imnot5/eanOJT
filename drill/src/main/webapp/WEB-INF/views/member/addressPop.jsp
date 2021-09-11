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
<style>
    .addModalBoby{
        position:absolute;
        align-items: center;
        align-content: center;
        top:50%;
        left:50%;
        transform: translate(-50%, -50%);
        background-color: rgb(255, 255, 255);
        width: 600px;
        height: 400px;
    }
    .show{
        display: block;
    }
    .close{
        width: 15px;
        height: 15px;
        border: 1px solid black;
        font-size: 20px;
        margin: 5px;
    }
</style>
</head>
<body>
<div class="addressModal">
    <form action="adrUpdate.me" method="POST">
    <div class="addModalBoby">
        <div class="close">&times;</div>
        <h3>배송지정보상세</h3>
        <table border="1">
        <tr>
        <th>배송지명</th>
        <td><input type="text" name="">집</td>
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
        <td><input type="checkbox">기본배송지로설정</td>
        </tr>
        </table>
        <input type="hidden" name="addressNo" value="${ adr.addressNo }">
        <div class="btnBox"><button type="submit">저장하기</button></div>
    </div>
    </form>
</div>
<script>

$(function(){
    var address = "${adr.address}";
    var addressArr = address.split(",");
    $("#sample6_address").val(addressArr[0]);
    $("#sample6_detailAddress").val(addressArr[1]);
    
})

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