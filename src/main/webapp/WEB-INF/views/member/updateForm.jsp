<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Member UpdateForm</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/myPage.css">
    <%@ include file="/WEB-INF/views/include/meta.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>

<sec:authorize access="isAuthenticated()">
  <sec:authentication property="principal" var="principal"/>
</sec:authorize>

<form id="myPageUpdateForm" method="post" action="update">
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
            <input type="hidden" name="member_email" value="${principal.member_email}">
          </div>
          <div class="mypage-area">
            <div class="mypage-title-area">이름</div>
            <div class="mypage-content-area">
              <input class="mypage-input" type="text" name="member_name" value="${principal.member_name}">
            </div>
          </div>
        </div>
        <div class="mypage-content">
          <div class="mypage-area">
            <div class="mypage-title-area">닉네임</div>
            <div class="mypage-content-area">
              <input type="text" class="mypage-input" name="member_nickname" value="${principal.member_nickname}">
            </div>
          </div>
          <div class="mypage-area">
            <div class="mypage-title-area">성별</div>
            <div class="mypage-content-area">
              <input type="radio" id="female" name="member_gender" value="여자" class="input-radio" <c:if test="${principal.member_gender eq '여자'}">checked</c:if>> <label for="female">여자</label>
              <input type="radio" id="male" name="member_gender" value="남자" class="input-radio" <c:if test="${principal.member_gender eq '남자'}">checked</c:if>> <label for="male">남자</label>
            </div>
          </div>
        </div>
        <div class="mypage-content">
          <div class="mypage-area">
            <div class="mypage-title-area">주소</div>
            <div class="mypage-content-area">
              <input class="mypage-input" type="text" name="member_address" value="${principal.member_address}"></div>
          </div>
            <div class="mypage-area">
              <div class="mypage-title-area">핸드폰</div>
              <div class="mypage-content-area">
                <input class="mypage-input" type="text" name="member_phone" value="${principal.member_phone}"></div>
            </div>
        </div>
        <div class="mypage-content">
          <div class="mypage-area">
            <div class="mypage-title-area">취미</div>
            <div class="mypage-content-area">
              <c:forEach var="hobby" items="${hobbyList}">
                <div class="input-checkbox">
                  <input type="checkbox" id="hobby${hobby.hobby_id}" name="hobbies" value="${hobby.hobby_name}" <c:if test="${principal.hobbyList.contains(hobby.hobby_name)}">checked</c:if>>
                  <label for="hobby${hobby.hobby_id}">${hobby.hobby_name}</label>
                </div>
              </c:forEach>
            </div>
          </div>
          <div class="mypage-area">
            <div class="mypage-title-area">가입일</div>
            <div class="mypage-content-area"><fmt:formatDate value="${principal.member_reg_date}" pattern="yyyy.MM.dd" /></div>
            <input type="hidden" name="reg_date" value="${principal.member_reg_date}">
          </div>
        </div>
      </div>

      <div class="mypage-btn-container">
        <div class="mypage-btn-div">
          <a href="javascript:jsUpdate()" class="mypage-modify-btn">수정하기</a>
        </div>
      </div>
    </div>
  </div>

  <input type="hidden" name="member_id" value="${principal.member_id}">

</form>

<script type="text/javascript" src="/resources/js/common.js"></script>
<script type="text/javascript">
function jsUpdate() {
  myFetch("update", "myPageUpdateForm", json => {
    if (json.status === 0) {
      alert(json.statusMessage);
      location.href="myPage";
    } else {
      alert(json.statusMessage);
    }
  })
}
</script>
</body>
</html>
