<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
   	<script type="text/javascript" src="/js/UserJoin.js"></script>
    
    <title>회원가입</title>
    <script>
        $(document).ready(function(){
            $("#footer").load("footer.html");
        });
    </script>

    <style>
        .hide {
            display: none;
        }
    </style>
    
</head>
<body>
    <div class="container" style="text-align: center; margin-top: 90px;">

        <a class="navbar-brand" href="/">
            <img src="/img/logo.png" alt="Logo">
        </a>

        <div class="container d-flex justify-content-center" style="width:100%">
            <div class="manu" style="width: 40%;">
                <h2>회원가입</h2>
                <img id="bird" src="/img/bird-2934141_1280.png">
                <form action="/join.do" method="post" onsubmit="return validateForm(this)">
                   
                    <div class="mb-3 mt-3">
                        <label for="uname" class="form-label">아이디</label>
                        <input type="text" class="form-control" id="userid" onchange="checkUserid();" placeholder="id" name="userid" required>
                        <div class="invalid-feedback">아이디를 입력하세요.</div>
                        <div id="message"></div>
                    </div>
                    
                     <div class="mb-3 mt-3">
                        <label for="nickname" class="form-label">닉네임</label>
                        <input type="text" class="form-control" id="nickname"  placeholder="nickname" name="nickname"  required>
                        <div class="invalid-feedback">닉네임을 입력하세요.</div>
                    </div>
                <div class="mb-3">
                    <label for="pwd" class="form-label">비밀번호</label>
                    <input type="password" class="form-control" id="pwd" placeholder="password" name="pwd" required>
                    <div class="invalid-feedback">비밀번호를 입력하세요.</div>
                    <div class="strongPassword-message hide">8글자 이상, 영문, 숫자, 특수문자(@$!%*#?&)를 사용하세요</div>
                </div>
                <div class="mb-3">
                    <label for="pwdch" class="form-label">비밀번호 확인</label>
                    <input type="password" class="form-control" id="pwdch" placeholder="password check" name="pwdch" required>
                    <div class="invalid-feedback">비밀번호를 다시 입력하세요.</div>
                </div>
                <div class="mb-3">
                    <label for="pwd" class="form-label">email</label>
                    <input type="email" class="form-control" id="email" onchange="checkUserEmail();" placeholder="Email" name="email" required>
                    <div class="invalid-feedback">이메일을 입력하세요.</div>
                    <div id="messageEmail"></div>
                    <button id="emailAuth" class="my-1 mb-3 btn btn-info" style="display: none" onclick="sendEmail();" ></button>
                    <label id="emailLabel" for="emailCheck" style="display: none; width:100%" class="form-label mb-1">email code</label>
                    <input type="text" style="display: none" class="form-control mt-0" id="emailCheck" name="emailCheck" required>
                    <button type="button" class="my-1 btn btn-info" style="display: none" id="checkBtn" onclick="emailAuthCheck();">인증확인</button>
                </div>
                <button type="submit" class="btn btn-success">회원가입</button>
                <button type="reset" class="btn btn-warning" onclick="removeECheck()">취소</button>
                <button class="btn btn-info" onclick="location.href='/index.do'">메인페이지</button>
                </form>
            </div>
            </div>
    </div>
<%@ include file="/common/footer.jsp" %>
</body>
</html>