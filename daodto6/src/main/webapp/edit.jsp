<%@page import="com.glassis5.Dto"%>
<%@page import="com.glassis5.Dao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
// get방식으로 전달된 글 번호를 받아옴
String no = request.getParameter("no");
// Dto 객체를 생성하여 해당 글번호로 게시글 정보 (Dto)를 가져옴
Dao dao = new Dao();
Dto dto = dao.read(no);
%>
<!-- 게시글 수정 폼 -->
<form action="ServletEdit"><!-- 수정 내용을 처리할 ServletEdit으로 전송 -->
<!-- 글번호를 숨겨서 전송 (수정 시 어떤 글인지 식별하기 위함) -->
<input type = "hidden" name = "no" value="<%=no %>">
<!-- 제목 입력 필드 : 기존 제목을 dto에서 가져와 표시 -->
<input name = "title" value = "<%=dto.title %>">
<!-- 내용입력필드 : 기존 내용을 dto에서 가져와 표시 -->
<input name = "text" value = "<%=dto.text %>">
<!-- 수정버튼 -->
<input type = "submit" value = "수정">
</form>
<!-- 리스트 페이지로 돌아가는 링크 -->
<a href = "list.jsp">리스트로</a>
</body>
</html>