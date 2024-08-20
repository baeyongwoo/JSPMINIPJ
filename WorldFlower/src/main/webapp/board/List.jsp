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
    <link href="css/style.css" rel="stylesheet">
    <title>목록</title>
</head>
     <style>
     	#page{
     		word-spacing:1vw; padding-top:2vw;
     	}
     	#page a{
     		text-decoration:none;
     	}
     	#page a:hover{
     		color:black; font-weight: 700;
     	}
        .card-img-top {
            height: 13vw; object-fit:cover;
        }

    </style>
<body>
	<%@ include file="/common/header.jsp" %>
   <div style="width:95%;" class="mx-auto">
	<!-- 검색폼 ------------------------------------------------------>
	<form method="get">
        <div class="manu" style="text-align:center" style="">
    <h2 style="text-align:center">목록</h2>
			<div class="input-group mx-auto w-50" >
				<select name="searchField" class="form-select me-1" style="width:20% !important">
					<c:forTokens var="opt" items="Flower,Nation,Title,Writer,Content" delims=",">
						<c:if test="${map.searchField==opt }">
							<option value="${opt}" selected>${opt}</option>						
						</c:if>
						<c:if test="${map.searchField!=opt }">
							<option value="${opt}">${opt}</option>				
						</c:if>
					</c:forTokens>
				</select>
				<input type="text" name="searchWord" value="${map.searchWord }" style="width:55% !important">
				<input type="submit" value="검색" style="width:15% !important">
			</div>
            <!-- 검색폼.end -->
            <div class="container" style="margin-top: 50px;">
                <div class="row">
                	<c:choose>
						<c:when test="${empty boardLists}">
							<div class="col-sm-12">
								검색결과가 없습니다.
							</div>
							<div style="height:200px"></div>
						</c:when>
						<c:otherwise>
		                    <c:forEach items="${boardLists}" var="row" varStatus="loop">
		                        <div class="col-sm-3">
		                            <div class="card">
		                                <a href="/view.do?bno=${row.bno}">
		                                    <c:if test="${!empty row.fimgsrc}">
		                                        <img src="/upload/${row.fimgsrc}" class="card-img-top" alt="${row.fname }">
		                                    </c:if>
		                                    <c:if test="${empty row.fimgsrc}">
		                                        <img src="/img/logo.png" class="card-img-top" alt="Flower Image">
		                                    </c:if>
		                                </a>
		                                <div class="card-body">
		                                    <p class="card-title">${row.title }</p>
		                                    <h5 class="card-text">작성자: ${row.userNickName }</h5>
		                                    <h5 class="card-text">조회수: ${row.viewcount }</h5>
		                                </div>
		                            </div>
		                        </div>
		                    </c:forEach>
	               		</c:otherwise>
					</c:choose>     
                </div>
             </div>    	
       
	        <!-- 페이지번호. 글쓰기 ------------------------------------------------>
	        <div class="row">
	        	<div class="col-sm-2"></div>
	        	<div class="col-sm-8" id="page"> ${paging}</div>
	        	<div class="col-sm-2"><button type="button" onclick="location.href='/write.do'">글쓰기</button></div>
	        </div>
   		</div>
	</form>	
	</div>
	<%@ include file = "/common/footer.jsp" %>
</body>
</html>