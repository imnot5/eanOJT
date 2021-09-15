<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .pwdModalBody{
        position:absolute;
        display: flex;
        flex-direction: column;
        align-items: center;
        top:50%;
        left:50%;
        transform: translate(-50%, -50%);
        background-color: rgb(255, 255, 255);
        width: 400px;
        height: 200px;
        font-size: 16px;
    }

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
    .pwdModal, .addressModal{
        display: none;
        background-color: rgba(0, 0, 0, 0.5);
        position: fixed;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
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
    button{
    	width:100px !important;
    	height:40px !important;
    }
</style>
</head>
<body>
	<jsp:include page="../common/header.jsp"/>

<div class="container">
<h2>${ loginUser.memId }님의 마이페이지</h2>
<div class="wrapper">
    <div class="nameSection">
        <label for="memName">이름:</label>
        <input type="text" name="memName" value="${ loginUser.memName }" readonly>
    </div>
    <div class="idSection">
        <label for="memId">아이디:</label>
        <input type="text" value="${ loginUser.memId }" readonly>
    </div>
    <div class="password">
        <label for="">비밀번호수정하기</label>
        <button type="button" class="modifyPwd">수정하기</button>
        <!-- 모달만들어서 수정 폼 뜨게 하기 -->
    </div>
    <div class="memGender">
        <label for="male">남자</label>
        <input type="radio" id="male "name="memGender" value="M">
        <label for="female">여자</label>
        <input type="radio" id="female" name="memGender" value="F">
    </div>
    <div class="memPhone">
        <label for="phone">휴대폰번호: </label>
        <input type="text" id="phone" name="memPhone" value="${loginUser.memPhone}">
    </div>
    <div class="memEmail">
        <label for="email">이메일: </label>
        <input type="email" name="memEmail" id="email" value="${loginUser.memEmail}">
    </div>
    <div class="address">
    	<label for="address">주소: </label>
    	<div class="btnBox" style="float:right; padding:10px 0"><button type="button" onclick="adrNewForm();">추가하기</button></div>
        <table class="table">
            <thead>
                <tr>
                	<th>번호</th>
                    <th>배송지</th>
                    <th>주소</th>
                    <th>수정/삭제</th>
                </tr>
            </thead>
            <tbody>

            </tbody>
        </table>
    </div>
</div>
</div>

<!-- 비밀번호 모달 -->
<div class="pwdModal">
    <div class="pwdModalBody">
    <div class="close">&times;</div>
        <form action="updatePwd.me" method="POST">
            <div class="currPwd">
                <label for="currPwd">현재비밀번호</label>
                <input type="password" name="memPwd" id="currPwd">
            </div>
            <div class="newPwd">
                <label for="newPwd">새 비밀번호</label>
                <input type="password" name="newPwd" id="newPwd">
                <div id="checkResult"></div>
            </div>
            <button type="submit">수정하기</button>
        </form>
    </div>
</div>

<script>
	selectAdrList();
	
	var pwdReg =/^(?=.*[0-9])(?=.*[!@#\$%\^&\*]).{8,16}$/;
    var pwCheck = false;
    var oldPw = "";
    $(document).on("propertychange change keyup paste input","#newPwd", function(){
        var pw = $(this).val();
        if(pw == oldPw){
            return;
        }
        oldPw = pw;
        var num = pw.search(/[0-9]/g);
        var eng = pw.search(/[a-z]/ig);
        var spe = pw.search(/[`~!@#$%^&*?]/gi);

        	if(pw.length < 8 || pw.length > 16){
        		$("#checkResult").text("8~16자리로 입력해주세요").css("color","red");
                pwCheck = false;
                return false;
        	}
            if(num< 0 || eng < 0 || spe < 0){
                $("#checkResult").text("영문, 숫자, 특수문자를 혼합해 입력하세요").css("color", "red");
                pwCheck = false;
                return false;
            }
            $("#checkResult").text("올바른 형식입니다.").css("color", "green");
                pwCheck = true;
    })

    $(function(){
    	
        $(".modifyPwd").on("click", function(){
            $(".pwdModal").addClass("show");
        })
        $(".close").on("click", function(){
            $(".pwdModal").removeClass("show");
        })
        $(".modifyAdd button").on("click", function(){
        	$(".addressModal").addClass("show");
        })

        var address = "${adrList[0].address}";
        console.log(address);
        var addressArr = address.split(",");
        $("input:text[name=memAddress]").each(function(idx){
        	$(this).val(addressArr[idx]);
        })
        
        $("input:radio[value=${loginUser.memGender}]").prop("checked", true);
    })
    
    function modifyPop(adrNo){
    	 var href = "addressPop.me?adrNo=" + adrNo
    	 window.open(href,"_blank","width=600px, height=400px, scrollbars=yes");
    }
    /* 주소 추가  */
    function adrNewForm(){
    	window.open("adrNewForm.me","_blank","width=600px, height=400px, scrollbars=yes");
    }
	function selectAdrList(){
		var memId = '${loginUser.memId}'
    	console.log("memId"+memId)
    	$.ajax({
    		url:"xxx.me",
    		dataType:"json",
    		contentType:"application/json; charset=UTF-8;",
    		type:"post",
    		data: memId,
    		success:function(json){
    			console.log(json);
    			var result ="";
    			for(var i=0; i<json.length; i++){
    				result += "<tr>" + "<td>" + json[i].addresesNo + "</td>"
    								 + "<td>" + json[i].aka + "</td>"
    								 + "<td>" + json[i].zipCode + json[i].Address +"</td>"
    								 + "<td>" + "버튼자리" +"</td>"
    						+ "</tr>";
    			};
    			$("table tbody").html(result);
    		}, error:function(){
    			console.log("ajax실패");
    		}
    	})
    }

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