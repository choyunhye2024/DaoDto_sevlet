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
//get 방식으로 넘어온 글 번호를 받아옴
String no = request.getParameter("no");
//Dao 객체 생성하여 게시글 정보(Dto)를 가져옴
Dao dao = new Dao();
Dto d = dao.read(no);
%>
<%=d.no %> <!-- 글번호 -->
<%=d.id %> <!-- 작성자 -->
<%=d.title %><!-- 제목 -->
<%=d.text %><!-- 본문 -->
<!--  글 삭제를 위한 링크 (ServletDel로 이동) -->
<a href = "ServletDel?no=<%=no %>">삭제</a>
<!--  글 수정을 위한 링크 (edit.jsp로 이동) -->
<a href = "edit.jsp?no=<%=no %>">수정</a>
<!--  리스트 페이지로 돌아가는 링크 -->
<a href = "list.jsp">리스트로</a>
</body>
</html>