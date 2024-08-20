<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Hi+Melody&family=Nanum+Pen+Script&display=swap" rel="stylesheet">
    <link href="/css/style.css" rel="stylesheet">
    <title>회원정보</title>
    <script>
        $(document).ready(function(){
            $("#footer").load("footer.html");
        });

        function validateForm(form){
            if(form.nickname.value === ""){
                alert("닉네임을 입력하세요");
                form.nickname.focus();
                return false;
            }
            if(form.pwd.value === ""){
                alert("비밀번호를 입력하세요");
                form.pwd.focus();
                return false;
            }
            if(!validatePassword(form.pwd.value)){
                alert("비밀번호는 8자 이상, 영문, 숫자, 특수문자(@$!%*#?&)를 포함해야 합니다.");
                form.pwd.focus();
                return false;
            }
            if(form.pswdch.value === ""){
                alert("비밀번호 확인을 입력하세요");
                form.pswdch.focus();
                return false;
            }
            if(form.pswdch.value !== form.pwd.value){
                alert("비밀번호와 일치하지 않습니다.");
                form.pswdch.focus();
                form.pwd.focus();
                return false;
            }
            alert("회원정보가 수정되었습니다.");
            return true;
        }

        function validatePassword(password) {
            const passwordRegex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/;
            return passwordRegex.test(password);
        }

        $(document).ready(function(){
            $('#pwd').on('input', function() {
                const strongPasswordMessage = $('.strongPassword-message');
                if(validatePassword($(this).val())){
                    strongPasswordMessage.addClass('hide');
                } else {
                    strongPasswordMessage.removeClass('hide');
                }
            });
        });
		function goList(e){
			location.href="/list.do";
		}
    </script>
    <style>
        .hide {
            display: none;
        }
    </style>
</head>
<body>
<form action="userUpdate.do" method="post" onsubmit="return validateForm(this);">
    <div class="container" style="text-align: center; margin-top: 90px;">
        <a class="navbar-brand" href="/">
            <img src="/img/logo.png" alt="Logo">
        </a>
        <div class="container d-flex justify-content-center">
            <div class="manu" style="width: 40%;">
                <h2>회원정보</h2>
                <img id="bird" src="/img/bird-2934141_1280.png">
                <div class="mb-3 mt-3">
                    <label for="nickname" class="form-label">닉네임</label>
                    <input type="text" class="form-control" id="nickname" placeholder="nickname" name="nickname" value="${ dto.userNickName }" required>
                    <div class="invalid-feedback">닉네임을 입력하세요.</div>
                </div>
                <div class="mb-3">
                    <label for="pwd" class="form-label">비밀번호</label>
                    <input type="password" class="form-control" id="pwd" placeholder="password" name="pwd" value="${ dto.pwd }" required>
                    <div class="invalid-feedback">비밀번호를 입력하세요.</div>
                    <div class="strongPassword-message hide">8글자 이상, 영문, 숫자, 특수문자(@$!%*#?&)를 사용하세요</div>
                </div>
                <div class="mb-3">
                    <label for="pwdch" class="form-label">비밀번호 확인</label>
                    <input type="password" class="form-control" id="pwdch" placeholder="password check" name="pswdch" required>
                    <div class="invalid-feedback">비밀번호 확인을 입력하세요.</div>
                </div>
                <button type="submit" class="btn btn-success">회원정보수정</button>
                <button type="reset" class="btn btn-warning">취소</button>
                <button type="button" onclick="goList(event)" class="btn btn-info">목록으로</button>
            </div>
        </div>
    </div>
</form>
<%@ include file="/common/footer.jsp" %>
</body>
</html>
