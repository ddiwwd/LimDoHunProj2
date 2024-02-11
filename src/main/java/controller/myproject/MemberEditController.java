package controller.myproject;

import java.io.IOException;
import java.io.PrintWriter;


import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.PagingUtil;
import model.bbs.BBSDao;
import model.bbs.BBSDto;
import model.member.MemberDao;
import model.member.MemberDto;

@WebServlet("/myproject/MemberEdit.ict")

public class MemberEditController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
 
		req.getRequestDispatcher("/project/EditMember.jsp").forward(req, resp);

	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//request.setCharacterEncoding("UTF-8");
		//파라미터 받기
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String gender = req.getParameter("gender");
		String inters = req.getParameter("inters");
		String education = req.getParameter("education");
		String selfintroduce = req.getParameter("selfintroduce");
		MemberDao dao = new MemberDao(getServletContext());
		
		//데이타를 전달할 DTO객체 생성및 데이타 설정
		MemberDto dto = new MemberDto();
		dto.setUsername(username);
		dto.setPassword(password);	
		dto.setGender(gender);
		dto.setInters(inters);
		dto.setEducation(education);
		dto.setSelfintroduce(selfintroduce);
		
		
		
		//CRUD작업용 DAO계열 객체 생성
		System.out.println(username);
		int affected=dao.update(dto);
		System.out.println(affected);
		dao.close();
		if(affected==1){
			System.out.println(username);
			System.out.println(password);
			System.out.println(gender);
			System.out.println(inters);
			System.out.println(education);
			System.out.println(selfintroduce);
			req.setAttribute("records", dto); 
			req.getRequestDispatcher("/myproject/List.ict").forward(req, resp);
			//resp.sendRedirect(req.getContextPath()+"/myproject/Password.ict?username="+username);
			//req.getRequestDispatcher("/myproject/ViewOK.ict?no="+no+"&"+PagingUtil.NOWPAGE+"="+nowPage).forward(req, resp);
		
		}
		else{
			System.out.println(gender);
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<script>");
			out.println("alert('수정 실패했어요');");
			out.println("history.back();");
			out.println("</script>");
		}
	}
}
