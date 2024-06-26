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
            <form id="searchForm" method="post" action="member">
                <sec:csrfInput/>

                <input type="hidden" id="action" name="action" value="member">
                <input type="text" class="admin-search-input" id="searchKey" name="searchKey" value="${param.searchKey}" placeholder="이메일을 입력하세요.">
                <input type="text" class="admin-search-input" id="searchName" name="searchName" value="${param.searchName}" placeholder="이름을 입력하세요.">
                <input type="text" class="admin-search-phone-input" id="searchPhone" name="searchPhone" value="${param.searchPhone}" placeholder="전화번호를 입력하세요.">
                <input type="submit" value="검색" class="admin-search-submit">
            </form>
<%--            <div>계정잠금여부 select</div>--%>
        </div>
        <div>
            <a href="javascript:jsLock()" class="admin-lock-btn">Account Lock</a>
            <a href="javascript:jsUnlock()" class="admin-unlock-btn">Account Unlock</a>
            <a href="javascript:jsSelectDelete()" class="admin-delete-btn">Delete</a>
        </div>
    </div>
    <div class="admin-list-container">

        <table>
            <thead>
            <tr>
                <th class="checkbox-col"></th>
                <th>No.</th>
                <th>이메일</th>
                <th>이름</th>
                <th>닉네임</th>
                <th>성별</th>
                <th>핸드폰</th>
                <th>권한</th>
                <th>가입일자</th>
                <th>마지막 로그인</th>
                <th>계정 잠김 여부</th>
            </tr>
            </thead>
            <c:forEach var="member" items="${memberList}">
            <tbody>
            <tr>
                <td class="checkbox-col"><input type="checkbox"></td>
                <td>${member.member_id}</td>
                <td>${member.member_email}</td>
                <td>${member.member_name}</td>
                <td>${member.member_nickname}</td>
                <td>${member.member_gender}</td>
                <td>${member.member_phone}</td>
                <td>${member.member_roles}</td>
                <td><fmt:formatDate value="${member.member_reg_date}" pattern="yyyy.MM.dd" /></td>
                <td><fmt:formatDate value="${member.member_last_login_time}" pattern="yyyy.MM.dd HH:mm" /></td>
                <td>${member.member_account_locked}</td>
            </tr>
            </tbody>
            </c:forEach>
        </table>
    </div>
</div>

<script type="text/javascript" src="/resources/js/common.js"></script>
<script type="text/javascript">


    function jsLock() {
        const checkItems = [];

        const checkbox = document.querySelectorAll('input[type="checkbox"]:checked');
        checkbox.forEach(function (check) {
            const member_email = check.closest('tr').querySelector('td:nth-child(3)').innerText;
            checkItems.push(member_email);
        });
        // console.log(checkItems);

        if (checkItems.length < 1) {
            alert("선택된 항목이 없습니다.")
        } else {
            myFetch("lock", {check_list : checkItems}, json => {
                if (json.status == 0) {
                    alert(json.statusMessage);
                    location.reload();
                } else {
                    alert(json.statusMessage);
                }
            })
        }
    }

    function jsUnlock() {
        const checkItems = [];

        const checkbox = document.querySelectorAll('input[type="checkbox"]:checked');
        checkbox.forEach(function (check) {
            const member_email = check.closest('tr').querySelector('td:nth-child(3)').innerText;
            checkItems.push(member_email);
        });

        if (checkItems.length < 1) {
            alert("선택된 항목이 없습니다.")
        } else {
            myFetch("unlock", {check_list : checkItems}, json => {
                if (json.status == 0) {
                    alert(json.statusMessage);
                    location.reload();
                } else {
                    alert(json.statusMessage);
                }
            })
        }
    }

    function jsSelectDelete() {
        const checkItems = [];

        const checkbox = document.querySelectorAll('input[type="checkbox"]:checked');
        checkbox.forEach(function (check) {
            const member_email = check.closest('tr').querySelector('td:nth-child(3)').innerText;
            checkItems.push(member_email);
        });

        if (checkItems.length < 1) {
            alert("선택된 항목이 없습니다.")
        } else {
            myFetch("delete", {check_list : checkItems}, json => {
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
