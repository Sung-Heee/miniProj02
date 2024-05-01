<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Board List</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/boardList.css">
    <!-- Bootstrap CSS -->
    <%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">--%>
</head>
<body>

<!-- 헤더 -->
<%@ include file="/WEB-INF/views/include/header.jsp" %>

<form id="searchForm" action="list" method="post" >
    <input type="hidden" id="action" name="action" value="list">

    <div class="list-container">
        <div class="list-inner-container">
            <div class="list-top-menu">
                <div class="select-searchbar-container">
                    <div class="select-container">
                        <select id="size" name="size" >
                            <c:forEach var="size" items="${sizes}">
                                <option value="${size.code_id}" ${pageRequestVO.size == size.code_id ? 'selected' : ''} >${size.code_name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="searchbar-container">
                        <div class="searchbar">
                            <input type="text" placeholder="제목을 입력해주세요." id="searchKey" name="searchKey" value="${param.searchKey}" class="searchbar-input">
                            <input type="submit" value="검색" class="searchbar-a">
                        </div>
                    </div>
                </div>
                <div class="insert-container">
                    <a href="<c:url value='/board/insertForm'/>" class="insert-btn">+ 글 작성하기</a>
                </div>

                <%--                <c:choose>--%>

                <%--                    <c:when test="${empty sessionScope.loginVO}">--%>
                <%--                        <div class="searchbar-container">--%>
                <%--                            <div class="searchbar">--%>
                <%--                                <input type="text" placeholder="제목을 입력해주세요." id="searchKey" name="searchKey" value="${param.searchKey}" class="searchbar-input">--%>
                <%--                                <input type="submit" value="검색" class="searchbar-a">--%>
                <%--                            </div>--%>
                <%--                        </div>--%>

                <%--                    </c:when>--%>

                <%--                    <c:otherwise>--%>
                <%--                        <c:if test="${loginVO.userId ne 'admin'}">--%>
                <%--                            <div class="insert-container">--%>
                <%--                                <a href="board.do?action=insertForm" class="insert-btn">+ 글 작성하기</a>--%>
                <%--                            </div>--%>
                <%--                        </c:if>--%>
                <%--                    </c:otherwise>--%>
                <%--                </c:choose>--%>

            </div>

            <div class="list">
                <div class="list-top-content">
                    <div class="no-area">No.</div>
                    <div class="title-area">제목</div>
                    <div class="writer-area">작성자</div>
                    <div class="date-area">작성일자</div>
                    <div class="admin-delete-btn"></div>
                </div>
                <c:forEach var="board" items="${pageResponseVO.list}">
                    <div class="list-content">
                        <div class="no-area">${board.board_id}</div>
                        <div class="title-area"><a href="detail?board_id=${board.board_id}" class="title-a">${board.board_title}</a></div>
                        <div class="writer-area">${board.board_writer}</div>
                        <div class="date-area">${board.board_date}</div>
<%--                        <c:if test="${loginVO.user_id eq 'admin'}">--%>
<%--                            <div class="admin-delete-btn"><a href="javascript:jsDelete(${board.bno})">삭제</a></div>--%>
<%--                        </c:if>--%>
                    </div>
                </c:forEach>
            </div>


        </div>

        <!--  페이지 네비게이션 바 출력  -->
        <div class="float-end">
            <ul class="pagination flex-wrap">
                <c:if test="${pageResponseVO.prev}">
                    <li class="page-item">
                        <a class="page-link" data-num="${pageResponseVO.start -1}">이전</a>
                    </li>
                </c:if>

                <c:forEach begin="${pageResponseVO.start}" end="${pageResponseVO.end}" var="num">
                    <li class="page-item ${pageResponseVO.pageNo == num ? 'active':''} ">
                        <a class="page-link"  data-num="${num}">${num}</a>
                    </li>
                </c:forEach>

                <c:if test="${pageResponseVO.next}">
                    <li class="page-item">
                        <a class="page-link"  data-num="${pageResponseVO.end + 1}">다음</a>
                    </li>
                </c:if>
            </ul>

        </div>
    </div>

</form>

<%--<form id="listForm" action="detail" method="post">--%>
<%--    <input type="hidden" id="bno" name="bno" >--%>
<%--</form>--%>


<script type="text/javascript">
    document.querySelector("#size").addEventListener("change", e => {
        searchForm.submit();
    });

    document.querySelector(".pagination").addEventListener("click", function (e) {
        e.preventDefault()

        const target = e.target

        if(target.tagName !== 'A') {
            return
        }
        //dataset 프로퍼티로 접근 또는 속성 접근 메서드 getAttribute() 사용 하여 접근 가능
        //const num = target.getAttribute("data-num")
        const num = target.dataset["num"];

        //페이지번호 설정
        searchForm.innerHTML += `<input type='hidden' name='pageNo' value='\${num}'>`;
        searchForm.submit();
    });


    // function jsDelete(bno) {
    //     if(confirm("게시물을 삭제하시겠습니까?")) {
    //         action.value = "delete";
    //         document.getElementById("bno").value = bno;
    //         myFetch("board.do", "listForm", json => {
    //             if (json.status == 0) {
    //                 alert(json.statusMessage);
    //                 location="board.do?action=list";
    //             } else {
    //                 alert(json.statusMessage);
    //             }
    //         })
    //     }
    // }
</script>
</body>
</html>