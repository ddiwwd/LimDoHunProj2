package controller.myproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.PagingUtil;
import model.bbs.BBSDao;
import model.bbs.BBSDto;




@WebServlet("/myproject/Delete.ict")

public class DeleteController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//1]파라미터(키값) 받기	
		String no = req.getParameter("no");

		//현재 페이지번호 받기
		int nowPage= Integer.parseInt(req.getParameter(PagingUtil.NOWPAGE));
		//페이지 사이즈 받기
		int pageSize=Integer.parseInt(req.getParameter(PagingUtil.PAGE_SIZE));
		

		
		//2]CRUD작업용 BBSDao생성
		BBSDao dao = new BBSDao(getServletContext());
		BBSDto dto = new BBSDto();
        dto.setNo(no); 

        int affected = dao.delete(dto);
        int totalRecordCount= dao.getTotalRecordCount(null);	
    	int totalPage =(int)Math.ceil((double)totalRecordCount/pageSize);
    	dao.close();
    	if(totalPage < nowPage) nowPage=totalPage;
    	
		if(affected==1){
		
			
			resp.sendRedirect(req.getContextPath()+"/myproject/List.ict?"+PagingUtil.NOWPAGE+"="+nowPage+"&"+PagingUtil.PAGE_SIZE+"="+pageSize);	
		}
		else{
			resp.setContentType("text/html;charset=UTF-8");
			PrintWriter out = resp.getWriter();
				out.println("<script>");
				out.println("alert('삭제 실패했어요');");
				out.println("history.back();");
				out.println("</script>");
		}
		
		}
	
	
}
