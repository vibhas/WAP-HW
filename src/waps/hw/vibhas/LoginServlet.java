package waps.hw.vibhas;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		Map<String, String> errors = new HashMap<String, String>();

		if (userName == "") {
			errors.put("userName", "Username cannot be blank.");
		}

		if (password == "") {
			errors.put("password", "Password cannot be blank.");
		}

		int flag = 0;
		if (errors.isEmpty()) {
			DbUtilities db = new DbUtilities();
			
			try {
								
				String hashPassword = Utilities.getHash(password);
				
				String query = "SELECT * FROM users WHERE username = '"
						+ userName + "' AND password = '" + hashPassword + "'";
				ResultSet result = db.retrieveData(query);
				
				if(result == null)
				{
					flag = 1;
				}
				else if (!result.next()) {
					errors.put("loginError",
							"Username or the password you have provided is not correct.");
				}

				else {
					int id = result.getInt("id");
					String name = result.getString("name");
					System.out.println("ID is " + id + " and name is " + name);
					session.setAttribute("id", id);
					session.setAttribute("name", name);
				}

			} catch (Exception e) {
				e.printStackTrace();
				flag = 1;
			}
			finally
			{
				db.closeConnection();
			}
		}

		if(flag == 1)
		{
			request.setAttribute(
					"DbError",
			"Login could not be successful because of some error. Please try again later.");
			request.getRequestDispatcher("login.jsp")
			.forward(request, response);
		}
		else if (errors.isEmpty()) {
			response.sendRedirect("home");
		} else {
			// Put errors in request scope and forward back to JSP.
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("login.jsp")
					.forward(request, response);
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect("login.jsp");
	}
}
