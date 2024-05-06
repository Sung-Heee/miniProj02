<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
<head>
    <title>Sign Up</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/signUp.css">
    <%@ include file="/WEB-INF/views/include/meta.jsp" %>
</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>


<div class="member-insertForm-container">
    <form id="insertForm" action="insert" method="post">
        <div class="member-insertForm-inner-container">
            <div class="member-top-container"><h1>Sign Up</h1></div>
            <div class="member-input-container">
                <div class="member-input-div">
                    <div class="member-label">이메일</div>
                    <div class="member-input">
                        <input type="email" id="userEmail" name="member_email" required>
                    </div>
                    <div class="member-duplicatedId">
                        <input type="button" id="duplicateEmail" value="중복확인">
                    </div>
                </div>

                <div class="member-input-div">
                    <div class="member-label">비밀번호</div>
                    <div class="member-input">
                        <input type="password" id="userPassword" name="member_pwd" required>
                    </div>
                </div>

                <div class="member-input-div">
                    <div class="member-label">비밀번호 확인</div>
                    <div class="member-input">
                        <input type="password" id="userPasswordCheck"  required>
                    </div>
                </div>

                <div class="member-input-div">
                    <div class="member-label">이름</div>
                    <div class="member-input">
                        <input type="text" name="member_name" required>
                    </div>
                </div>

                <div class="member-input-div">
                    <div class="member-label">닉네임</div>
                    <div class="member-input">
                        <input type="text" name="member_nickname" id="userNickname" required>
                    </div>
                    <div class="member-duplicatedId">
                        <input type="button" id="duplicateNickname" value="중복확인">
                    </div>
                </div>

                <div class="member-input-div">
                    <div class="member-label">주소</div>
                    <div class="member-input">
                        <input type="text" name="member_address" required>
                    </div>
                </div>

                <div class="member-input-div">
                    <div class="member-label">번호</div>
                    <div class="member-input">
                        <input type="text" name="member_phone" required>
                    </div>
                </div>

                <div class="member-input-div">
                    <div class="member-label">성별</div>
                    <input class="input-radio" type="radio" id="female" name="member_gender" value="여자" required> <label for="female">여자</label>
                    <input class="input-radio" type="radio" id="male" name="member_gender" value="남자" required> <label for="male">남자</label>
                </div>

                <div class="member-input-div">
                    <div class="member-label">취미</div>
                    <c:forEach var="hobby" items="${hobbyList}">
                        <input class="input-checkbox" type="checkbox" id="hobby${hobby.hobby_id}" name="hobbies" value="${hobby.hobby_name}">
                        <label for="hobby${hobby.hobby_id}">${hobby.hobby_name}</label>
                    </c:forEach>
                </div>
            </div>

            <input type="submit" value="회원가입" class="member-insertForm-btn">

        </div>
    </form>
</div>
<script type="text/javascript" src="/resources/js/common.js"></script>
<script type="text/javascript">
    const insertForm = document.getElementById("insertForm");
    const userEmail = document.getElementById("userEmail");
    const userPassword = document.getElementById("userPassword");
    const userPasswordCheck = document.getElementById("userPasswordCheck");
    const userNickname = document.getElementById("userNickname");

    // 이메일 중복확인
    let validUserEmail = "";
    // 닉네임 중복확인
    let validUserNickname = "";

    insertForm.addEventListener("submit", e => {
        e.preventDefault();

        if (validUserEmail === "" || userEmail.value !== validUserEmail) {
            alert("이메일 중복확인을 해주세요.");
            return false;
        }

        if (validUserNickname === "" || userNickname.value !== validUserNickname) {
            alert("닉네임 중복확인을 해주세요.");
            return false;
        }

        if (userPassword.value !== userPasswordCheck.value) {
            alert("비밀번호가 일치하지 않습니다.");

            // 비밀번호 잘못되면 값 비워주고 focus()
            userPasswordCheck.value = "";
            userPasswordCheck.focus();
            return false;
        }

        myFetch("insert", "insertForm", json => {
            if (json.status === 0) {
                alert(json.statusMessage);
                location="loginForm";
            } else if (json.status === -1) {
                alert(json.statusMessage);
            } else {
                alert(json.statusMessage);
            }
        });
    });

    const duplicatedEmail = document.getElementById("duplicateEmail");
    const duplicateNickname = document.getElementById("duplicateNickname");
    duplicatedEmail.addEventListener("click", () => {
        const userEmail = document.getElementById("userEmail")

        if (userEmail.value === "" ) {
            alert("이메일을 입력해주세요.");
            userEmail.focus();
            return;
        }

        myFetch("existUser", {member_email : userEmail.value}, json => {
            if (json.existUser === true) {
                alert(json.statusMessage);
                validUserEmail = "";
            } else {
                alert(json.statusMessage);
                validUserEmail = userEmail.value;
            }
        })
    })

    duplicateNickname.addEventListener("click", () => {
        const userNickname = document.getElementById("userNickname")

        if (userNickname.value === "" ) {
            alert("닉네임을 입력해주세요.");
            userNickname.focus();
            return;
        }

        myFetch("existNickname", {member_nickname : userNickname.value}, json => {
            if (json.existNickname === true) {
                alert(json.statusMessage);
                validUserNickname = "";
            } else {
                alert(json.statusMessage);
                validUserNickname = userNickname.value;
            }
        })
    })
</script>
</body>
</html>
