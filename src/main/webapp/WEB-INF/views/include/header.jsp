<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<link rel="stylesheet" type="text/css" href="/resources/css/header.css">

<header>
    <sec:authorize access="isAuthenticated()">
        <sec:authentication property="principal" var="principal"/>
    </sec:authorize>


    <div class="header-container">

        <div class="logo-container">
            <a href="<c:url value='/'/>" class="logo">logo</a>
        </div>

        <div class="menu-container">
            <a href="/" class="menu-btn">Company</a>
            <a href="<c:url value='/board/list'/>" class="menu-btn">Board</a>
            <a href="<c:url value='/member/list'/>" class="menu-btn">Mypage</a>
        </div>

        <div class="sign-container">
            <%-- 세션 스코프에 있는 loginVO 변수가 비어있는지 확인하여 로그인 여부를 판단. --%>
            <c:choose>
                <c:when test="${empty principal}">
                    <!-- 로그인 되어 있지 않은 경우 -->
                    <a href="<c:url value='/member/loginForm'/>" class="square-btn">Login</a>
                </c:when>
                <c:otherwise>
                    <!-- 로그인 되어 있는 경우 -->
                    <span>${principal.member_nickname}님</span>
                    <a href="/login/logout" class="square-btn">Logout</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</header>

</script>
