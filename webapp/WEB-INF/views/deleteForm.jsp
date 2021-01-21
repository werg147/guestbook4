<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="${pageContext.request.contextPath}/guest/delete" method="get">
		비밀번호
		<input type="text" name="password" value="">
		<button type="submit">확인</button> <br>
		<input type="hidden" name="no" value="${param.no}">
    </form>
    
    <a href="${pageContext.request.contextPath}/guest/list">메인으로 돌아가기</a>
    
</body>
</html>