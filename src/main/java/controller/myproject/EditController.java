package controller.myproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.FileUtils;
import model.PagingUtil;
import model.bbs.BBSDao;
import model.bbs.BBSDto;
import model.member.MemberDao;
import model.member.MemberDto;

@WebServlet("/myproject/Edit.ict")

public class EditController extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String no = req.getParameter("no");
		String nowPage = req.getParameter("nowPage");
		String pageSize = req.getParameter("pageSize");
		System.out.println("nowPage : "+nowPage);
		System.out.println("pageSize : "+pageSize);
		
        BBSDao dao = new BBSDao(getServletContext());
        BBSDto dto = dao.selectOne(no);
        dao.close();
        req.setAttribute("no", no );
        // 줄바꿈 처리
        dto.setContent(dto.getContent().replace("\r\n", "<br/>"));
        req.setAttribute("records", dto);  
		req.getRequestDispatcher("/project/EditPage.jsp").forward(req, resp);
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//request.setCharacterEncoding("UTF-8");
		//파라미터 받기
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String no = req.getParameter("no");
		String username = req.getParameter("username");
		//현재 페이지번호 받기
		String nowPage= req.getParameter("nowPage");
		String pageSize= req.getParameter("pageSize");
		//데이타를 전달할 DTO객체 생성및 데이타 설정
		BBSDto dto = new BBSDto();
		dto.setUsername(username);
		dto.setContent(content);	
		dto.setTitle(title);
		dto.setNo(no);
		System.out.println(no);
		System.out.println(content);
		//CRUD작업용 DAO계열 객체 생성
		BBSDao dao = new BBSDao(req.getServletContext());
		int affected=dao.update(dto);
		dao.close();
		if(affected==1){  				
			resp.sendRedirect(req.getContextPath()+"/myproject/ViewOK.ict?no="+no+"&nowPage="+nowPage+"&pageSize="+pageSize);
		}
		else{
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter out = resp.getWriter();
			out.println("<script>");
			out.println("alert('수정 실패했어요');");
			out.println("history.back();");
			out.println("</script>");
		}
	}
}
