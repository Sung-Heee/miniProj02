<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Admin Page</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/member.css">
    <%@ include file="/WEB-INF/views/include/meta.jsp" %>

</head>
<body>
<header>
    <div class="admin-menu-container">
        <div class="admin-menu-div">
            <a href="<c:url value='/admin/member'/>" class="admin-header-member-a">Member</a>
            <a href="<c:url value='/admin/board'/>" class="admin-header-board-a">Board</a>
        </div>
        <a href="/login/logout" class="admin-header-logout-a">Logout</a>
    </div>
</header>
<div class="admin-container">
    <div class="admin-top-menu-container">
        <div class="admin-search-container">
            <form id="searchForm" method="post" action="board">
                <sec:csrfInput/>

                <input type="hidden" id="action" name="action" value="board">
                <input type="text" class="admin-search-input" id="searchKey" name="searchKey" value="${param.searchKey}" placeholder="제목을 입력하세요.">
                <input type="submit" value="검색" class="admin-search-submit">
            </form>
            <%--            <div>계정잠금여부 select</div>--%>
        </div>
        <div>
            <a href="javascript:jsBoardDelete()" class="admin-delete-btn">Delete</a>
        </div>
    </div>
    <div class="admin-list-container">

        <table>
            <thead>
            <tr>
                <th class="checkbox-col"></th>
                <th>No.</th>
                <th>제목</th>
                <th>작성자</th>
                <th>작성일자</th>
                <th>조회수</th>
            </tr>
            </thead>
            <c:forEach var="board" items="${boardList}">
                <tbody>
                <tr>
                    <td class="checkbox-col"><input type="checkbox"></td>
                    <td>${board.board_id}</td>
                    <td>${board.board_title}</td>
                    <td>${board.board_writer}</td>
                    <td><fmt:formatDate value="${board.board_date}" pattern="yyyy.MM.dd HH:mm" /></td>
                    <td>${board.view_count}</td>
                </tr>
                </tbody>
            </c:forEach>
        </table>
    </div>
</div>

<script type="text/javascript" src="/resources/js/common.js"></script>
<script type="text/javascript">

    function jsBoardDelete() {
        const checkItems = [];

        const checkbox = document.querySelectorAll('input[type="checkbox"]:checked');
        checkbox.forEach(function (check) {
            const board_id = check.closest('tr').querySelector('td:nth-child(2)').innerText;
            checkItems.push(board_id);
        });

        if (checkItems.length < 1) {
            alert("선택된 항목이 없습니다.")
        } else {
            myFetch("boardDelete", {check_list : checkItems}, json => {
                if (json.status == 0) {
                    alert(json.statusMessage);
                    location.reload();
                } else {
                    alert(json.statusMessage);
                }
            })
        }
    }
</script>
</body>
</html>
