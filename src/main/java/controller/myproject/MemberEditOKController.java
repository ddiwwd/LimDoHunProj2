package controller.myproject;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.member.MemberDao;
import model.member.MemberDto;

@WebServlet("/myproject/memberEditOk.ict")
public class MemberEditOKController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String gender = req.getParameter("gender");
		String inters = req.getParameter("inters");
		String education = req.getParameter("education");
		String selfintroduce = req.getParameter("selfintroduce");
		
		MemberDao dao = new MemberDao(getServletContext());
		MemberDto dto = new MemberDto();
		
		dto.setUsername(username);
		dto.setPassword(password);	
		dto.setGender(gender);
		dto.setInters(inters);
		dto.setEducation(education);
		dto.setSelfintroduce(selfintroduce);
		
        req.setAttribute("record", dto);
		resp.sendRedirect(req.getContextPath()+"/project/LoginPage.jsp");
		
	}
	
	

}
