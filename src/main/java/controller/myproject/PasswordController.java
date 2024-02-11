package controller.myproject;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.bbs.BBSDto;
import model.bbs.BBSDao;


@WebServlet("/myproject/Password.ict")
public class PasswordController extends HttpServlet {
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//파라미터 받기]
		String no =req.getParameter("no");
		String username = req.getParameter("username");
		String mode = req.getParameter("mode");
		String password = req.getParameter("password");
		//모델 호출 및 결과값 받기]	
		BBSDao dao = new BBSDao(getServletContext());
		//boolean isCorrect = dao.isMember(username,password);
		boolean isCorrect = dao.isMember(username,password);
		
		dao.close();
		req.getSession().setAttribute("password", password);
		req.getSession().setAttribute("username", username);
		req.getSession().setAttribute("mode", mode);
		req.getSession().setAttribute("no", no);
		if(!isCorrect) {
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter out= resp.getWriter();
			out.println("<script>");
			out.println("alert('비밀번호가 일치하지 않아요');");
			out.println("history.back();");
			out.println("</script>");
			return;
			
		} 

		//[비밀번호가 일치하는 경우]
		req.getRequestDispatcher("/myproject/List.ict").forward(req, resp);

	}/////
}

