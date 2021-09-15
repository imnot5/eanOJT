<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!-- flatpickr  -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
<script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>
<!-- daum address -->
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<style>
	.wrapper{
		display: flex;
		flex-direction: column;
		width: 80%;
	}
	.btn_verify, button[name=check], .addF{
		position:absolute;
		width: 115px;
		height: 51px;
		top:0;
		right:0;
		text-align: center;
		box-sizing: border-box;
		cursor: pointer;
		text-decoration: none;
		display: block;
		font-size: 15px;
		padding: 17px 0 17px;
	}
	.btn_primary{
		color:#fff;
		border:solid 1px rgba(0, 0, 0, .08);
		background-color: #03c75a;
	}
	input{
		width: 100%;
		height: 50px;
	}
	.join_title{
		margin: 19px 0 8px;
		font-size: 14px;
		font-weight: 700;
	}
	.mem_area{
		position: relative;
		margin-top:10px;
		padding:0 125px 0 0;
	}
	.id_next{
		position:absolute;
		top: 16px;
		right: 13px;
	}
	.ps_box{
		display: block;
		position:relative;
		width: 100%;
		border: solid 1px #dadada;
		padding: 10px 110px 10px 14px;
		box-sizing: border-box;
	}
	.join_id{
		display: inline-block;
		width: 100%;
		padding:10px 15px 10px 14px;
		vertical-align: top;
	}
</style>
</head>
<body>
<jsp:include page="../common/header.jsp"/>
<div class="container">
<h2>회원가입</h2>
<br>
<form id="enrollForm" action="insert.me" method="POST">
	<div class="wrapper">
		<!-- 아이디 -->
		<div class="join_row">
			<h3 class="join_title"><label for="id">아이디</label></h3>
			<div class="mem_area">
				<span class="ps_box join_id"><input type="text" id="memId" name="memId" required></span>
				<a class="btn_verify btn_primary" role="button" name="check">id중복체크</a>
			</div>
			<span class="error_next_box" id="idMsg"></span>
		</div>
		<!-- 비밀번호 -->
		<div class="join_row">
			<h3 class="join_title"><label for="pwd1">비밀번호</label></h3>
			<div class="ps_box">
				<input type="password" name="memPwd" id="memPwd">
			</div>
		</div>
    	<label for="memId">* ID : 숫자 1개 이상 포함해 총 8~13자 입력</label>
        <input type="text" id="memId" name="memId" required>
        <div id="checkResult" style="font-size:0.8em; display:none;"></div><br>
		<button name="check">id중복체크</button><br>
        
        <label for="userPwd">* Password :영문자, 숫자 , 특수문자 포함하여 총 8~16자로 입력!</label>
        <input type="password" id="memPwd" name="memPwd" placeholder="Please Enter Password" required><br>
        <p class="pwdAlert1"></p>
        
        <label for="checkPwd">* Password Check : 위의 것과 똑같이 입력!</label>
        <input type="password" id="checkPwd" placeholder="Please Enter Password" required><br>
        <p class="pwdAlert2"></p>
        
        <label for="memName">* Name :</label>
        <input type="text" id="memName" name="memName" placeholder="Please Enter Name" required><br>
        <p class="nameAlert"></p>
        
        <label for="email"> &nbsp; Email :</label>
        <input type="email" id="email" name="memEmail" placeholder="Please Enter Email"><br>
        
        <label for="bDay"> &nbsp; BirthDay :</label>
        <input type="text" id="bDay" name="memBday" placeholder="Please pick your dDay"><br>
        
        <label for="phone"> &nbsp; Phone :</label>
        <input type="text" id="memPhone" name="memPhone" placeholder="Please Enter Phone (-없이)"><br>
        
        <label for="address"> &nbsp; Address :</label>
        <div class="address">
	    <input type="text" id="sample6_postcode" placeholder="우편번호" name="zipCode">
		<input type="button" class="addF" onclick="sample6_execDaumPostcode()" value="우편번호 찾기"><br>
		<input type="text" id="sample6_address" placeholder="주소" name="memAddress"><br>
		<input type="text" id="sample6_detailAddress" name="memAddress" placeholder="상세주소">
        </div>
        
        <label for=""> &nbsp; Gender : </label> &nbsp;&nbsp;
        <input type="radio" name="memGender" id="Male" value="M">
        <label for="Male">남자</label> &nbsp;&nbsp;
        <input type="radio" name="memGender" id="Female" value="F">
        <label for="Female">여자</label><br>
        
    </div>
	<br>
    <div class="btns" align="center">
		<button type="submit" class="btn btn-primary" disabled>회원가입</button>
		<button type="reset" class="btn btn-danger"> 초기화</button>
	</div>
</form>
</div>


<script>
var idReg = /^[a-z0-9]{6,13}$/;
var idInput = document.getElementById("memId");
$(document).on("change", "#memId", function(){
	if(!idReg.test(idInput.value)){
		$("#checkResult").show();
		$("#checkResult").text("옳은형식이아닙니다").css("color","red");
        idInput.value="";
        idInput.focus();
        return false;
	} else {
		$("#checkResult").show();
		$("#checkResult").text("사용가능합니다.").css("color","green");
	}
})

$(document).on("click", "a[name=check]", function(){
	$.ajax({
		url:"idCheck.me",
		data:{checkId: $("#memId").val()},
		success:function(result){
			if(result == "NNNNN"){
				$("#checkResult").show();
				$("#checkResult").css("color", "red").text("사용 불가능!");
		        idInput.value="";
		        $("#memId").prop("disabled",false);
		        idInput.focus();
		        return false;
			} else {
				$("#checkResult").show();
				$("#checkResult").css("color", "green").text("사용가능");
			}
		}, error:function(){
			console.log("ajax실패");
		}
	});
})

	var memPwd1 = document.getElementById("memPwd");
	var memPwd2 = document.getElementById("checkPwd");
	var pwdReg = /^(?=.*[0-9])(?=.*[!@#\$%\^&\*]).{8,16}$/;
	
	var pwCheck = false;
	var pwConf_check = false;
	var oldPw = "";
	var confPw = "";

	$("#memPwd").on("propertychange change keyup paste input", function(){
		var pw = $(this).val();
		if(pw == oldPw){
			return;
		}
		oldPw = pw;

		var num = pw.search(/[0-9]/g);
		var eng = pw.search(/[a-z]/ig);
		var spe = pw.search(/[`~!@#$%^&*?]/gi);
		if(pw.length < 8 || pw.length >16){
			$(".pwdAlert1").text("8 ~ 16 자리 이내로 입력해 주세요").css("color", "red");
			pwCheck = false;
			return false;
		}

		if(pw.search(/\s/) != -1){
			$(".pwdAlert1").text("공백없이 입력해주세요").css("color", "red");
			pwCheck = false;
			return false;
		}

		if(num < 0 || eng < 0 || spe <0){
			$(".pwdAlert1").text("영문, 숫자, 특수문자를 혼합하여 입력").css("color", "red");
			pwCheck = false;
			return false;
		}

		$(".pwdAlert1").text("올바른 형식입니다.").css("color", "green");
		pwCheck = true;

	})
	$("#checkPwd").on("propertychange change keyup paste input", function(){
		confPw = $(this).val();
		if(confPw == oldPw){
			$(".pwdAlert2").text("패스워드가 일치합니다");
			pwConf_check = true;
			$("button[type=submit]").prop("disabled",false);
			return;
		} else {
			$(".pwdAlert2").text("패스워드가 일치하지 않습니다");
			pwConf_check = false;
			return;
		}
	})
	/*
	$(document).on("blur", "#checkPwd", function(){
	    if(memPwd1.value != memPwd2.value){
	    	$(".pwdAlert2").text("패스워드가 다릅니다");
	       	memPwd2.value="";
	        memPwd2.focus();
	        return false;
	        } else {
	        	$(".pwdAlert2").text(" ");
	        	$("button[type=submit]").prop("disabled",false);
	        }
	})
*/
	/*
	$(document).on("blur", "#memPwd", function(){
		var pwd = $("#memPwd").val();
		var idid = $("#memId").val();
		
	    if(!pwdReg.test(memPwd1.value)){
	    	$(".pwdAlert1").text("아이디를 포함한 비밀번호 사용 불가").css("color","red");
	        memPwd1.value="";
	        memPwd1.focus();
	        return false;
	        } else if(pwd.search(idid)>-1){
	        	$(".pwdAlert1").text("옳은 형식이 아닙니다").css("color","red");
	        } else {
	        	$(".pwdAlert1").text("유효한 형식입니다").css("color", "green");
	        	$("button[type=submit]").prop("disabled",false);
	        }
	    
	});
	*/

	
	flatpickr("#bDay", {
	    dateFormat: "Y-m-d",
	    minDate: "today"
	});

	var nameCheck = false;
	var oldName = "";
	$("#memName").on("propertychange change keyup paset input", function(){
		console.log("dddd");
		var name = $(this).val();
		if(name == oldName){
			return;
		}
		oldName = name;
		console.log(oldName);

		var nameReg = /^[가-힣]{2,5}$/;
		
		if(name == '' || !nameReg.test(name)){
			$(".nameAlert").text("올바른 형식의 이름이 아닙니다").css("color", "red");
			if(name.lenght>3){
				$("#memName").val("");
			}
			nameCheck = false;
			return;
		} else {
			$(".nameAlert").text("사용가능한 이름입니다").css("color", "green");
			nameCheck = true;
			return;
		}
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
    
    $(document).on("keyup", "#memPhone", function() { $(this).val( $(this).val().replace(/[^0-9]/g, "").replace(/(1[0-9]{2}|^02|^0505|^1[0-9]{3}|^0[0-9]{2})([0-9]+)?([0-9]{4})/,"$1-$2-$3").replace("--", "-") ); });
</script>
</body>
</html>