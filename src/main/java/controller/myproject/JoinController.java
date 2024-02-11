package controller.myproject;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.member.MemberDao;
import model.member.MemberDto;

@WebServlet("/myproject/join.ict")
public class JoinController extends HttpServlet {
	
	@Override
	 protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String pwd = req.getParameter("pwd");
        String gender = req.getParameter("gender");
        String inters=Arrays.stream(req.getParameterValues("inters")).collect(Collectors.joining(","));
        String education = req.getParameter("grade");
        String selfintroduce = req.getParameter("self");
        MemberDto dto=new MemberDto();
        dto.setUsername(id);
        dto.setPassword(pwd);
        dto.setGender(gender);
        dto.setInters(inters);
        dto.setEducation(education);
        dto.setSelfintroduce(selfintroduce);
        
        MemberDao dao=new MemberDao(getServletContext());
        dao.insert(dto);
        req.setAttribute("record", dto);
		resp.sendRedirect(req.getContextPath()+"/project/LoginPage.jsp");
		
	}
}
