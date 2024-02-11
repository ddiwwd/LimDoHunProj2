package controller.myproject;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/myproject/Logout")
public class LogoutController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processLogout(req,resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		processLogout(req,resp);
	}
	private void processLogout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            // 현재 세션이 있으면 세션을 무효화하고 로그아웃 처리
            session.invalidate();
        }
        // 로그아웃 후 로그인 페이지 또는 다른 페이지로 이동
        resp.sendRedirect(req.getContextPath()+"/project/LoginPage.jsp"); // 로그인 페이지로 이동하도록 수정
    }
	
}
