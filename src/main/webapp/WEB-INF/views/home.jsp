<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Main</title>
    <link rel="stylesheet" type="text/css" href="/resources/css/home.css">

</head>
<body>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
<%@ include file="/WEB-INF/views/include/meta.jsp" %>

<div class="main-content">
    <div>
        <h1>Welcome to Our Website!</h1>
        <p>Welcome to our site, where you can share your thoughts, experiences, and stories with the world.</p>
        <p>Whether you're a seasoned writer or just starting out, our platform provides a welcoming space for all.</p>
        <p>Join our community today and start writing!</p>
        <div class="btn-container">
            <a href="<c:url value='/board/list'/>" class="btn">Board List</a>
        </div>
    </div>
    <img src="/resources/image/main_image.png" alt="Main Image">
</div>

</body>
</html>