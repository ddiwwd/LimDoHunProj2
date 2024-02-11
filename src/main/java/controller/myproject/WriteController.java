
package controller.myproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import model.bbs.BBSDao;
import model.bbs.BBSDto;





@WebServlet("/myproject/write.ict")
public class WriteController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	int insertFlag;
    	// Get parameters from request
 
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        //String username=(String)req.getSession().getAttribute("username");
        String username = req.getParameter("username");
        System.out.println(username);
       
        
        // Create a BBSDto object and set its properties
        BBSDao dao = new BBSDao(getServletContext());
		BBSDto dto = new BBSDto();
		dto.setContent(content);
		dto.setUsername(username);
		dto.setTitle(title);
		
		insertFlag= dao.insert(dto);

        // Create a BBSDao object and save the data
      
		req.setAttribute("SUCCFAIL", insertFlag);
		req.setAttribute("WHERE", "INS");
        // Save username to session

        // Forward the request to WriteOk.jsp
        req.getRequestDispatcher("/myproject/List.ict").forward(req, resp);
    }
}
