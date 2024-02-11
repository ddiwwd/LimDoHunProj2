package controller.myproject;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.PagingUtil;
import model.member.MemberDao;

@WebServlet("/myproject/PasswordCheck.ict")
public class LoginCheckController extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String username = (String)req.getSession().getAttribute("username");
		String no = req.getParameter("no");
		String nowPage = req.getParameter("nowPage");
		String pageSize = req.getParameter("pageSize");
		String password = req.getParameter("password");
		System.out.println(nowPage);
		MemberDao dao = new MemberDao(getServletContext());
		//boolean isCorrect = dao.isMember(username,password);
		boolean isCorrect = dao.isMember(username,password);
		
		dao.close();
		if(!isCorrect) {
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter out= resp.getWriter();
			out.println("<script>");
			out.println("alert('비밀번호가 일치하지 않아요');");
			out.println("history.back();");
			out.println("</script>");
			return;
		} 
		http://localhost:8060/LimDoHunProj2/myproject/View.ict?no=189        &pageSize= 10
		resp.sendRedirect(req.getContextPath()+"/myproject/Edit.ict?no="+no+"&nowPage="+nowPage+"&pageSize="+pageSize);
		
	}
	
}
