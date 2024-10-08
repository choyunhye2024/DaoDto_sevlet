package com.glassis5;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// "/ServletDel" URL 패턴에 대한 서블릿 설정
@WebServlet("/ServletDel")
public class ServletDel extends HttpServlet {
	// get 요청을 처리하는 메서드 (주로 삭제기능에서 사용)
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 클라이언트로 부터 전달된 파라미터 "no"를 받아옴 (삭제할 게시글 번호)
		String no = request.getParameter("no");

		// Dao 개체 생성 후 del 메서드를 호출하여 게시글 삭제
		Dao dao = new Dao();
		dao.del(no);
		// 게시글 삭제 후 게시글 목록 페이지로 리다이렉트
		// 리다이렉트 : 사용자가 처음 요청한 URL이 아닌, 다른 URL로 보내는 것
		response.sendRedirect("list.jsp");
	}

}
