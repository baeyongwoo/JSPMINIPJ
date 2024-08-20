<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세보기</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<link href="https://fonts.googleapis.com/css2?family=Hi+Melody&family=Nanum+Pen+Script&display=swap" rel="stylesheet">
<link href="css/style.css" rel="stylesheet">                      
<script>
function goList(e){
    location.href="/list.do";
 }
</script>

</head>
<body>
	<%@ include file="/common/header.jsp" %>
	<div style="width:95%;" class="mx-auto">
	<form action="/deletePost.do" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
        <div class="manu" style="text-align: center; padding: 50px;">
            <h1>게시글</h1>
	<div class="row">
        <div class="col-sm-6">
            <label for="title" class="form-label">제목</label>
            <div class="form-floating mb-3 mt-3">
                <input type="text" name="title" placeholder="${ dto.title }" readonly>
            </div>
            
            <label for="flowername" class="form-label">꽃이름</label>
            <div class="form-floating mb-3 mt-3">
                <input type="text" name="fname" placeholder="${ dto.fname }" readonly>
            </div>
            
            <label for="worldname" class="form-label">나라이름</label>
            <div class="form-floating mb-3 mt-3">
                <input type="text" name="nname" placeholder="${ dto.nname }" readonly>
            </div>
                    
            <label for="content" class="form-label">내용:</label>
                <div>
            <textarea name="content" rows="10" style="width: 100%;" placeholder="${ dto.content }" maxlength="3000" readonly>${ dto.content }</textarea>
            </div>
                </div>
                <div class="col-sm-6 ">
                    <div id="uploadImgDiv">
                    	<c:if test="${!empty dto.fimgsrc}">
                        ${ dto.fimgname }
                        <a href="/download.do?fimgname=${ dto.fimgname }&fimgsrc=${ dto.fimgsrc }&bno=${ dto.bno }">
							[다운로드]
						</a>
                        <img src="/upload/${dto.fimgsrc}" class="card-img-top" alt="${dto.fname }">
	                    </c:if>
	                    <c:if test="${empty dto.fimgsrc}">
                        <img src="/img/logo.png" class="card-img-top" alt="Flower Image">                            
						</c:if>


                    </div>			
                </div>
                <div class="container-fluid">
                    <div class="container d-flex justify-content-center">  
                      	
		           	<c:if test="${userId == null }">
			           <button type="button" class="btn btn-info" onclick ="location.href='/'; "style="float:left">메인페이지</button>
			           <button type="button" class="btn btn-info" onclick ="location.href='/list.do'; "style="float:right">목록으로</button>
		        	   <input type="hidden" name="bno" value="${param.bno}" />
		            </c:if>
		            <c:if test="${userId != null }">
		             	<button type="button" class="btn btn-info" onclick ="location.href='/'; "style="float:left">메인페이지</button>
						<button type="button" class="btn btn-success" onclick="location.href='/edit.do?&bno=${param.bno}';">수정하기</button>
                        <input type="hidden" name="bno" value="${param.bno}" />
						<button type="submit" class="btn btn-danger" value="삭제">삭제하기</button>
						<button type="button" class="btn btn-info" onclick="goList(event)">목록으로</button>
					</c:if>
                    </div>
                </div>
            </div>
        </div>
    </form>
	</div>	
	<%@ include file="/common/footer.jsp" %>
</body>
</html>