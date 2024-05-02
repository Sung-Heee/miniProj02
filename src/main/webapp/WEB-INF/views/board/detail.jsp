<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Board Detail</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/detail.css">
    <%@ include file="/WEB-INF/views/include/meta.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>

<!-- 내용 -->
<div class="detail-container">
    <div class="detail-inner-container">

        <div class="top-bno-container">
            <div class="top-bno">
                No.     ${board.board_id}
            </div>
            <div class="view-count">
                조회수 : ${board.view_count}
            </div>
        </div>

        <div class="detail">
            <div class="detail-title">
                <div class="title">
                    제목
                </div>

                <div class="content">
                    ${board.board_title}
                </div>
            </div>

            <div class="detail-content">
                <div class="title">
                    내용
                </div>

                <div class="content">
                    ${board.board_content}
                </div>
            </div>

            <div class="detail-file">
                <div class="title">
                    첨부파일
                </div>

                <div class="file-content" style="cursor: pointer" id="boardFile" data-board-file-no="" onclick="onBoardFileDownload(this)">
                    ${board.boardFileVO.original_filename}
                </div>
            </div>

            <div class="detail-writer">
                <div class="title">
                    작성자
                </div>

                <div class="content">
                    ${board.board_writer}
                </div>
            </div>

            <div class="detail-date">
                <div class="title">
                    작성일
                </div>

                <div class="content">
                    ${board.board_date}
                </div>
            </div>
            <%--            <c:choose>--%>
            <%--                <c:when test="${loginVO.userId eq 'admin'}">--%>
            <%--                    <div class="detail-btn-container">--%>
            <%--                        <a href="board.do?action=updateForm&bno=${board.bno}" class="modify-btn">수정</a>--%>
            <%--                        <a href="javascript:jsDelete()" class="delete-btn">삭제</a>--%>
            <%--                    </div>--%>
            <%--                </c:when>--%>
            <%--                <c:when test="${loginVO.userId eq board.bwriter}">--%>
            <%--                    <div class="detail-btn-container">--%>
            <%--                        <a href="board.do?action=updateForm&bno=${board.bno}" class="modify-btn">수정</a>--%>
            <%--                        <a href="javascript:jsDelete()" class="delete-btn">삭제</a>--%>
            <%--                    </div>--%>
            <%--                </c:when>--%>
            <%--            </c:choose>--%>


            <div class="detail-btn-container">
                <input placeholder="비밀번호" type="password" id="password" class="password-input">
                <a href="javascript:jsUpdate()" id="modify-btn" class="modify-btn">수정</a>
                <a href="javascript:jsDelete()" class="delete-btn">삭제</a>
            </div>
        </div>
    </div>
</div>

<form id="detailForm" method="post" action="delete">
    <input type="hidden" name="board_id" value="${board.board_id}">
</form>

<script type="text/javascript" src="/resources/js/common.js"></script>
<script type="text/javascript">
const boardFile = document.getElementById("boardFile");
boardFile.setAttribute("data-board-file-no", ${board.boardFileVO.board_file_id});

const onBoardFileDownload = boardFile => {
    alert("첨부파일 번호 : " + boardFile.getAttribute("data-board-file-no"));
}

const password = document.getElementById("password");

function jsUpdate() {
    if (password.value !== "") {
        myFetch("checkPwd", {board_id : ${board.board_id}, input_pwd : password.value}, json => {
            if (json.status === 0) {
                // 비밀번호가 맞을 때 수정 페이지로 이동
                location.href = "updateForm?board_id=${board.board_id}";
            } else {
                alert(json.statusMessage);
            }
        });
    } else {
        alert("설정한 비밀번호를 입력해주세요.");
        password.style.border = '2px solid red';
    }
}

function jsDelete() {
    if (password.value !== "") {
        myFetch("checkPwd", {board_id : ${board.board_id}, input_pwd : password.value}, json => {
            if (json.status === 0) {
                myFetch("delete", "detailForm", json => {
                    if (json.status === 0) {
                        alert(json.statusMessage);
                        location = "list";
                    } else {
                        alert(json.statusMessage);
                    }
                });
            } else {
                alert(json.statusMessage);
            }
        });
    } else {
        alert("설정한 비밀번호를 입력해주세요.");
        password.style.border = '2px solid red';
    }
}
</script>

</body>
</html>
