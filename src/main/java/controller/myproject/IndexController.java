package controller.myproject;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/myproject/index.ict")
public class IndexController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//WEB-INF밑에 있는 뷰(JSP)는 컨트롤러를 거치지 않고는 직접 URL접근 불가(404에러):간접 보안 효과
		req.getRequestDispatcher("/project/MainPage.jsp").forward(req, resp);
		//JSP는 출력 담당임으로 데이타를 리퀘스크 영역에 저정하는 서블릿(컨트롤러)로 포워드 시킨다.
		//req.getRequestDispatcher("/Dataroom/JoinMembership.ict").forward(req, resp);
		
	}
}
