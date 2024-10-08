package com.glassis5;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//게시글 작성기능
@WebServlet("/ServletWrite")
public class ServletWrite extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 클라이언트로부터 전달된 "title", "id", "text" 파라미터를 사용하여 Dto 객체생성
		Dto dto = new Dto(request.getParameter("title"), request.getParameter("id"), request.getParameter("text"));
		// Dao 객체 생성 후 write 메서드를 호출하여 새로운 게시글 작성
		Dao dao = new Dao();
		dao.write(dto);
		// 게시글 작성 후 게시글 목록페이지로 리다이렉트
		response.sendRedirect("list.jsp");
	}
}
