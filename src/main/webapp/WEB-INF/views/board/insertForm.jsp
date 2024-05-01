<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Board InsertForm</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/insertForm.css">
    <%@ include file="/WEB-INF/views/include/meta.jsp" %>

</head>
<body>
<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="principal"/>
</sec:authorize>

<%@ include file="/WEB-INF/views/include/header.jsp" %>

<div class="insertForm-container">

    <form id="insertForm" action="insert" method="post">
<%--        <input type="hidden" name="member_nickname" value="${principal.member_nickname}">--%>
        <input type="hidden" name="member_email" value="${principal.member_email}">

        <div class="insertForm-inner-container">
            <div class="insertForm-top-container"><h1>Writing</h1></div>
            <div class="insertForm-title-container">
                <div class="insertForm-title">Title</div>
                <input class="insertForm-title-input" type="text" name="board_title" placeholder="제목을 입력해주세요.">
            </div>
            <div class="insertForm-content-container">
                <div class="insertForm-content">
                    <div class="insertForm-content-area">Content</div>
                </div>
                <div class="insertForm-content-input-container">
                    <textarea class="insertForm-content-input" name="board_content" placeholder="내용을 입력해주세요."></textarea>
                </div>
            </div>
            <div class="insertForm-file-container">
                <div class="insertForm-file">File</div>
                <input class="insertForm-file-input" type="text">
            </div>
            <div class="insertForm-pwd-container">
                <div class="insertForm-pwd">Password</div>
                <input class="insertForm-pwd-input" type="password" name="board_pwd" placeholder="비밀번호를 입력해주세요.">
            </div>
            <div class="insertForm-btn-container">
                <input type="submit" value="등록" class="insertForm-btn"/>
            </div>
        </div>
    </form>

</div>

<script type="text/javascript" src="/resources/js/common.js"></script>
<script type="text/javascript">

const insertForm = document.getElementById("insertForm");

insertForm.addEventListener("submit", e => {
    e.preventDefault();

    myFetch("insert", "insertForm", json => {
        if (json.status === 0) {
            alert(json.statusMessage);
            location = "/board/list";
        } else {
            alert(json.statusMessage);
        }
    });
});

</script>
</body>
</html>
