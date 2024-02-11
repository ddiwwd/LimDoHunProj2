package controller.myproject;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.bbs.BBSDao;
import model.bbs.BBSDto;
import model.member.MemberDao;
import model.member.MemberDto;




@WebServlet("/myproject/MDelete.ict")

public class MemberDeleteController extends HttpServlet{

	
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1]파라미터(키값) 받기	
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		//2]CRUD작업용 BBSDao생성
		

		
		MemberDao dao = new MemberDao(getServletContext());
		MemberDto dto = new MemberDto();
		
		BBSDao bbsdao = new BBSDao(getServletContext());
		bbsdao.Mdelete(username);
		


        dto.setUsername(username); 
        	System.out.println("username:"+username);

    	

            int affected = dao.Mdelete(username);
            dao.close();
            HttpSession session = req.getSession(false);
            if (session != null) {
                // 현재 세션이 있으면 세션을 무효화하고 로그아웃 처리
                session.invalidate();
            }
            if (affected == 1) {
                // 회원 삭제 성공 시 MainPage로 이동
             resp.setContentType("text/html;charset=UTF-8");
           	 PrintWriter out = resp.getWriter();
             out.println("<script>");
             out.println("alert('탈퇴 했어요');");
             out.println("location.href='http://localhost:8060/LimDoHunProj2/myproject/index.ict';");
             out.println("</script>");
            //resp.sendRedirect(req.getContextPath()+"/myproject/index.ict");

            } else {
                // 삭제 실패 시 에러 메시지 출력 후 이전 페이지로 이동
                resp.setContentType("text/html;charset=UTF-8");
                PrintWriter out = resp.getWriter();
                out.println("<script>");
                out.println("alert('탈퇴 실패했어요');");
                out.println("history.back();");
                out.println("</script>");
            }
         
	



    	
    	
    	
    	/*
		if(affected==1){
		
			req.getRequestDispatcher("/project/MainPage.jsp");
			//resp.sendRedirect(req.getContextPath()+"/myproject/List.ict?"+PagingUtil.NOWPAGE+"="+nowPage+"&"+PagingUtil.PAGE_SIZE+"="+pageSize);	
		}
		else{
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter out = resp.getWriter();
				out.println("<script>");
				out.println("alert('삭제 실패했어요');");
				out.println("history.back();");
				out.println("</script>");
		}
		*/
	}
}
	

