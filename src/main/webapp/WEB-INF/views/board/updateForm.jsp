<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Board UpdateForm</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/detail.css">
    <%@ include file="/WEB-INF/views/include/meta.jsp" %>

</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>

<!-- 내용 -->
<form id="updateForm" method="post" action="update">
    <input type="hidden" name="board_id" value="${board.board_id}">
    <div class="detail-container">
        <div class="detail-inner-container">

            <div class="top-bno-container">
                <div class="top-bno">
                    No.     ${board.board_id}
                </div>
            </div>

            <div class="detail">
                <div class="detail-title">
                    <div class="title">
                        제목
                    </div>

                    <input type="text" name="board_title" class="content-input" value="${board.board_title}">
                </div>

                <div class="detail-content">
                    <div class="title">
                        내용
                    </div>

                    <textarea name="board_content" class="content-input" >${board.board_content}</textarea>

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

                <div class="detail-btn-container">
                    <a href="javascript:jsUpdate()" class="modify-btn">수정</a>
                </div>
            </div>
        </div>
    </div>
</form>

<script type="text/javascript" src="/resources/js/common.js"></script>
<script type="text/javascript">

    /* 삭제 */
    function jsUpdate() {
        if (confirm("수정하시겠습니까?")) {
            myFetch("update", "updateForm", json => {
                if (json.status === 0) {
                    // 성공
                    alert(json.statusMessage);
                    location.href = "detail?board_id=${board.board_id}";
                } else {
                    alert(json.statusMessage);
                }
            });
        }
    }

</script>

</body>
</html>
