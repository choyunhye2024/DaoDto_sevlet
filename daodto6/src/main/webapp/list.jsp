<%@page import="com.glassis5.Board"%>
<%@page import="com.glassis5.Dto"%>
<%@page import="java.util.ArrayList"%>
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
글번호     / 제목     / 작성자  <hr>
<%

// 현재 페이지 번호를 GET 방식으로 받아옴 (초기 페이지는 1)
String pageNum = request.getParameter("page");
if(pageNum == null){
	pageNum = "1"; //페이지번호가 없으면 기본값 1로 설정
}
//Dao 객체 생성
Dao dao = new Dao();
//총 페이지 수를 저장할 변수
int totalPage = 0;
// 게시글 목록을 저장할 ArrayList
ArrayList<Dto> posts = null;
//검색어를 get방식으로 받아옴
String searchWord = request.getParameter("word");
// case 1 . 검색어가 없을경우
if(searchWord == null || searchWord.equals("null")){
	// 모든 게시글 목록 가져오기
	posts = dao.list(pageNum);
	// 전체 페이지 수 구하기
	totalPage = dao.getTotalPageCount(); 
}else{
	// case 2. 검색어가 없을경우
	// 검색 결과 목록 가져오기
	posts = dao.listSearch(searchWord, pageNum);
	// 검색 결과의 총 페이지 수 구하기
	totalPage = dao.getSearchTotalPageCount(searchWord);
}

for (int i=0; i<posts.size(); i++){
	
%>
<%=posts.get(i).no %>
<a href="read.jsp?no=<%=posts.get(i).no %>"><%=posts.get(i).title %></a>
<%=posts.get(i).id %>
<hr>
<% 
}
%>
<a href = "write.jsp">쓰기</a>
</body>
</html>
<hr><hr>
<%
int nPageNum = Integer.parseInt(pageNum); //계산을 위한 반환
// (블럭처리 1/9) 블럭 총 갯수 구하기
// 블럭 총 갯수 = 페이지 수 / 블럭 당 페이지 수 << 결과에 올림처리
int totalBlock = (int)Math.ceil((double) totalPage / Board.PAGE_LINK_AMOUNT);

// (블럭처리 2/9) 현재 블럭번호 구하기
// 현재 블럭번호 = 현재 페이지번호 / 블럭당 페이지 수
int currentBlockNo = (int)Math.ceil((double)nPageNum / Board.PAGE_LINK_AMOUNT);

// (블럭처리 3/9) 블럭 시작페이지 번호 구하기
// 블럭시작 페이지번호 = (현재 블럭번호 - 1) * 블럭당 페이지 수 + 1
int blockStartNo = (currentBlockNo - 1)*Board.PAGE_LINK_AMOUNT + 1;

// (블럭처리 4/9) 블럭페이지 끝번호 구하기
// 블럭페이지 끝번호 = 현재블럭번호 * 블럭당 페이지 수
int blockEndNo = currentBlockNo * Board.PAGE_LINK_AMOUNT;

//만약 블럭페이지 마지막 페이지 번호가 전체 페이지 마지막 번호보다 큰 경우
//블럭 마지막 페이지 번호를 페이지 마지막 번호로 저장하는 예외처리하기
if(blockEndNo > totalPage){
	blockEndNo = totalPage;
}

// (블럭처리 5/9) 이전 다음 처리
// 현재 블럭에서 이전 / 다음 눌렀을때 현재 페이지 값 변경시 넣을값을 일단 초기화하기
int prevPage = 0;
int nextPage = 0;

// (블럭처리 6/9) 이전 다음 처리
// 현재 블럭에서 이전 / 다음이 가능한지 게산하고 가능 여부를 저장해두기
boolean hasPrev = true; // 이전 블럭 가기 가능여부 저장값 초기화
	if(currentBlockNo == 1){ // 현재블럭이 1번 블럭이면
		hasPrev = false; // 이전 블럭가기 불가능
	}else{ // 또는 현재블럭이 1번 블럭이 아니면
		hasPrev = true; // 이전 블럭 가기 가능
		// 이전 블럭 이동 시 몇 페이지로 이동할지 정하기
		// 이전 블럭의 마지막 페이지로 이동하게 하면됨(보통 이렇게 처리)
		// 공식 : (현재 블럭번호 - 1) * 블럭당 페이지 수
		prevPage = (currentBlockNo - 1)*Board.PAGE_LINK_AMOUNT;
	}

boolean hasNext = true; // 다음블럭 가기 가능 여부 저장값 초기화
if(currentBlockNo < totalBlock){ //현재블럭이 마지막 블럭보다 작으면
	hasNext = true; //다음 블럭 가기 가능
	// 다음 블럭 이동 시 몇페이지로 이동할지 정하기
	// 다음 블럭의 첫 페이지로 이동하게 하면 됨(보통 이렇게 처리)
	// 공식: 현재블럭번호 * 블럭당 페이지 수 + 1
	nextPage = currentBlockNo * Board.PAGE_LINK_AMOUNT + 1;
}else{ // 현재블럭이 마지막 블럭보다 같거나 크면
	hasNext = false; // 다음블럭가기 불가능
}

// (블럭처리 7/9) 이전 다음 처리
// 이전 블럭이동이 가능하면 미리 계산한 이전 블럭이동 시
// 이동할 페이지 번호를 링크에 전달하기
if(hasPrev){
	if(searchWord == null){ // 1)일반 리스트
%>
<a href="list.jsp?page<%=prevPage %>">이전블럭가기</a>
<%
	}else{ // 2) 검색어로 검색한 리스트
		%>
		<a href = "list.jsp?page=<%=prevPage %>&word=<%=searchWord %>">이전블럭가기</a>
		<%
	}
	}

// (블럭처리 8/9) 기존의 제한없던 페이지리스트 나열에 블럭적용
// 현재 페이지 블럭의 페이지 시작번호와 끝번호를 이용하여 반복문의 시작값
// 끝값으로 하고 이 값을 페에지 번호로 출력하기
// for(int i=1; <=pageMaxNumber; i++){ //이전 반복문을
for(int i=blockStartNo; i<=blockEndNo; i++){ //이렇게 바꿈
	if(nPageNum == 1){
		
		
	}else{
		// (검색처리 5/5) 하단 페이지 링크리스트 출력 및 링크처리를 검색모드/ 일반모드 구분해 처리
		if(searchWord == null){ //일반 리스트
			%>
			<a href = "list.jsp?page=<%=i %>"><%=i %></a>
			<%
			
		}else{ // 검색어로 검색한 리스트
			// 한글 검색어를 전달할때는 url인코딩을 해주어야함 
		}
			String urlEncodedSearchWord = java.net.URLEncoder.encode(searchWord);
		%>
		<a href = "list.jsp?page=<%=i %>&word=<%=urlEncodedSearchWord %>"><%=i %></a>
		<%
		
		}
	}
// (블럭처리 9/9) 이전 다음처리
if(hasNext){
	if(searchWord == null){// 1)일반 리스트
		
	
	%>
	<a href = "list.jsp?page=<%=nextPage %>">다음블럭가기</a>
	<%
}else{ // 2) 검색어로 검색한 리스트
	%>
	<a href = "list.jsp?page=<%=nextPage %>&word=<%=searchWord %>">다음블럭가기</a>
	<%
}
}
%>
<form action = "list.jsp">
<input name = "word">
<input type = "submit" value="검색">
</form>
