package com.glassis5;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SeervletEdit
 */
@WebServlet("/SeervletEdit")
// get 요청을 처리하는 메서드 (게시글 수정기능)
public class SeervletEdit extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 클라이언트로부터 전달된 "title"과 "text" parameter을 사용하여 Dto 객체 생성
		Dto dto = new Dto(request.getParameter("title"), request.getParameter("text"));

		// Dao 객체 생성 후 edit 메서드를 호출하여 게시글 수정
		Dao dao = new Dao();
		// "no" parameter 를 사용하여 수정할 게시글 선택
		dao.edit(dto, request.getParameter("no"));
		// 게시글 수정 후 게시글 목록 페이지로 리다이렉트
		response.sendRedirect("list.jsp");

	}
}
