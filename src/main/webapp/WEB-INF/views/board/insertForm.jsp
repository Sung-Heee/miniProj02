<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Board InsertForm</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/insertForm.css">
    <%@ include file="/WEB-INF/views/include/meta.jsp" %>

    <%-- ckeditor 관련 자바 스크립트  --%>
    <script src="https://cdn.ckeditor.com/ckeditor5/12.4.0/classic/ckeditor.js"></script>
    <script src="https://ckeditor.com/apps/ckfinder/3.5.0/ckfinder.js"></script>

</head>
<body>
<sec:authorize access="isAuthenticated()">
    <sec:authentication property="principal" var="principal"/>
</sec:authorize>

<%@ include file="/WEB-INF/views/include/header.jsp" %>

<div class="insertForm-container">

    <form id="insertForm" action="insert" method="post">
        <%-- csrf 토큰 설정 --%>
        <sec:csrfInput/>

        <input type="hidden" name="member_email" value="${principal.member_email}">

        <div class="insertForm-inner-container">
            <div class="insertForm-top-container"><h1>Writing</h1></div>
            <div class="insertForm-title-container">
                <div class="insertForm-title">Title</div>
                <input class="insertForm-title-input" type="text" name="board_title" placeholder="제목을 입력해주세요.">
            </div>
            <div class="insertForm-content-container">

                <div class="insertForm-content-input-container">
                    <textarea class="insertForm-content-input" id="board_content" name="board_content" placeholder="내용을 입력해주세요."></textarea>
                </div>
            </div>
<%--            <div class="insertForm-file-container">--%>
<%--                <div class="insertForm-file">File</div>--%>
<%--                <input class="insertForm-file-input" type="text">--%>
<%--            </div>--%>
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



// ck-editor
let board_content; // ck-editor의 객체를 저장하기 위한 변수
ClassicEditor.create(document.querySelector('#board_content'))
.then(editor => {
    console.log('편집기 초기화');
    window.board_content = editor;
    console.log(board_content);
})
.catch(error => {
    console.error(error);
});

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
