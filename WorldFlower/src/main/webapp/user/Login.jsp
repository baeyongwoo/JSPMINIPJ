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
    <title>로그인</title>

    <script type="text/javascript">
    
    	$(document).ready(function(){
    		 
    		var operForm = $("#operForm"); 
    			
   			$("button[data-oper='regist']").on("click", function(e){
   				e.preventDefault();
   				operForm.attr("method", "get");
   				operForm.attr("action", "/join.do");
   				operForm.submit();
   			});
   			
   			$("button[data-oper='findPwd']").on("click", function(e){
   				e.preventDefault();
   				operForm.attr("method", "get");
   				operForm.attr("action", "/findPwd.do");
   				operForm.submit();
   			});
   			
   			$("button[data-oper='login']").on("click", function(e){
   				e.preventDefault();
   				operForm.attr("method", "post");
   				if($("#userId").val()==""){
			            alert("아이디를 입력하세요!!!");
			            $("#userId").focus();
		
			     } else if ($("#pwd").val()==""){
			    	 alert("비밀번호를 입력하세요");
			    	 $("#pwd").focus(); 
			     } else {
					operForm.attr("action", "/login.do");
			    	operForm.submit();
			     }
   			});
    						
	    })
	    
	  function goList(e){
         location.href="/index.do";
      }
	    
	    
    </script>
</head>
<body>
    <div class="container" style="text-align: center; margin-top: 90px;">
        <a class="navbar-brand" href="/index.do">
        <img src="/img/logo.png" alt="Logo">
        </a>
        
        <div class="container d-flex justify-content-center">
            <div class="manu" style="margin-bottom:100px;width:40%;">
            	<h2>로그인</h2>
            	<img id="bird" src="/img/bird-2934141_1280.png">
                <form method="post" action="/login.do" id="operForm">
                <input type="hidden" name="prevUrl" value="${prevUrl}">
                <input type="hidden" id="nickname" name="nickname" value="${ dto.userNickName }">
                    <div class="mb-3 mt-3">
                        <label for="uname" class="form-label">아이디:</label>
                        <input type="text" class="form-control" id="userId" placeholder="Id" name="userId">
                    </div>
                <div class="mb-3">
                    <label for="pwd" class="form-label">비밀번호:</label>
                    <input type="password" class="form-control" id="pwd" placeholder="password" name="pwd">
                </div>
                
                <!-- 추가 -->
                <div style="font-size:x-large; min-height:120px">
	                <c:if test="${pCount<3 }">
		                ${message }
	                </c:if>
	                <c:if test="${pCount==3||pCount==4}">
	                	${pCount}회 틀리셨습니다.<br>5회 틀릴 경우 해당 아이디는 하루동안 로그인이 제한됩니다.
	                </c:if>
	                <c:if test="${pCount==5}">하루동안 로그인이 제한됩니다.<br>(비밀번호 5회 틀림)</c:if>
                </div>
                <div class="pt-3">
	                <button type="submit" data-oper="login" class="btn btn-success">로그인</button>
	                <button type="reset" data-oper="cancle" class="btn btn-warning">취소</button>
					<button type="submit" data-oper="regist" class="btn btn-primary">회원가입</button>
	                <button type="button" class="btn btn-info" onclick="goList(event)">메인페이지</button>
                </div>
                </form>
            </div>
        </div>
    </div >
   <%@ include file="/common/footer.jsp" %>

</body>
</html>
