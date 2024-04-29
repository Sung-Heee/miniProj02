<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>

<html>
<head>
    <title>LoginForm</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/loginForm.css">

</head>
<body>

<div class="login-container">
        <div class="login-inner-container">
            <div class="login-top-container"><h1>Login</h1></div>
            <div class="login-input-container">
                <form action="/login" method="post" >
                    <sec:csrfInput/>
                    <div class="login-id">
                        <input type="email" class="login-id-input" name="email" required="required" placeholder="id">
                    </div>
                    <div class="login-password">
                        <input type="password" class="login-password-input" name="password" required="required" placeholder="password">
                    </div>
                    <div class="login-autologin-container">
                        <div class="login-autologin">
                            <label for="autologin">자동로그인</label> <input type="checkbox" id="autologin" name="autologin" value="Y">
                        </div>
                    </div>
                </form>
            </div>
            <div class="login-btn-container">
                <div class="login-btn-div">
                    <input type="submit" value="Login" class="login-btn">
                </div>
                <div class="login-signup">
                    계정이 없으신가요? <a href="<c:url value='/login/signUp'/>" class="login-signup-a">회원가입</a>
                </div>
                <!-- <a href="member.do?action=list" class="btn">취소</a> -->
            </div>
        </div>

</div>

<script>
    msg = "${error ? exception : ''}";
    if (msg !== "") {
        alert(msg);
    }
</script>
</body>
</html>
