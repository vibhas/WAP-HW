package waps.hw.vibhas;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("home");
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String messageId = request.getParameter("messageID");
		HttpSession session = request.getSession();
		
		if (messageId != null)  
		{
				DbUtilities db = new DbUtilities();
				String query = "DELETE FROM message where id = "
						+ messageId;

				Boolean result = db.updateData(query);
				if(result == false)
				{
					session.setAttribute("Error", "The message could not be deleted because of some error. Please try again later.");
				}
				db.closeConnection();			
		}
		response.sendRedirect("home");
	}

}
