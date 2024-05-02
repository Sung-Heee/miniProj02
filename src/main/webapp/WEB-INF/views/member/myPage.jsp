<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>MyPage</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/myPage.css">
    <%@ include file="/WEB-INF/views/include/meta.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>

<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="principal"/>
</sec:authorize>

<div class="mypage-container">
    <div class="mypage-inner-container">
        <div class="mypage-top-container">
            <div class="mypage-userid">
                ${principal.member_nickname} <span>&nbsp;님</span>
            </div>

        </div>
        <div class="mypage-content-container">
            <div class="mypage-content">
                <div class="mypage-area">
                    <div class="mypage-title-area">아이디</div>
                    <div class="mypage-content-area">${principal.member_email}</div>
                </div>
                <div class="mypage-area">
                    <div class="mypage-title-area">이름</div>
                    <div class="mypage-content-area">${principal.member_name}</div>
                </div>
            </div>
            <div class="mypage-content">
                <div class="mypage-area">
                    <div class="mypage-title-area">닉네임</div>
                    <div class="mypage-content-area">${principal.member_nickname}</div>
                </div>
                <div class="mypage-area">
                    <div class="mypage-title-area">성별</div>
                    <div class="mypage-content-area">${principal.member_gender}</div>
                </div>
            </div>
            <div class="mypage-content">
                <div class="mypage-area">
                    <div class="mypage-title-area">주소</div>
                    <div class="mypage-content-area">${principal.member_address}</div>
                </div>
                <div class="mypage-area">
                    <div class="mypage-title-area">핸드폰</div>
                    <div class="mypage-content-area">${principal.member_phone}</div>
                </div>
            </div>
            <div class="mypage-content">
                <div class="mypage-area">
                    <div class="mypage-title-area">취미</div>
                    <div class="mypage-content-area"></div>
                </div>
                <div class="mypage-area">
                    <div class="mypage-title-area">가입일</div>
                    <div class="mypage-content-area">${principal.member_reg_date}</div>
                </div>
            </div>
        </div>
        <div class="mypage-btn-container">
            <div class="mypage-btn-div">
                <a href="member/updateForm?member_id=${principal.member_id}" class="mypage-modify-btn">수정</a>
                <a href="javascript:jsWithdraw()" class="mypage-withdraw-btn">탈퇴</a>
            </div>
        </div>
    </div>
</div>

</body>
</html>
