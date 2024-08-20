<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
    <link href="https://fonts.googleapis.com/css2?family=Nanum+Pen+Script&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Hi+Melody&family=Nanum+Pen+Script&display=swap" rel="stylesheet">
    <link href="/css/style.css" rel="stylesheet">
    <title>WorldFlower</title>
</head>
<body>
    <%@ include file="/common/header.jsp" %>
    
    <!-- content 시작 -->
    <div class="container-fluid mt-3">
        <div class="row">
            <div class="col-sm-9">
                <div class="content">
                    <div class="main">
                    
                      <div class="manu">
                        <h3>최신순</h3>
                        <div class="row">
                            <c:forEach items="${dateList}" var="var">
                            	<div class="col-sm-6">
	                            	<div class="card">
	                            		 <a href="/view.do?bno=${var.bno}">
						                     <c:choose>
					                    		<c:when test="${empty var.fimgsrc }">
					                    			<img src="/img/logo.png" class="card-img-top" alt="${var.fname }">
					                    		</c:when>
					                    		<c:otherwise>
					                   				<img src="/upload/${var.fimgsrc }" class="card-img-top" alt="${var.fname }">
					                    		</c:otherwise>
					                    	</c:choose>
				                    	</a>
	                                    <div class="card-body">
	                                        <p class="card-title">${var.fname }</p>
	                                        <h5 class="card-text">작성자: ${var.userNickName}</h5>
	                                        <h5 class="card-text">게시일: ${var.updatedate}</h5>
	                                    </div>
	                                </div>
                                </div>
                            </c:forEach>
                            <div class="d-flex justify-content-center">
                                <button style="display: block" class="w-25" onclick="location.href='/list.do'">더보기</button>
                            </div>
                        </div>
                      </div>
                      
                      <div class="manu">
                        <h3>조회순</h3>
                        <div class="row">
                        	<c:forEach items="${viewList}" var="var">
	                            <div class="col-sm-6">
	                                <div class="card">
	                                	<a href="/view.do?bno=${var.bno}">
		                                	<c:choose>
					                    		<c:when test="${empty var.fimgsrc }">
					                    			<img src="/img/logo.png" class="card-img-top" alt="${var.fname }">
					                    		</c:when>
					                    		<c:otherwise>
					                   				<img src="/upload/${var.fimgsrc }" class="card-img-top" alt="${var.fname }">
					                    		</c:otherwise>
					                    	</c:choose>
				                    	</a>
	                                    <div class="card-body">
	                                        <p class="card-title">${var.fname }</p>
	                                        <h5 class="card-text">작성자: ${var.userNickName}</h5>
	                                        <h5 class="card-text">조회수: ${var.viewcount}</h5>
	                                    </div>
	                                </div>
	                            </div>
							</c:forEach>
     						
                            <div class="d-flex justify-content-center">
                                <button style="display: block" class="w-25" onclick="location.href='/list.do'">더보기</button>
                            </div>
                        </div>
                    </div>
                  </div>
                </div>
            </div>
            <!-- side 시작 -->
            <div class="col-sm-3">
                 <form class="search-form" action="/list.do" method="get">
                    <div>
	                    <h3>빠른 검색</h3>
	                    <select name="searchField" class="form-select w-50">
		                    <c:forTokens var="opt" items="Flower,Nation" delims=",">
								<option value="${opt}">${opt}</option>										
							</c:forTokens>
						</select>
	                    <input class="form-control mx-auto mt-2" type="text" placeholder="Search" name="searchWord" value="${map.searchWord }">
	                    <button style="display:block" class="btn btn-primary mt-2 w-50 mx-auto" type="submit">Search</button>
					</div>                  
                  </form> 
                <div class="sid1 px-auto">
                    <h3 class="w-100 mt-1">접속중인 회원</h3>
                    <ul class="list-group w-100 mx-auto" >
                    	<c:forEach items="${userNickName}" var="var">
                        	<li class="list-group-item">${var}</li>
                        </c:forEach>
                    </ul>
                </div>
<!--                  <div class="sid2">
                    <h4>검색어 순위</h4>
                    <ul class="list-group">
                        <li class="list-group-item">검색어 1</li>
                        <li class="list-group-item">검색어 2</li>
                        <li class="list-group-item">검색어 3</li>
                        <li class="list-group-item">검색어 4</li>
                        <li class="list-group-item">검색어 5</li>
                    </ul>
                </div> -->
            </div>
            <!-- side 끝 -->
        </div>
    </div>
    <!-- content 끝 -->

    <%@ include file = "/common/footer.jsp" %>
</body>
</html>
