<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	   <!-- nav 시작 -->
    <nav class="navbar navbar-expand-sm">
        <div class="container">
            <a class="navbar-brand" href="/index.do">
              <img src="/img/logo.png" alt="Logo">
          </a>
            <div class="navbar-nav">
                <!-- <ul class="navbar-nav me-auto">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">분기별</a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#">1분기</a></li>
                            <li><a class="dropdown-item" href="#">2분기</a></li>
                            <li><a class="dropdown-item" href="#">3분기</a></li>
                            <li><a class="dropdown-item" href="#">4분기</a></li>
                        </ul>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">대륙별</a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="#">아시아</a></li>
                            <li><a class="dropdown-item" href="#">남미</a></li>
                            <li><a class="dropdown-item" href="#">어딘가</a></li>
                        </ul>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">최신순</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#">인기순</a>
                    </li>
                </ul> -->
            </div>
            
            <c:if test="${userId == null }">
            <div class="navbar-nav right">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="/login.do">로그인</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/join.do">회원가입</a>
                    </li>
                </ul>
            </div>
            </c:if>
            <c:if test="${userId != null }">
             <div class="navbar-nav right">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="/userUpdate.do?userId=${ userId }" >${ userId }</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/logout.do">로그아웃</a>
                    </li>
                </ul>
            </div>
			</c:if>
        </div>
    </nav>
    <!-- nav 끝 -->